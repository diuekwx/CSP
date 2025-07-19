package yeri.csphub.DTO;

public class CreateRepoRequestDTO {

    private String title;
    private String description;
    private String fileUri;

    public CreateRepoRequestDTO(String fileUri, String description, String title) {
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
