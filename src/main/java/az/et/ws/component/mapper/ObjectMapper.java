package az.et.ws.component.mapper;

import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.entity.TokenEntity;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.model.Token;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ObjectMapper {

    public abstract PostEntity r2e(PostRequest request);

    public abstract PostResponse e2r(PostEntity entity);

    public abstract TokenEntity m2e(Token token);

    public AppUser createAppUser(AppUserEntity entity) {
        return new AppUser(
                entity.getUsername(),
                entity.getPassword(),
                entity.isEnabled(),
                entity.isAccountNonExpired(),
                entity.isCredentialsNonExpired(),
                entity.isAccountNonLocked(),
                getAuthorities(entity),
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
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
