package az.et.ws.component.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostTagResponse implements Serializable {
    private Long id;
    private String tagName;
}
