package yeri.csphub.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "artwork_versions")
public class ArtworkVersions {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "TEXT", name = "file_url")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "artworks_id")
    private Artworks artworkId;

    @Column(columnDefinition = "TEXT", name = "version_description")
    private String version_description;

    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;
}
