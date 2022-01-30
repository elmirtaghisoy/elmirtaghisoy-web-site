package az.et.ws.component.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "user.field.username")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "user.field.password")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "user.field.email")
    @Column(name = "email")
    private String email;

}
