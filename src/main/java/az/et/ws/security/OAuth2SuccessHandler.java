package az.et.ws.security;

import az.et.ws.component.model.GoogleUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        GoogleUser user = (GoogleUser) authentication.getPrincipal();
        System.err.println(user.getEmail());
        System.err.println(user.getName());
        System.err.println(user.getPicture());
        System.err.println(user.getSurname());

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
