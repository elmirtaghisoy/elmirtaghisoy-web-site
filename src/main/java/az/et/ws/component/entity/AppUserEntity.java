package az.et.ws.component.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "app_user")
@ToString
@Getter
@Setter
public class AppUserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //    delete this column
//    @NotBlank(message = "user.field.username")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "user.field.password")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "user.field.email")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "user.field.firstName")
    @Column(name = "first_name")
    private String name;

    @NotBlank(message = "user.field.lastName")
    @Column(name = "last_name")
    private String surname;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @NotNull
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @NotNull
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "auth_provider")
//    private AuthenticationProvider authProvider;

    @Column(name = "google_secret_key")
    private String googleSecretKey;

    @Column(name = "google_validation_code")
    private Integer googleValidationCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<AppRoleEntity> roles;


}
