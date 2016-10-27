package mj.kokoa.instance.web.dto;

import lombok.Data;
import mj.kokoa.instance.entity.Connection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by poets11 on 2016. 10. 17..
 */
@Data
public class InstanceDto implements Serializable {
    private static final long serialVersionUID = 7146163316594780220L;

    private Long seq;
    private String id;
    private String name;
    private String host;
    private String description;
    private String version;
    private String branch;
    private Date updatedDate;
    private Connection connection;
}
