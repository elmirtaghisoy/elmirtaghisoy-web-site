package az.et.ws.component.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class AuthResponse {
    @JsonIgnore
    private Long userId;
    private String username;
    @ToString.Exclude
    private String accessToken;
    @ToString.Exclude
    private String refreshToken;
    @JsonIgnore
    private Collection<GrantedAuthority> authorities;
}
