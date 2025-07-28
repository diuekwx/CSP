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
import yeri.csphub.DTO.AddContributionDTO;
import yeri.csphub.DTO.ContributionDTO;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Users;
import yeri.csphub.Service.ArtworkVersionsService;
import yeri.csphub.Service.ContributionService;
import yeri.csphub.Service.UploadService;
import yeri.csphub.utils.FileUtils;
import yeri.csphub.utils.UserUtil;

import java.io.IOException;
import java.sql.Date;
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
    private final UserUtil userUtil;
    private final FileUtils fileUtils;

    public ContributionController(UploadService uploadService, ContributionService contributionService,
                                  ArtworkVersionsService artworkVersionsService, UserUtil userUtil, FileUtils fileUtils){
        this.uploadService = uploadService;
        this.contributionService = contributionService;
        this.artworkVersionsService = artworkVersionsService;
        this.userUtil = userUtil;
        this.fileUtils = fileUtils;
    }

    @GetMapping("/view-image")
    public ResponseEntity<String> viewImage(@RequestParam String repoName, @RequestParam String fileName){
        try{
            String file = artworkVersionsService.viewImage(repoName, fileName);
            return ResponseEntity.status(200).body(file);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CANT GET IMAGE LOL");
        }
    }

    @GetMapping("/user-contributions")
    public ResponseEntity<Map<Date, Long >> getContributionDates(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Map<Date, Long> contributions = contributionService.getContributionDates(username);
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
            Users user = userUtil.findUser();
            //file.getOriginalFilename();
            String ogFileName = fileUtils.generateUniqueFileName(user, repoName, file.getOriginalFilename());

            String uniquePath = UUID.randomUUID() + "-" + ogFileName;
            String storagePath = uploadService.upload(file, uniquePath);

            ContributionDTO dto = new ContributionDTO(repoName, ogFileName, description, uniquePath);

            ArtworkVersions artworkVersion = artworkVersionsService.commitUpload(dto);
            AddContributionDTO addDto = new AddContributionDTO(userUtil.findUser(), artworkVersion.getArtworkId(), artworkVersion, "upload");

            contributionService.addContribution(addDto);
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
