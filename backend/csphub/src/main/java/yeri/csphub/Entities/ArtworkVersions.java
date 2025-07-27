package yeri.csphub.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
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
    @JoinColumn(name = "artwork_id")
    private Artworks artworkId;

    @Column(columnDefinition = "TEXT", name = "version_description")
    private String version_description;

    @Column(name = "uploaded_at", columnDefinition = "timestamp")
    private Instant uploadedAt;

    @Column(columnDefinition = "TEXT", name = "og_file_name")
    private String ogFileName;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Artworks getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Artworks artworkId) {
        this.artworkId = artworkId;
    }

    public String getVersion_description() {
        return version_description;
    }

    public void setVersion_description(String version_description) {
        this.version_description = version_description;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getOgFileName() {
        return ogFileName;
    }

    public void setOgFileName(String ogFileName) {
        this.ogFileName = ogFileName;
    }
}
