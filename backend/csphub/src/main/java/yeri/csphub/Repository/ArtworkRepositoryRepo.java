package yeri.csphub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri.csphub.Entities.ArtworkRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtworkRepositoryRepo extends JpaRepository<ArtworkRepository, Long> {

    List<ArtworkRepository> findAllByUsername(String username);
    ArtworkRepository findById(UUID id);
}
