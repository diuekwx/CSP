package yeri.csphub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepo extends JpaRepository<Artworks, Long> {
    List<Artworks> findAllByUser(Users user);
    Optional<Artworks> findByTitle(String title);
    Optional<Artworks> findByUserAndName(String repoName, Users user);
}
