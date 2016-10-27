package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by poets11 on 2016. 9. 20..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StatusId implements Serializable {
    private static final long serialVersionUID = 3891152139802459814L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INST_NO")
    private Instance instance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRE_DT", updatable = false)
    private Date createdDate;
}
