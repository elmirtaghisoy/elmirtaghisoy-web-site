package az.et.ws.util;

import az.et.ws.component.exception.FileNotUploaded;
import az.et.ws.component.model.AppFile;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@UtilityClass
public class FileUtil {

    public static String generateFilePath(String folderPath, String fileName) {
        return String.format("%s/%s", folderPath, fileName);
    }

    public static String generateFileName(AppFile appFile) {
        return String.format(
                "%s.%s",
                UUID.randomUUID(),
                FilenameUtils.getExtension(appFile.getFile().getOriginalFilename())
        );
    }

    public static void createFile(AppFile appFile, String filePath) {
        try {
            appFile.getFile().transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileNotUploaded(appFile.getFile().getOriginalFilename());
        }
    }

    public static void createFolder(String folderPath) {
        File uploadDir = new File(folderPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
}
