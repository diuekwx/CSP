package yeri.csphub.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Service.ArtworkService;

import java.net.URI;


@RestController
@CrossOrigin("http://127.0.0.1:5173")
@RequestMapping("/repository")
public class RepoController {

    private final ArtworkService artworkService;

    public RepoController(ArtworkService artworkService){
        this.artworkService = artworkService;
    }

    //how do i get user id for every event they do ?
    // connect user to every endpoint after login
    // ie when saving a new project, how does the db know its connecting to a user

    //requestbody check -- have to change fs
    // make this shit a dto, cant have multiple requestbody lol
    @PostMapping("/create")
    public ResponseEntity<?> createNewRepo(@RequestBody String title, @RequestBody String desc) throws DataIntegrityViolationException {
        try {
            Artworks created = artworkService.createNewRepo(title, desc);
            URI location = URI.create("/" + created.getUserId().getUsername() + "/" + created.getTitle());
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
            Artworks updated = artworkService.editProject(name, req);
            return ResponseEntity.status(200).body(updated);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't find hehe ");
        }

    }

}
