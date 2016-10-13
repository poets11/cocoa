package mj.kokoa.instance.repository;

import mj.kokoa.instance.entity.Segment;
import mj.kokoa.instance.entity.SegmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by poets11 on 2016. 9. 30..
 */
public interface SegmentRepository extends JpaRepository<Segment, SegmentId>, QueryDslPredicateExecutor<Segment> {
}
