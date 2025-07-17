package yeri.csphub.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepositoryRepo;
import yeri.csphub.Repository.UsersRepo;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;

@Service
public class ArtworkRepositoryService {

    private final ArtworkRepositoryRepo artworkRepositoryRepo;
    private final UsersRepo usersRepo;

    public ArtworkRepositoryService(ArtworkRepositoryRepo artworkRepositoryRepo, UsersRepo usersRepo){
        this.artworkRepositoryRepo = artworkRepositoryRepo;
        this.usersRepo = usersRepo;
    }

    public List<ArtworkRepository> getProjectsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return artworkRepositoryRepo.findAllByUser(user);
    }

    public ArtworkRepository createRepo(String name, String description){
        Instant curr = Instant.now();

        //make util lol
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username).orElseThrow();

        ArtworkRepository newEntry = new ArtworkRepository(name, description, curr);
        newEntry.setOwner(user);

        artworkRepositoryRepo.save(newEntry);
        return newEntry;
    }

    public void editRepo(){


    }
}
