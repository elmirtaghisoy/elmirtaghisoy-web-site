package az.et.ws.security;

import az.et.ws.component.entity.TokenEntity;
import az.et.ws.component.exception.InvalidTokenException;
import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.repository.redis.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${secret.jwt}")
    private String jwtSecret;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public byte[] getJwtSecret() {
        return jwtSecret.getBytes();
    }

    public AuthResponse createTokenAndSession(Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        AuthResponse authResponse = AuthResponse.builder()
                .userId(appUser.getId())
                .username(appUser.getUsername())
                .accessToken(accessToken(appUser))
                .refreshToken(refreshToken(appUser))
                .authorities(appUser.getAuthorities())
                .build();
        tokenRepository.save(objectMapper.authResponseToCache(authResponse));
        return authResponse;
    }

    public TokenEntity verifyToken(String authorizationHeader) throws InvalidTokenException {
        String token = authorizationHeader.substring("Bearer ".length());
        if (findAuthToken(token).isPresent()) {
            return findAuthToken(token).get();
        } else {
            throw new InvalidTokenException();
        }
    }

    public void invalidateToken(String authorizationHeader) throws InvalidTokenException {
        if (Objects.nonNull(authorizationHeader)) {
            String token = authorizationHeader.substring("Bearer ".length());
            if (findAuthToken(token).isPresent()) {
                tokenRepository.deleteById(token);
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    protected Optional<TokenEntity> findAuthToken(String token) {
        return tokenRepository.findById(token);
    }

    private String accessToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(getJwtSecret()));
    }

    private String refreshToken(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(getJwtSecret()));
    }

}