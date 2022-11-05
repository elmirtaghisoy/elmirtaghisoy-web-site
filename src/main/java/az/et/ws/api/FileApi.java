package az.et.ws.api;

import az.et.ws.component.model.AppFile;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileApi {

    // uploadFile
    // deleteFile
    // downloadFile
    private final FileService fileService;

    @PostMapping("/file/upload")
    public SuccessResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        AppFile appFile = new AppFile();
        appFile.setCategory("BLOG");
        appFile.setFolder("blogs");
        appFile.setFile(file);
        return SuccessResponse.create(fileService.uploadFile(appFile));
    }


    /*
     * TODO
     * 1. Validate file is null or not +
     * 2. Exception handling for files +
     * 2. MultipleUpload               -
     * 3. Save file(s) paths in db     -
     */

}
