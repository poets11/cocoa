package mj.kokoa.web.instance;

import mj.kokoa.common.ExceptionUtil;
import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.service.InstanceService;
import mj.kokoa.instance.web.dto.InstanceCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poets11 on 2016. 8. 19..
 */
@Controller
public class InstanceManageController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/instance-manage/main.mj")
    public String manageMain(Model model, InstanceCondition instanceCondition) {
        instanceCondition.setSort(new Sort(Sort.Direction.ASC, "branch", "host", "id"));

        List<Instance> instanceList = instanceService.getAllInstanceList(instanceCondition);
        logger.debug("조회된 인스턴스 수 : " + instanceList.size());

        model.addAttribute("instanceList", instanceList);

        return "manage/main";
    }

    @RequestMapping("/instance-manage/test-connection.mj")
    @ResponseBody
    public Map<String, Object> connectionTest(Instance instance) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            boolean valid = instance.getConnection().isValid();
            logger.debug("인스턴스({}) 연결 테스트 결과 : {}", instance.getId(), valid);

            result.put("result", valid);
        } catch (SQLException e) {
            logger.error("인스턴스({}) 연결 테스트 실패, 에러 : ", instance.getId(), e.getMessage(), e);

            result.put("result", false);
            result.put("message", e.getMessage());
            result.put("stackTrace", ExceptionUtil.getStackTrace(e));
        }

        return result;
    }
}
