package az.et.ws.component.model;

import az.et.ws.component.annotation.IsValidFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AppFile {
    private String objectType;
    @IsValidFile
    private MultipartFile file;
}
