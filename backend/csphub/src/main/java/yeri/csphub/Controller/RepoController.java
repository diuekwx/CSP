package yeri.csphub.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri.csphub.DTO.EditingRepoRequestDTO;
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

    //how do i get user id for every event they do ?
    // connect user to every endpoint after login
    // ie when saving a new project, how does the db know its connecting to a user

    //requestbody check -- have to change fs
    @PostMapping("/create")
    public ResponseEntity<?> createNewRepo(@RequestBody String title, @RequestBody String desc) throws DataIntegrityViolationException {
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

    @PatchMapping("/edit")
    public ResponseEntity<?> editRepo(@RequestParam String name, @RequestBody EditingRepoRequestDTO req){
        try{
            ArtworkRepository updated = artworkRepositoryService.editRepo(name, req);
            return ResponseEntity.status(200).body(updated);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't find");
        }

    }

}
