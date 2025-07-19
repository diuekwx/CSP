package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.CreateRepoRequestDTO;
import yeri.csphub.DTO.FileDto;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkVersionRepo;
import yeri.csphub.utils.UserUtil;

import java.time.Instant;

@Service
public class ArtworkVersionsService {
    private final ArtworkVersionRepo artworkVersionRepo;
    private final UserUtil userUtil;
    private final ArtworkRepo artworkRepo;

    public ArtworkVersionsService (ArtworkVersionRepo artworkVersionRepo, UserUtil userUtil,
                                   ArtworkRepo artworkRepo){
        this.artworkVersionRepo = artworkVersionRepo;
        this.userUtil = userUtil;
        this.artworkRepo = artworkRepo;
    }

    public void commitUpload(CreateRepoRequestDTO dto){
        ArtworkVersions commit = new ArtworkVersions();
        commit.setVersion_description(dto.getDescription());
        commit.setUploadedAt(Instant.now());
        commit.setFileUrl(dto.getFileUri());

        Users user = userUtil.findUser();
        Artworks repo = artworkRepo.findByTitle(dto.getTitle())
                .orElseThrow(() -> new RuntimeException("Commit error!"));

        if (repo.getUserId() == user){
            commit.setArtworkId(repo);
        }

        artworkVersionRepo.save(commit);

    }

}
