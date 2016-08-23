package mj.cocoa.instance;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by poets11 on 2016. 8. 21..
 */
public interface InstanceRepository extends CrudRepository<Instance, Long> {
    Instance findInstanceById(String id);
}
