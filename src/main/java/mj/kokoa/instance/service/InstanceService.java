package mj.kokoa.instance.service;

import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.web.dto.InstanceCondition;

import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public interface InstanceService {
    Instance reloadInstanceInfo(String id);
    Instance getInstanceById(String id);
    Instance getInstanceBySeq(Long seq);
    List<Instance> getAllInstanceList(InstanceCondition instanceCondition);
    Instance save(Instance instance);
    boolean deleteInstanceById(String id);
}
