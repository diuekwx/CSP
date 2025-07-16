package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Repository.ArtworkRepositoryRepo;

import java.time.Instant;
import java.time.InstantSource;

@Service
public class ArtworkRepositoryService {

    private final ArtworkRepositoryRepo artworkRepositoryRepo;

    public ArtworkRepositoryService(ArtworkRepositoryRepo artworkRepositoryRepo){
        this.artworkRepositoryRepo = artworkRepositoryRepo;
    }

    public ArtworkRepository createRepo(String name, String description){
        Instant curr = Instant.now();
        ArtworkRepository newEntry = new ArtworkRepository(name, description, curr);
        artworkRepositoryRepo.save(newEntry);
        return newEntry;
    }

}
