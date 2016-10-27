package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "segmentList")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segmentId.tablespace")
    private List<Segment> segmentList;
}
