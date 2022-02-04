package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.request.RefreshTokeRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.repository.AppUserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return objectMapper.appUserToAppUserDetails(appUserRepository.findByUsername(username));
    }

//    public AuthResponse login(LoginRequest request) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        return generateToken((UserDetails) authentication.getPrincipal());
//    }
//
//    public AuthResponse refreshToken(RefreshTokeRequest request) {
//        String refreshToken = request.getRefreshToken();
//        if (Objects.nonNull(refreshToken) && refreshToken.startsWith("Bearer ")) {
//            try {
//                String token = refreshToken.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(token);
//                String username = decodedJWT.getSubject();
//                User user = (User) loadUserByUsername(username);
//                return generateToken(user);
//            } catch (Exception exception) {
////                response.sendError(FORBIDDEN.value());
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//        return null;
//    }



}
