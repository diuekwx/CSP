package yeri.csphub.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeri.csphub.Service.UploadService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/contribution")
public class ContributionController {

    private final UploadService uploadService;

    public ContributionController(UploadService uploadService){
        this.uploadService = uploadService;
    }

    @PostMapping("/contribution")
    public ResponseEntity<Map<String, String>> fileUpload(@RequestHeader("Authorization") String jwt,
            @RequestParam("file")MultipartFile file){
        try{
            String ogFileName = file.getOriginalFilename();
            String uniquePath = UUID.randomUUID() + "-" + ogFileName;

            uploadService.upload(jwt, file, uniquePath);

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
