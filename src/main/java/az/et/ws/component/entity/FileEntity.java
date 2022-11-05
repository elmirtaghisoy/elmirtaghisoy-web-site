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

@Entity
@Table(name = "files")
@ToString
@Getter
@Setter
public class FileEntity extends Auditable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_type")
    private int fileType;

    @Column(name = "folder")
    private String folder;

    @Column(name = "category")
    private String category;

    @Column(name = "ref_object_id")
    private Long refObjectId;

}
