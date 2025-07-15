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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Artworks getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Artworks artworkId) {
        this.artworkId = artworkId;
    }

    public ArtworkVersions getArtworkVersionId() {
        return artworkVersionId;
    }

    public void setArtworkVersionId(ArtworkVersions artworkVersionId) {
        this.artworkVersionId = artworkVersionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
