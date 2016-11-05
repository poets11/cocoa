package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_DAILY_TS_SEG")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "INST_NO")
    private Long instanceNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRE_DT")
    private Date createdDate;

    @Column(name = "TS_NM", length = 50)
    private String tablespaceName;

    private long bytes;

    @Column(name = "SEG_NM", length = 50)
    private String name;

    @Column(name = "SEG_OWN", length = 50)
    private String owner;

    @Column(name = "SEG_TYP", length = 20)
    private String segmentType;

    @Column(length = 100)
    private String partitionName;

    @PrePersist
    public void initDefaultValue() {
        if (StringUtils.hasText(partitionName) == false) {
            partitionName = "none";
        }
    }
}

