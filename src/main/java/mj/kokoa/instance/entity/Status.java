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
@Table(name = "TB_DAILY")
public class Status implements Serializable {
    private static final long serialVersionUID = -9072438034291123631L;

    @EmbeddedId
    private StatusId statusId;

    @Embedded
    private Session session;

    @OneToMany(mappedBy = "tablespaceId.status", cascade = CascadeType.ALL)
    @OrderBy(value = "usedRatio DESC, totalSize DESC, tablespaceId.name ASC")
    private List<Tablespace> tablespaceList;

}
