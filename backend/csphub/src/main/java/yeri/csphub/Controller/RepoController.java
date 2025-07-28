package yeri.csphub.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import yeri.csphub.DTO.ArtworkDTO;
import yeri.csphub.DTO.CreateRepoDTO;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.DTO.ProjectsDto;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Service.ArtworkService;
import yeri.csphub.utils.UserUtil;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin("http://127.0.0.1:5173")
@RequestMapping("/repository")
public class RepoController {

    private final ArtworkService artworkService;
    private final UserUtil userUtil;

    public RepoController(ArtworkService artworkService, UserUtil userUtil){
        this.artworkService = artworkService;
        this.userUtil = userUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewRepo(@RequestBody CreateRepoDTO repoDto) throws DataIntegrityViolationException {

        try {
            Artworks created = artworkService.createNewRepo(repoDto.getTitle(), repoDto.getDescription(), repoDto.isPublic());
            URI location = URI.create("/" + created.getUserId().getUsername() + "/" + created.getTitle());
            return ResponseEntity.created(location).body(created);
        }
        //DataIntegrityViolationException
        catch (Exception e){
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

    @GetMapping("/projects")
    public ResponseEntity<?> allRepos(){
        try{
            Users user = userUtil.findUser();
            List<ProjectsDto> projects = artworkService.getAllprojects(user);
            return ResponseEntity.status(200).body(projects);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cant retrieve projects");
        }
    }

    @GetMapping("/{owner}/{repoName}")
    public ResponseEntity<?> viewRepo(@PathVariable String owner, @PathVariable String repoName){
            try{
                Users user = userUtil.findUser();
                ArtworkDTO dto = artworkService.viewArtwork(user, owner, repoName);
                return ResponseEntity.status(200).body(dto);
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wtfffffff ");
            }
    }

}
