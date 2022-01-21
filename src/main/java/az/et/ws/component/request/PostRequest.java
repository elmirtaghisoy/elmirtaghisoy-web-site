package az.et.ws.component.request;

import lombok.Data;

@Data
public class PostRequest {
    private Long id;
    private String header;
    private String content;
}
