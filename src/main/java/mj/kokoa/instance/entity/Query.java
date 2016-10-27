package mj.kokoa.instance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by poets11 on 2016. 8. 22..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Query {
    private String session;
    private String tablespace;
    private String segment;
}
