package az.et.ws.api;

import az.et.ws.component.exception.UndefinedFileTypeException;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.FileService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class FileApi {
    private final FileService fileService;
    private final JobLauncher jobLauncher;
    private final Job firstJob;

    @PostMapping("/file/upload")
    public SuccessResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        return SuccessResponse.create(fileService.uploadFile(file, "UNDEFINED"));
    }

    @PostMapping("/files/upload")
    public SuccessResponse<Object> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        return SuccessResponse.create(fileService.uploadFiles(files, "UNDEFINED"));
    }

    @GetMapping("/file/load/{fileId}")
    public ResponseEntity<Resource> loadFile(
            @PathVariable Long fileId,
            HttpServletRequest request
    ) {
        Resource file = fileService.loadFileAsResource(fileId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new UndefinedFileTypeException();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        Objects.isNull(contentType) ? "application/octet-stream" : contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("%s%s%s", "attachment; filename=\"", file.getFilename(), "\""))
                .body(file);
    }

    @PostMapping("/files/delete")
    public SuccessResponse<ObjectUtils.Null> deleteFiles(
            @RequestParam List<Long> fileIds
    ) {
        fileService.deleteFiles(fileIds);
        return SuccessResponse.delete();
    }


    @PostMapping("/files/upload/excel")
    public SuccessResponse<ObjectUtils.Null> uploadExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            jobLauncher.run(firstJob, new JobParameters());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SuccessResponse.ok();
    }
}
