package az.elmirtaghisoywebsite.component.request;

import lombok.Data;

@Data
public class PostRequest {
    private Long id;
    private String header;
    private String content;
}
