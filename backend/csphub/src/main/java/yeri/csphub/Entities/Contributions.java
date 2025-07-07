package yeri.csphub.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Contributions {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artworks artworkId;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private ArtworkVersions artworkVersionId;

    @Column(columnDefinition = "TEXT")
    private String type;

    private Timestamp timestamp;
}
