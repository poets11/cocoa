package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Entity
@Table(name = "TB_DAILY_TS")
public class Tablespace {
    @EmbeddedId
    private TablespaceId tablespaceId;

    @Column(name = "FILE_CNT")
    private int fileCount;

    private double totalSize;
    private double usedSize;
    private double freeSize;
    @Column(name = "USED_RT")
    private double usedRatio;

    @Transient
    private double variationAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "INST_NO", referencedColumnName = "INST_NO"),
            @JoinColumn(name = "CRE_DT", referencedColumnName = "CRE_DT"),
            @JoinColumn(name = "TS_NM", referencedColumnName = "TS_NM")
    })
    private List<Segment> segmentList;
}
