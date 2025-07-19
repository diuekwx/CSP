package yeri.csphub.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Artworks {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @Column(columnDefinition = "TEXT")
    private String title;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "project_id", nullable = false)
//    private ArtworkRepository project;

    // TODO:EDIT SUPABASE
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", columnDefinition = "timestamp")
    private Instant createdAt;

    private boolean isPublic;

    public Artworks(Users userId, String title, String description, Instant createdAt, boolean isPublic) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

//    public @NotNull ArtworkRepository getProject() {
//        return project;
//    }
//
//    public void setProject(@NotNull ArtworkRepository project) {
//        this.project = project;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
