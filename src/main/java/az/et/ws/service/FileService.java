package az.et.ws.service;

import az.et.ws.component.entity.FileEntity;
import az.et.ws.component.validatior.ObjectValidator;
import az.et.ws.config.properties.FileProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static az.et.ws.util.FileUtil.createFile;
import static az.et.ws.util.FileUtil.createFolder;
import static az.et.ws.util.FileUtil.generateFileName;
import static az.et.ws.util.FileUtil.generateFilePath;

@Service
@AllArgsConstructor
public class FileService {
    private final FileProperties fileProperties;
    private final ObjectValidator validator;

    public FileEntity uploadFile(MultipartFile file, String objectType) {
        String folderPath = generateFolderPath(objectType, UUID.randomUUID().toString());
        createFolder(folderPath);
        String fileName = generateFileName(file);
        String filePath = generateFilePath(folderPath, fileName);
        createFile(file, filePath);
        return FileEntity.builder()
                .folder(folderPath)
                .category(objectType)
                .fileName(fileName)
                .contentType(FilenameUtils.getExtension(fileName))
                .build();
    }

    public List<FileEntity> uploadFiles(List<MultipartFile> files, String objectType) {
        List<FileEntity> filePaths = new ArrayList<>();
        String folderName = UUID.randomUUID().toString();
        if (files.get(0).getSize() == 0) {
            return null;
        }
        files.forEach(file -> {
            String folderPath = generateFolderPath(objectType, folderName);
            createFolder(folderPath);
            String fileName = generateFileName(file);
            String filePath = generateFilePath(folderPath, fileName);
            createFile(file, filePath);
            filePaths.add(
                    FileEntity.builder()
                            .folder(folderPath)
                            .category(objectType)
                            .fileName(fileName)
                            .contentType(FilenameUtils.getExtension(fileName))
                            .build()
            );
        });
        return filePaths;
    }

    private String generateFolderPath(String objectType, String folderName) {
        return StringUtils.cleanPath(
                String.format(
                        "%s/%s/%s",
                        fileProperties.getUploadPath(),
                        objectType,
                        folderName
                )
        );
    }

}
