package az.et.ws.component.response;

import az.et.ws.component.statemachine.blog.BlogState;
import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String header;
    private String content;
    private BlogState state;
}
