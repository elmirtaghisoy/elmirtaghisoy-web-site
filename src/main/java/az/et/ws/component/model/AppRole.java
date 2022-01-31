package az.et.ws.component.model;

import lombok.Data;

import java.util.Collection;

@Data
public class AppRole {
    private Long id;
    private String name;
    private boolean isActive;
    private Collection<AppUser> users;
    private Collection<AppPermission> permissions;
}
