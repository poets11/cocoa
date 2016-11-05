package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SegmentId implements Serializable {
    private static final long serialVersionUID = -8933891049035230878L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT"),
            @JoinColumn(name = "TS_NM", referencedColumnName = "TS_NM")
    })
    private Tablespace tablespace;

    @Column(name = "SEG_NM", updatable = false, insertable = false, length = 50)
    private String name;

    @Column(name = "SEG_OWN", updatable = false, insertable = false, length = 50)
    private String owner;

    @Column(name = "SEG_TYP", updatable = false, insertable = false, length = 20)
    private String segmentType;

    @Column(updatable = false, insertable = false, length = 100)
    private String partitionName;
}
