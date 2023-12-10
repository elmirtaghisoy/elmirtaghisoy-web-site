package az.et.ws.component.model;

import az.et.ws.component.statemachine.blog.BlogState;
import lombok.Data;

@Data
public class BlogCsv {
    private String header;
    private String content;
    private String state;
}
