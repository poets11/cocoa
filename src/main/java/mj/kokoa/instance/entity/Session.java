package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Embeddable
public class Session {
    @Column(name = "ACTIVE_SESS")
    private int activeCount;
    @Column(name = "TOTAL_SESS")
    private int totalCount;
    @Column(name = "LOCK_SESS")
    private int lockedCount;
}