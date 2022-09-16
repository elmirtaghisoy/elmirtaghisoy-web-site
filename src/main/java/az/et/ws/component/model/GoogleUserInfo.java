package az.et.ws.component.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2User {

    private OAuth2User oAuth2User;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("given_name");
    }

    public String getSurname() {
        return oAuth2User.getAttribute("family_name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getPicture() {
        return oAuth2User.getAttribute("picture");
    }
}


