package mj.kokoa.instance.entity;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Data
@Embeddable
public class Session {
    private int activeCount;
    private int totalCount;
    private int lockedCount;
}