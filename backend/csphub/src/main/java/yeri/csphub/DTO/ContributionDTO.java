package yeri.csphub.DTO;

public class ContributionDTO {

    private String title;
    private String description;
    private String fileUri;

    public ContributionDTO(String title, String description, String fileUri) {
        this.fileUri = fileUri;
        this.description = description;
        this.title = title;
    }

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

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
