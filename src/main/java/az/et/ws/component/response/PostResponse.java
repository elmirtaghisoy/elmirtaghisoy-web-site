package az.et.ws.component.response;

import az.et.ws.component.statemachine.blog.BlogState;
import lombok.Data;

import java.io.Serializable;

@Data
public class PostResponse implements Serializable {
    private Long id;
    private String header;
    private String content;
    private BlogState state;
}
