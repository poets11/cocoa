package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SegmentId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT"),
            @JoinColumn(name = "TS_NM", referencedColumnName = "TS_NM")
    })
    private Tablespace tablespace;

    @Column(name = "SEG_NM", updatable = false, insertable = false)
    private String name;
}
