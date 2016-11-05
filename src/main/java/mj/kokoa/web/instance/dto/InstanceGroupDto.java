package mj.kokoa.web.instance.dto;

import lombok.Data;
import mj.kokoa.instance.entity.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poets11 on 2016. 10. 19..
 */
@Data
public class InstanceGroupDto {
    private String branch;
    private List<Instance> instanceList = new ArrayList<Instance>();
}
