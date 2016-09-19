package mj.kokoa.instance.repository;

import mj.kokoa.instance.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by poets11 on 2016. 8. 22..
 */
public interface StatusRepository extends JpaRepository<Status, Long>, QueryDslPredicateExecutor<Status> {
}
