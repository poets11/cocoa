package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by poets11 on 2016. 9. 20..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TablespaceId implements Serializable {
    private static final long serialVersionUID = -6150336544389311657L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT")
    })
    private Status status;

    @Column(name = "TS_NM", updatable = false, insertable = false, length = 50)
    private String name;
}
