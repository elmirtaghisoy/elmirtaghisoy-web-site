package az.et.ws.component.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
}
