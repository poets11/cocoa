package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Entity
public class Instance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    private String host;
    private String description;
    private String version;
    private String branch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Embedded
    private Connection connection;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_seq")
    @OrderBy(value = "createdDate DESC")
    private List<Status> statusList;
}
