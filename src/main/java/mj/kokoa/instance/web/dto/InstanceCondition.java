package mj.kokoa.instance.web.dto;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import mj.kokoa.common.Predicatable;
import mj.kokoa.instance.entity.QInstance;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * Created by poets11 on 2016. 9. 6..
 */
public class InstanceCondition implements Predicatable {
    private Sort sort;
    private String branch;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public Predicate convertPredicate() {
        if (StringUtils.hasText(branch) == false) {
            return null;
        }

        QInstance instance = QInstance.instance;
        BooleanExpression eq = instance.branch.eq(branch);

        return eq;
    }
}
