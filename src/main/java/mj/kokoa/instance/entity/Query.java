package mj.kokoa.instance.entity;

import lombok.Data;
import lombok.ToString;

/**
 * Created by poets11 on 2016. 8. 22..
 */
@Data
@ToString
public class Query {
    private String session;
    private String tablespace;
    private String segment;
}
