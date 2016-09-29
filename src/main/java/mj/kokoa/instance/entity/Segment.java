package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Data
@Entity
@Table(name = "TB_DAILY_TS_SEG")
public class Segment {
    @EmbeddedId
    private SegmentId segmentId;

    private String owner;
    private String type;
    private double usedSize;
}
