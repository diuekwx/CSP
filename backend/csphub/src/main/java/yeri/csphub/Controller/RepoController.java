package yeri.csphub.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Service.ArtworkRepositoryService;

import java.net.URI;

@RestController
@RequestMapping("/repository")
public class RepoController {

    private final ArtworkRepositoryService artworkRepositoryService;

    public RepoController(ArtworkRepositoryService artworkRepositoryService){
        this.artworkRepositoryService = artworkRepositoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewRepo(@RequestParam String title, @RequestParam String desc) throws DataIntegrityViolationException {
        try {
            ArtworkRepository created = artworkRepositoryService.createRepo(title, desc);
            URI location = URI.create("/" + created.getName());
            return ResponseEntity.created(location).body(created);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("A repository with this name already exists.");
        }
    }



}
