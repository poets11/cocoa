package mj.kokoa.instance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "TB_DAILY_TS")
public class Tablespace implements Serializable {
    private static final long serialVersionUID = 4949983347628145268L;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segmentId.tablespace")
    private List<Segment> segmentList;
}
