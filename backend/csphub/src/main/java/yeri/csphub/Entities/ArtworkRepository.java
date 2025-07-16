package yeri.csphub.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="artwork_repository")
public class ArtworkRepository {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private boolean isPublic = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Artworks> artworks;

    private Instant createdAt;
    private Instant updatedAt;

    public ArtworkRepository(String name, String description, Instant createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public List<Artworks> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artworks> artworks) {
        this.artworks = artworks;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
