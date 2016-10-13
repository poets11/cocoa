package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by poets11 on 2016. 9. 20..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class TablespaceId implements Serializable {
    private static final long serialVersionUID = -5640463904808803497L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT")
    })
    private Status status;

    @Column(name = "TS_NM", updatable = false, insertable = false)
    private String name;
}
