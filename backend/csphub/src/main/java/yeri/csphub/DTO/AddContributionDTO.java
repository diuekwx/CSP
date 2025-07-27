package yeri.csphub.DTO;

import yeri.csphub.Entities.ArtworkVersions;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Entities.Users;

public class AddContributionDTO {
    private Users user;
    private Artworks artwork;
    private ArtworkVersions versions;
    private String type;

    public AddContributionDTO(Users user, Artworks artwork, ArtworkVersions versions, String type) {
        this.user = user;
        this.artwork = artwork;
        this.versions = versions;
        this.type = type;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Artworks getArtwork() {
        return artwork;
    }

    public void setArtwork(Artworks artwork) {
        this.artwork = artwork;
    }

    public ArtworkVersions getVersions() {
        return versions;
    }

    public void setVersions(ArtworkVersions versions) {
        this.versions = versions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
