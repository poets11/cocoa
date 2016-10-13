package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by poets11 on 2016. 9. 20..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class StatusId implements Serializable {
    private static final long serialVersionUID = 7333850262506867678L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INST_NO")
    private Instance instance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRE_DT", updatable = false)
    private Date createdDate;
}
