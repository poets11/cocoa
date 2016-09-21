package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by poets11 on 2016. 9. 20..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class TablespaceId implements Serializable {
    private StatusId statusId;
    @Column(name = "TS_NM")
    private String name;
}
