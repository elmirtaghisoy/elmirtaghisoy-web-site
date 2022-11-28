package az.et.ws.component.request;

import az.et.ws.component.entity.AuthenticationProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Transient;

@Data
public class RegistrationRequest {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private transient String password;
    private String email;
    private String firstName;
    private String lastName;
    private AuthenticationProvider provider;
}
