package az.et.ws.component.criteria;


import az.et.ws.component.statemachine.blog.BlogState;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PostSearchCriteria {
    private String header;
    private BlogState state;
}
