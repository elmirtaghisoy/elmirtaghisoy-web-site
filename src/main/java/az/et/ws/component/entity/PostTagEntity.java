package az.et.ws.component.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "post_tag")
@Getter
@Setter
public class PostTagEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "category.field.tag-name")
    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<PostEntity> posts;

}
