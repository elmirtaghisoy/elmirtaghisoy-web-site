package az.et.ws.component.criteria;


import az.et.ws.component.statemachine.blog.BlogState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchCriteria {
    private String header;
    private BlogState state;
}
