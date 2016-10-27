package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_DAILY_TS_SEG")
public class Segment {
    @EmbeddedId
    private SegmentId segmentId;
    private long bytes;
//    private String segmentSubtype;
//    private long headerFile;
//    private long headerBlock;
//    private long blocks;
//    private long extents;
//    private long initialExtent;
//    private long nextExtent;
//    private long minExtents;
//    private long maxExtents;
//    private long maxSize;
//    private String retention;
//    private long minretention;
//    private long pctIncrease;
//    private long freelists;
//    private long freelistGroups;
//    private long relativeFno;
//    private String bufferPool;
//    private String flashCache;
//    private String cellFlashCache;
}

