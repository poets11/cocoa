package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Entity
public class Tablespace {
    @Id
    @GeneratedValue
    private long seq;

    @Column(name = "status_seq")
    private long statusSeq;
    private String name;
    private int fileCount;
    private double totalSize;
    private double usedSize;
    private double freeSize;
    private double usedRatio;

    @Transient
    private double variationSize;
    @Transient
    private double variationRatio;
}
