package az.et.ws.component.request;

import az.et.ws.component.entity.AuthenticationProvider;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private AuthenticationProvider provider;
}
