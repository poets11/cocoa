package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Entity
@Table(name = "TB_DAILY")
public class Status {
    @EmbeddedId
    private StatusId statusId;

    @Embedded
    private Session session;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT")
    })
    @OrderBy(value = "usedRatio DESC, totalSize DESC, tablespaceId.name ASC")
    private List<Tablespace> tablespaceList;

}
