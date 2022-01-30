package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUserDetails;
import az.et.ws.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDetails appUserDetails = findByUsername(username);
        return User.builder()
                .password(appUserDetails.getPassword())
                .username(appUserDetails.getUsername())
                .accountExpired(appUserDetails.isAccountNonExpired())
                .accountLocked(appUserDetails.isAccountNonLocked())
                .credentialsExpired(appUserDetails.isCredentialsNonExpired())
                .disabled(appUserDetails.isEnabled())
                .authorities(appUserDetails.getAuthorities())
                .build();
    }

    public AppUserDetails findByUsername(String username) {
        return objectMapper.appUserToAppUserDetails(appUserRepository.findByUsername(username));
    }


}
