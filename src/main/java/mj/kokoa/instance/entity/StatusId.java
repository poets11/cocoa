package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Column(name = "INST_NO")
    private long instanceSeq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRE_DT")
    private Date createdDate;
}
