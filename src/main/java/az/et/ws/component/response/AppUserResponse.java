package az.et.ws.component.response;

import lombok.Data;

@Data
public class AppUserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
}
