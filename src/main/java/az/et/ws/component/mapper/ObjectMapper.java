package az.et.ws.component.mapper;

import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ObjectMapper {

    public abstract PostEntity r2e(PostRequest request);

    public abstract PostResponse e2r(PostEntity entity);

    public UserDetails appUserToAppUserDetails(AppUserEntity entity) {
        UserDetails userDetails = User.builder()
                .password(entity.getPassword())
                .username(entity.getUsername())
                .authorities(getAuthorities(entity))
                .build();
        return userDetails;
    }

    protected List<GrantedAuthority> getAuthorities(AppUserEntity entity) {
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
