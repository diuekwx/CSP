package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.utils.UserUtil;

import java.time.Instant;
import java.util.List;

@Service
public class ArtworkService {

    /**
     * ARTWORKS ARE JUST THE PROJECTS BASICALLY. THE ARTWORK VERSIONS ARE THE FILES THEMSELVES
     */



    private final ArtworkRepo artworksRepo;
    private final UserUtil userUtil;

    public ArtworkService(ArtworkRepo artworkRepo, UserUtil userUtil){
        this.artworksRepo = artworkRepo;
        this.userUtil = userUtil;
    }

    // make this shit a dto
    public Artworks createNewRepo(String title, String description){
        Instant now = Instant.now();
        Users user = userUtil.findUser();

        Artworks artworks = new Artworks(user, title, description, now, true);
        artworksRepo.save(artworks);
        return artworks;
    }



    public Artworks editProject(String repoName, EditingRepoRequestDTO dto){
        Users user = userUtil.findUser();
        Artworks toBeEdited = artworksRepo.findByUserIdAndTitle(user, repoName)
                .orElseThrow(() -> new RuntimeException("Editing: Repo not found!"));

        if (dto.getName() != null) {
            toBeEdited.setTitle(dto.getName());
        }

        if (dto.getDesc() != null) {
            toBeEdited.setDescription(dto.getDesc());
        }

        if (dto.isPublic() != null) {
            toBeEdited.setPublic(dto.isPublic());
        }

        artworksRepo.save(toBeEdited);

        return toBeEdited;
    }

    public List<Artworks> getAllprojects(){
        Users user = userUtil.findUser();
        List<Artworks> artworks = artworksRepo.findAllByUserId(user);
        return artworks;
    }


}
