package yeri.csphub.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yeri.csphub.DTO.EditingRepoRequestDTO;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ArtworkRepositoryRepo;
import yeri.csphub.Repository.UsersRepo;
import yeri.csphub.utils.UserUtil;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;

@Service
public class ArtworkRepositoryService {

    private final ArtworkRepositoryRepo artworkRepositoryRepo;
    private final UsersRepo usersRepo;
    private final UserUtil userUtil;

    public ArtworkRepositoryService(ArtworkRepositoryRepo artworkRepositoryRepo, UsersRepo usersRepo, UserUtil userUtil){
        this.artworkRepositoryRepo = artworkRepositoryRepo;
        this.usersRepo = usersRepo;
        this.userUtil = userUtil;
    }

    public List<ArtworkRepository> getProjectsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return artworkRepositoryRepo.findAllByUser(user);
    }

    public ArtworkRepository createRepo(String name, String description){
        Instant curr = Instant.now();

        //make util lol

        Users user = userUtil.findUser();

        ArtworkRepository newEntry = new ArtworkRepository(name, description, curr);
        newEntry.setOwner(user);

        artworkRepositoryRepo.save(newEntry);
        return newEntry;
    }

    public ArtworkRepository editRepo(String nameOfRepo, EditingRepoRequestDTO dto){
        Users user = userUtil.findUser();
        ArtworkRepository toBeEdited = artworkRepositoryRepo.findByUserAndName(nameOfRepo, user)
                .orElseThrow(() -> new RuntimeException("Editing: Repo not found"));
        if (dto.getName() != null) {
            toBeEdited.setName(dto.getName());
        }

        if (dto.getDesc() != null) {
            toBeEdited.setDescription(dto.getDesc());
        }

        if (dto.isPublic() != null) {
            toBeEdited.setPublic(dto.isPublic());
        }

        artworkRepositoryRepo.save(toBeEdited);
        return toBeEdited;


    }
}
