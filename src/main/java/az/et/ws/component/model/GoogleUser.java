package az.et.ws.component.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class GoogleUser implements OAuth2User {

    private OAuth2User oAuth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
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


