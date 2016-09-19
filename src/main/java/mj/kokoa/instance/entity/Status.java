package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Entity
public class Status {
    @Id
    @GeneratedValue
    private long seq;

    @Column(name = "instance_seq")
    private long instanceSeq;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Embedded
    private Session session;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_seq")
    @OrderBy(value = "usedRatio DESC, totalSize DESC, name ASC")
    private List<Tablespace> tablespaceList;
}
