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
@ToString(exclude = "tablespaceList")
@Entity
@Table(name = "TB_DAILY")
public class Status {
    @EmbeddedId
    private StatusId statusId;

    @Embedded
    private Session session;

    @OneToMany(mappedBy = "tablespaceId.status", cascade = CascadeType.ALL)
    @OrderBy(value = "usedRatio DESC, totalSize DESC, tablespaceId.name ASC")
    private List<Tablespace> tablespaceList;
}
