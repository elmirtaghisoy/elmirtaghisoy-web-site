package az.et.ws.component.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class AuthResponse {
    private Long userId;
    private String username;
    private String accessToken;
    private String refreshToken;
    private Collection<GrantedAuthority> authorities;
}
