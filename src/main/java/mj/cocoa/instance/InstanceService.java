package mj.cocoa.instance;

import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public interface InstanceService {
    boolean reloadInstanceInfo(String id);
    Instance getInstanceById(String id);
    List<Instance> getAllInstanceList();
}
