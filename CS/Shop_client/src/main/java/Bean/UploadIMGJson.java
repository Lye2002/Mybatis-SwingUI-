package Bean;

import lombok.Data;

@Data
public class UploadIMGJson {
    private String title;
    private String fileName;
    private String originalFilename;
    private String uploadedFilePath;
}
