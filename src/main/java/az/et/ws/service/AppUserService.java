package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.repository.postgres.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AppUser loadUserByUsername(String username) {
        return objectMapper.createAppUser(appUserRepository.findByUsername(username));
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
