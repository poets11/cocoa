package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;

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
}
