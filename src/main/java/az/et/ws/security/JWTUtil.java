package az.et.ws.security;

import az.et.ws.component.constraints.Status;
import az.et.ws.component.exception.BadLoginRequestException;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.ErrorResponse;
import az.et.ws.component.response.SuccessResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JWTUtil {

    protected static LoginRequest getRequestBody(HttpServletRequest request) {
        LoginRequest loginRequest;
        try {
            loginRequest = new ObjectMapper().readValue(IOUtils.toString(request.getReader()), LoginRequest.class);
        } catch (IOException e) {
            throw new BadLoginRequestException();
        }
        return loginRequest;
    }

    protected static String accessToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    protected static String refreshToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    protected static void buildSuccessResponse(HttpServletResponse httpServletResponse, UserDetails user) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                httpServletResponse.getOutputStream(),
                SuccessResponse.create(
                        AuthResponse.builder()
                                .accessToken(accessToken(user))
                                .refreshToken(refreshToken(user))
                                .build()
                )
        );
    }

    protected static void buildErrorResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                httpServletResponse.getOutputStream(),
                ErrorResponse.error(Status.WRONG_USERNAME_OR_PASSWORD)
        );
    }
}