package yeri.csphub.DTO;

import yeri.csphub.Entities.ArtworkVersions;
import java.util.List;

public class ArtworkDTO {
    private String title;
    private String description;
    private List<ArtworkVersions> files;
    private boolean isEmpty;
    private boolean isPublic;
    private String owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ArtworkVersions> getFiles() {
        return files;
    }

    public void setFiles(List<ArtworkVersions> files) {
        this.files = files;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
