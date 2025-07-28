package yeri.csphub.Service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import yeri.csphub.DTO.ContributionDTO;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkVersionRepo;
import yeri.csphub.utils.FileUtils;
import yeri.csphub.utils.UserUtil;

import java.time.Instant;

@Service
public class ArtworkVersionsService {
    private final ArtworkVersionRepo artworkVersionRepo;
    private final UserUtil userUtil;
    private final ArtworkRepo artworkRepo;
    private final UploadService uploadService;

    public ArtworkVersionsService (ArtworkVersionRepo artworkVersionRepo, UserUtil userUtil,
                                   ArtworkRepo artworkRepo, UploadService uploadService){
        this.artworkVersionRepo = artworkVersionRepo;
        this.userUtil = userUtil;
        this.artworkRepo = artworkRepo;
        this.uploadService = uploadService;
    }

    public ArtworkVersions commitUpload(ContributionDTO dto){

        ArtworkVersions commit = new ArtworkVersions();
        commit.setVersion_description(dto.getDescription());

        commit.setUploadedAt(Instant.now());
        commit.setFileUrl(dto.getFileUri());

        Users user = userUtil.findUser();
        Artworks repo = artworkRepo.findByTitle(dto.getTitle())
                .orElseThrow(() -> new RuntimeException("Commit error!"));

        if (repo.getUserId().getId().equals(user.getId())){
            commit.setArtworkId(repo);
        }

        commit.setOgFileName(dto.getOgFileName());

        artworkVersionRepo.save(commit);

        return commit;
    }

    public String viewImage(String repoName, String ogFile){
        Users user = userUtil.findUser();

        Artworks artworks = artworkRepo.findByUserIdAndTitle(user, repoName).orElseThrow(() -> new RuntimeException("Repo not found!!!!!!"));
        ArtworkVersions version = artworkVersionRepo.findByArtworkIdAndOgFileName(artworks, ogFile);

        String unqiueId = version.getFileUrl();

        String path = uploadService.generateSignedUrl(unqiueId, 3600);
        return path;
    }

}
