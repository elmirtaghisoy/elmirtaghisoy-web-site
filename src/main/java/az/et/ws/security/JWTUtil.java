package az.et.ws.security;

import az.et.ws.component.constraints.Status;
import az.et.ws.component.entity.TokenEntity;
import az.et.ws.component.exception.BadLoginRequestException;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.model.Token;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.ErrorResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.repository.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${secret.jwt}")
    private String jwtSecret;
    private final TokenRepository tokenRepository;
    private final az.et.ws.component.mapper.ObjectMapper objectMapper;

    @PostConstruct
    public byte[] getJwtSecret() {
        return jwtSecret.getBytes();
    }

    protected LoginRequest getRequestBody(HttpServletRequest request) {
        LoginRequest loginRequest;
        try {
            loginRequest = new ObjectMapper().readValue(IOUtils.toString(request.getReader()), LoginRequest.class);
        } catch (IOException e) {
            throw new BadLoginRequestException();
        }
        return loginRequest;
    }

    protected void buildSuccessResponse(HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                httpServletResponse.getOutputStream(),
                SuccessResponse.create(createTokenAndSession(authentication))
        );
    }

    protected void buildErrorResponse(HttpServletResponse httpServletResponse, Status status) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                httpServletResponse.getOutputStream(),
                ErrorResponse.error(status)
        );
    }

    protected String accessToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(getJwtSecret()));
    }

    protected String refreshToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(getJwtSecret()));
    }

    protected Token generateToken(AppUser appUser) {
        return Token.builder().accessToken(accessToken(appUser)).refreshToken(refreshToken(appUser)).id(appUser.getId()).build();
    }

    protected AuthResponse createTokenAndSession(Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        AuthResponse authResponse = AuthResponse.builder()
                .token(generateToken(appUser))
                .build();
        tokenRepository.save(objectMapper.m2e(authResponse.getToken()));
        return authResponse;
    }

    protected Optional<TokenEntity> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

    protected void verifyToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authorizationHeader) throws IOException, ServletException {
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(getJwtSecret())).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        if (findByAccessToken(token).isPresent()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else {
            throw new TokenExpiredException("HOZU XETA");
        }
    }
}