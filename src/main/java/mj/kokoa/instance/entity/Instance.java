package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "statusList")
@Entity
@Table(name = "TB_INST")
public class Instance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INST_NO", updatable = false)
    private Long seq;

    @Column(name = "TNS_NM", unique = true, nullable = false, length = 30)
    private String id;

    @Column(name = "INST_NM", nullable = false, length = 20)
    private String name;

    @Column(length = 30)
    private String host;

    @Column(name = "INST_DESC", length = 50)
    private String description;

    @Column(length = 15)
    private String version;

    @Column(name = "COMPANY", length = 30)
    private String branch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "UPD_DT")

    @Embedded
    private Connection connection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId.instance")
    private List<Status> statusList;
}
