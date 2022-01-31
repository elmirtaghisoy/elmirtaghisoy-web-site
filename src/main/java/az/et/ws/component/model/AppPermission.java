package az.et.ws.component.model;

import lombok.Data;

import java.util.Collection;

@Data
public class AppPermission {
    private Long id;
    private String name;
    private boolean active;
    private Collection<AppRole> roles;
}
