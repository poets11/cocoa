package mj.kokoa.instance.repository;

import mj.kokoa.instance.entity.Tablespace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by poets11 on 2016. 8. 22..
 */
public interface TablespaceRepository extends JpaRepository<Tablespace, Long>, QueryDslPredicateExecutor<Tablespace> {
}
