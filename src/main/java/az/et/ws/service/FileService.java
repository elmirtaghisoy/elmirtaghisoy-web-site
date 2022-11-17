package az.et.ws.service;

import az.et.ws.component.entity.FileEntity;
import az.et.ws.component.validatior.ObjectValidator;
import az.et.ws.config.properties.FileProperties;
import az.et.ws.repository.postgres.FileRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final FileRepository fileRepository;
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

    public Resource loadFileAsResource(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow(EntityNotFoundException::new);
        try {
            Resource resource = new UrlResource(Paths.get(String.format("%s/%s", fileEntity.getFolder(), fileEntity.getFileName())).toUri().toString());
            if (resource.exists()) {
                return resource;
            } else {
                throw new EntityNotFoundException();
            }
        } catch (MalformedURLException ex) {
            throw new EntityNotFoundException();
        }
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

    private List<FileEntity> getFilesById(List<Long> fileIds) {
        List<FileEntity> files = fileRepository.findAllById(fileIds);
        if (CollectionUtils.isEmpty(files)) {
            throw new EntityNotFoundException();
        }
        return files;
    }

    public void deleteFiles(List<Long> fileIds) {
        List<FileEntity> files = getFilesById(fileIds);
        files.forEach(file -> {
            try {
                Files.delete(Path.of(String.format("%s/%s", file.getFolder(), file.getFileName())));
                fileRepository.deleteAll(files);
            } catch (IOException e) {
                throw new EntityNotFoundException();
            }
        });
    }


}
