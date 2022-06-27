package az.et.ws.security;

import az.et.ws.component.model.GoogleUser;
import az.et.ws.component.response.GoogleUserResponse;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
/*
    eger istifadeci movcuddursa birbasha login olsun (yeni token flan return etsin)
    eks halda qeydiyyat sehifesine datalar ile birlikde yonlendirilsin
*/
        GoogleUser user = (GoogleUser) authentication.getPrincipal();
        GoogleUserResponse userResponse = new GoogleUserResponse(user.getName(), user.getSurname(), user.getEmail(), user.getPicture());
        String json = new Gson().toJson(userResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.setStatus(200);
    }

}
