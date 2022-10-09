package az.et.ws.component.mapper;

import az.et.ws.component.entity.AppRoleEntity;
import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.component.entity.AuthenticationProvider;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.entity.TokenEntity;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.request.RegistrationRequest;
import az.et.ws.component.response.AppUserResponse;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.PostResponse;
import az.et.ws.component.views.AppUserView;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ObjectMapper {

    public abstract PostEntity r2e(PostRequest request);

    public abstract PostResponse e2r(PostEntity entity);

    public abstract AppUserResponse v2r(AppUserView view);

    public AppUser generateAppUser(AppUserEntity entity) {
        return new AppUser(entity.getEmail(), entity.getPassword(), entity.isEnabled(), entity.isAccountNonExpired(), entity.isCredentialsNonExpired(), entity.isAccountNonLocked(), getAuthorities(entity), entity.getId(), entity.getName(), entity.getSurname());
    }

    public TokenEntity authResponseToCache(AuthResponse authResponse) {
        return new TokenEntity(authResponse.getAccessToken(), authResponse.getRefreshToken(), authResponse.getEmail(), authResponse.getAuthorities());
    }

    public AppUserEntity createNewUser(RegistrationRequest request, AuthenticationProvider authenticationProvider, AppRoleEntity appRole) {
        AppUserEntity newUser = new AppUserEntity();
        newUser.setName(request.getFirstName());
        newUser.setSurname(request.getLastName());
        newUser.setPassword(new BCryptPasswordEncoder(8).encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
//        newUser.setAuthProvider(request.getAuthProvider());
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setEnabled(true);
        newUser.setRoles(List.of(appRole));
        return newUser;
    }

    private List<GrantedAuthority> getAuthorities(AppUserEntity entity) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        entity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            });
        });

        return authorities;
    }
}
