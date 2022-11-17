package az.et.ws.component.response;

import lombok.Data;

@Data
public class AppFileResponse {
    private Long id;
    private int fileType;
    private String folder;
    private String fileName;
    private String contentType;
}
