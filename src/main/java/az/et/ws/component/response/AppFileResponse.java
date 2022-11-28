package az.et.ws.component.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppFileResponse implements Serializable {
    private Long id;
    private int fileType;
    private String folder;
    private String fileName;
    private String contentType;
}
