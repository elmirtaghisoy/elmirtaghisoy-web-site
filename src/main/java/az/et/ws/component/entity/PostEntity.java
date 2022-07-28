package az.et.ws.component.entity;

import az.et.ws.component.statemachine.blog.BlogState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "post")
@Getter
@Setter
public class PostEntity extends Auditable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "training.field.header")
    @Column(name = "header")
    private String header;

    @NotBlank(message = "training.field.context")
    @Column(name = "content")
    private String content;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private BlogState state;

}
