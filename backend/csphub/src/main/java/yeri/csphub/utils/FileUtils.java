package yeri.csphub.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepo;
import yeri.csphub.Repository.ArtworkVersionRepo;

@Component
public class FileUtils {

    private final ArtworkVersionRepo artworkVersionRepo;
    private final ArtworkRepo artworkRepo;

    public FileUtils(ArtworkVersionRepo artworkVersionRepo, ArtworkRepo artworkRepo){
        this.artworkVersionRepo = artworkVersionRepo;
        this.artworkRepo = artworkRepo;
    }

    public String generateUniqueFileName(Users user,  String repoName, String originalName) {
        Artworks artworks = artworkRepo.findByUserIdAndTitle(user, repoName)
                .orElseThrow(() -> new RuntimeException("cant find associated repo"));

        String baseName = FilenameUtils.getBaseName(originalName);
        String extension = FilenameUtils.getExtension(originalName);
        String fileName = originalName;
        int counter = 1;

        while (artworkVersionRepo.existsByArtworkIdAndOgFileName(artworks, fileName)) {
            fileName = baseName + " (" + counter + ")." + extension;
            counter++;
        }

        return fileName;
    }
}
