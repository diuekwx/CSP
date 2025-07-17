package yeri.csphub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri.csphub.Entities.ArtworkRepository;
import yeri.csphub.Entities.Users;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtworkRepositoryRepo extends JpaRepository<ArtworkRepository, Long> {

    List<ArtworkRepository> findAllByUser(Users user);
    ArtworkRepository findById(UUID id);
}
