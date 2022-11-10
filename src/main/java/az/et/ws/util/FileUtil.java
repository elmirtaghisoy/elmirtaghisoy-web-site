package az.et.ws.util;

import az.et.ws.component.exception.FileNotUploaded;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@UtilityClass
public class FileUtil {

    public static String generateFilePath(String folderPath, String fileName) {
        return String.format("%s/%s", folderPath, fileName);
    }

    public static String generateFileName(MultipartFile file) {
        return String.format(
                "%s.%s",
                UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())
        );
    }

    public static void createFile(MultipartFile file, String filePath) {
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileNotUploaded(file.getOriginalFilename());
        }
    }

    public static void createFolder(String folderPath) {
        File uploadDir = new File(folderPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
}
