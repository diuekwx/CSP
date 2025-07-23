package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.ArtworkDTO;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkVersionRepo;
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
    private final ArtworkVersionRepo artworkVersionRepo;

    public ArtworkService(ArtworkRepo artworkRepo, UserUtil userUtil, ArtworkVersionRepo artworkVersionRepo){
        this.artworksRepo = artworkRepo;
        this.userUtil = userUtil;
        this.artworkVersionRepo = artworkVersionRepo;
    }

    // make this shit a dto
    public Artworks createNewRepo(String title, String description){
        Instant now = Instant.now();
        Users user = userUtil.findUser();

        Artworks artworks = new Artworks(user, title, description, now, true);
        artworksRepo.save(artworks);
        return artworks;
    }

    public ArtworkDTO viewArtwork(Users user, String username, String repoName){
        ArtworkDTO dto = new ArtworkDTO();

        Artworks art = artworksRepo.findByUserIdAndTitle(user, repoName).orElseThrow(() -> new RuntimeException("Repo not found"));
        dto.setTitle(repoName);
        dto.setDescription(art.getDescription());
        dto.setOwner(username);
        dto.setPublic(art.isPublic());
        List<ArtworkVersions> versions = artworkVersionRepo.findAllByArtworkId(art);
        if (versions.isEmpty()){
            dto.setEmpty(true);
        }
        dto.setFiles(versions);

        return dto;
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
