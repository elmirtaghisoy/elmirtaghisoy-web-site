package az.et.ws.config;

import az.et.ws.repository.postgres.GoogleAuthCredentialRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GoogleAuthConfig {

    private final GoogleAuthCredentialRepository googleAuthCredentialRepository;

    @Bean
    public GoogleAuthenticator gAuth() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(googleAuthCredentialRepository);
        return googleAuthenticator;
    }

}
