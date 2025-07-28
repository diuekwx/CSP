package yeri.csphub.Service;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import yeri.csphub.DTO.ArtworkDTO;
import yeri.csphub.DTO.ArtworkVersionDTO;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.DTO.ProjectsDto;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkVersionRepo;
import yeri.csphub.utils.UserUtil;

import java.time.Instant;
import java.util.ArrayList;
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
    public Artworks createNewRepo(String title, String description, boolean isPublic){
        Instant now = Instant.now();
        Users user = userUtil.findUser();

        Artworks artworks = new Artworks(user, title, description, now, isPublic);
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
        dto.setEmpty(versions.isEmpty());

        List<ArtworkVersionDTO> versionDTOs = versions.stream()
                .map(ArtworkVersionDTO::new)
                .toList();

        dto.setFiles(versionDTOs);

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

    public List<ProjectsDto> getAllprojects(Users user){
        List<Artworks> artworks = artworksRepo.findAllByUserId(user);
        List<ProjectsDto> listOfDto = new ArrayList<>();
        for (Artworks a: artworks){
            ProjectsDto dto = new ProjectsDto();
            dto.setId(a.getId());
            dto.setTitle(a.getTitle());
            dto.setPublic(a.isPublic());
            dto.setUpdatedAt(a.getCreatedAt());
            listOfDto.add(dto);
        }
        return listOfDto;
    }


}
