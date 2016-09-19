package mj.kokoa.common;

import com.querydsl.core.types.Predicate;

/**
 * Created by poets11 on 2016. 9. 18..
 */
public interface Predicatable {
    Predicate convertPredicate();
}
