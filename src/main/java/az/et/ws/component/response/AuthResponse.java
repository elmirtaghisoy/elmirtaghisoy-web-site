package az.et.ws.component.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class AuthResponse {
    private Long userId;
    private String username;
    @ToString.Exclude
    private String accessToken;
    @ToString.Exclude
    private String refreshToken;
    private Collection<GrantedAuthority> authorities;
}
