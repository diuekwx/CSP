package yeri.csphub.DTO;

import yeri.csphub.Entities.ArtworkVersions;

import java.time.Instant;
import java.util.UUID;

public class ArtworkVersionDTO {
    private UUID id;
    private String fileUrl;
    private String ogFileName;
    private String versionDescription;
    private Instant uploadedAt;

    public ArtworkVersionDTO(ArtworkVersions version) {
        this.id = version.getId();
        this.ogFileName = version.getOgFileName();
        this.fileUrl = version.getFileUrl();
        this.versionDescription = version.getVersion_description();
        this.uploadedAt = version.getUploadedAt();
    }

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

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
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
