package az.et.ws.repository.postgres;

import az.et.ws.component.entity.AppUserEntity;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleAuthCredentialRepository implements ICredentialRepository {

    private final AppUserRepository appUserRepository;

    @Override
    public String getSecretKey(String email) {
        return appUserRepository.findByEmail(email).getGoogleSecretKey();
    }

    @Override
    public void saveUserCredentials(String email, String secretKey, int validationCode, List<Integer> scratchCodes) {
        AppUserEntity appUser = appUserRepository.findByEmail(email);
        appUser.setGoogleSecretKey(secretKey);
        appUser.setGoogleValidationCode(validationCode);
        appUserRepository.save(appUser);
    }

}
