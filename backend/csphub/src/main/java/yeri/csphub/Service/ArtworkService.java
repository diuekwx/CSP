package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.DTO.FileDto;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkRepositoryRepo;
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
    // change this name man
    private final ArtworkRepositoryRepo artworkRepositoryRepo;

    public ArtworkService(ArtworkRepo artworkRepo, UserUtil userUtil, ArtworkRepositoryRepo artworkRepositoryRepo){
        this.artworksRepo = artworkRepo;
        this.userUtil = userUtil;
        this.artworkRepositoryRepo = artworkRepositoryRepo;
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
        Artworks toBeEdited = artworksRepo.findByUserAndName(repoName, user)
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
        List<Artworks> artworks = artworksRepo.findAllByUser(user);
        return artworks;
    }


}
