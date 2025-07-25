package yeri.csphub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkVersionRepo extends JpaRepository<ArtworkVersions, Long> {
    List<ArtworkVersions> findAllByArtworkId(Artworks artworks);
}
