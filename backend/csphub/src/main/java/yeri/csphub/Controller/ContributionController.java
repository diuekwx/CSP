package yeri.csphub.Controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeri.csphub.DTO.ContributionDTO;
import yeri.csphub.Service.ArtworkVersionsService;
import yeri.csphub.Service.ContributionService;
import yeri.csphub.Service.UploadService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("http://127.0.0.1:5173")
@RequestMapping("/contribution")
public class ContributionController {

    private final UploadService uploadService;
    private final ContributionService contributionService;
    private final ArtworkVersionsService artworkVersionsService;

    public ContributionController(UploadService uploadService, ContributionService contributionService,
                                  ArtworkVersionsService artworkVersionsService){
        this.uploadService = uploadService;
        this.contributionService = contributionService;
        this.artworkVersionsService = artworkVersionsService;
    }

    @GetMapping("/user-contributions")
    public ResponseEntity<Map<LocalDate, Long >> getContributionDates(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Map<LocalDate, Long> contributions = contributionService.getContributionDates(username);
        return ResponseEntity.ok(contributions);

    }
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> fileDownload(@RequestParam("path") String path) throws IOException, InterruptedException {
        byte[] res = uploadService.download(path);
        ByteArrayResource resource = new ByteArrayResource(res);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    @PostMapping("/contribution")
    public ResponseEntity<Map<String, String>> fileUpload(@RequestParam String repoName,
                                                          @RequestParam String description,
                                                          @RequestParam("file")MultipartFile file){
        try{

            String ogFileName = file.getOriginalFilename();
            String uniquePath = UUID.randomUUID() + "-" + ogFileName;

            String storagePath = uploadService.upload(file, uniquePath);

            ContributionDTO dto = new ContributionDTO(repoName, description, storagePath);

            artworkVersionsService.commitUpload(dto);

            Map<String, String> res = new HashMap<>();
            res.put("filename", ogFileName);
            res.put("path", uniquePath);

            return ResponseEntity.ok(res);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
