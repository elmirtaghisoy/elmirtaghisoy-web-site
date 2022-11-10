package az.et.ws.api;

import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileApi {

    // uploadFile
    // uploadFiles
    // deleteFile
    // downloadFile
    private final FileService fileService;

    @PostMapping("/file/upload")
    public SuccessResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        return SuccessResponse.create(fileService.uploadFile(file, "UNDEFINED"));
    }

    @PostMapping("/files/upload")
    public SuccessResponse<Object> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        return SuccessResponse.create(fileService.uploadFiles(files, "UNDEFINED"));
    }

}
