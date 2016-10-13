package mj.kokoa.instance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "TB_INST")
public class Instance implements Serializable {
    private static final long serialVersionUID = -6366327113137421353L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INST_NO", updatable = false)
    private Long seq;

    @Column(name = "TNS_NM", unique = true, nullable = false)
    private String id;

    @Column(name = "INST_NM", nullable = false)
    private String name;

    private String host;

    @Column(name = "INST_DESC")
    private String description;
    private String version;

    @Column(name = "COMPANY")
    private String branch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "UPD_DT")

    @Embedded
    private Connection connection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId.instance")
    private List<Status> statusList;
}
