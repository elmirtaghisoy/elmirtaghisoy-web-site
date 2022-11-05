package az.et.ws.service;

import az.et.ws.component.model.AppFile;
import az.et.ws.config.properties.FileProperties;
import az.et.ws.repository.postgres.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static az.et.ws.util.FileUtil.createFile;
import static az.et.ws.util.FileUtil.createFolder;
import static az.et.ws.util.FileUtil.generateFileName;
import static az.et.ws.util.FileUtil.generateFilePath;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    private final Validator validator;

    public String uploadFile(AppFile appFile) {
        Set<ConstraintViolation<AppFile>> violations = validator.validate(appFile);
        if (violations.isEmpty()) {
            String folderPath = generateFolderPath(appFile);
            createFolder(folderPath);
            String fileName = generateFileName(appFile);
            String filePath = generateFilePath(folderPath, fileName);
            createFile(appFile, filePath);
            return filePath;
        } else {
            throw new ConstraintViolationException(violations);
        }
    }

    private String generateFolderPath(AppFile appFile) {
        return StringUtils.cleanPath(
                String.format(
                        "%s/%s/%s",
                        fileProperties.getUploadPath(),
                        appFile.getCategory(),
                        UUID.randomUUID()
                )
        );
    }

}
