package mj.kokoa.instance.repository;

import mj.kokoa.instance.entity.Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by poets11 on 2016. 8. 21..
 */
public interface InstanceRepository extends JpaRepository<Instance, Long>, QueryDslPredicateExecutor<Instance> {
    Instance findById(String id);
}
