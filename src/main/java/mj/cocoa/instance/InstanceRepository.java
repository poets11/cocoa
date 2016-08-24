package mj.cocoa.instance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by poets11 on 2016. 8. 21..
 */
public interface InstanceRepository extends PagingAndSortingRepository<Instance, Long> {
    Instance findInstanceById(String id);
}
