package az.et.ws.security;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.model.GoogleUserInfo;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.GoogleUserResponse;
import az.et.ws.repository.postgres.AppUserRepository;
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

    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        GoogleUserInfo user = (GoogleUserInfo) authentication.getPrincipal();
        GoogleUserResponse userResponse = new GoogleUserResponse(user.getName(), user.getSurname(), user.getEmail(), user.getPicture());
        if (appUserRepository.existsByEmail(userResponse.getEmail())) {
            AppUser appUser = objectMapper.generateAppUser(appUserRepository.findByEmail(userResponse.getEmail()));
            AuthResponse authResponse = jwtUtil.createTokenAndSession(appUser);
            String json = new Gson().toJson(authResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            String json = new Gson().toJson(userResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(200);
        }

/*
TODO
    eger istifadeci movcuddursa birbasha login olsun (yeni token yaradib return etsin)
    eks halda qeydiyyat sehifesine datalar ile birlikde yonlendirilsin
*/


    }

}
