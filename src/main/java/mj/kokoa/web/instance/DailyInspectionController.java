package mj.kokoa.web.instance;

import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.service.InstanceService;
import mj.kokoa.instance.web.vo.InstanceCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Controller
public class DailyInspectionController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/")
    public String index() {
        logger.debug("인덱스로 접근 -> 일일점검 메인페이지로 리다이렉트");

        return "redirect:/daily-inspection/main.mj";
    }

    @RequestMapping("/daily-inspection/main.mj")
    public String dailyMain(Model model, InstanceCondition instanceCondition) {
        instanceCondition.setSort(new Sort(Sort.Direction.ASC, "branch", "host", "id"));

        List<Instance> instanceList = instanceService.getAllInstanceList(instanceCondition);
        logger.debug("조회된 인스턴스 수 : {}", instanceList.size());

        model.addAttribute("instanceList", instanceList);

        return "daily/main";
    }

    @RequestMapping("/daily-inspection/reload-instance.mj")
    public String reloadInstance(String id, Model model) {
        try {
            logger.debug("인스턴스({}) 리로드, 스니팻 조회", id);

            instanceService.reloadInstanceInfo(id);

            Instance instance = instanceService.getInstanceById(id);
            model.addAttribute("curInst", instance);

            return "daily/success-snippet";
        } catch (Exception e) {
            logger.error("인스턴스({}) 리로드 실패, 에러 : ", e.getMessage(), e);

            Instance instance = instanceService.getInstanceById(id);
            model.addAttribute("curInst", instance);
            model.addAttribute("message", e.getMessage());

            return "daily/fail-snippet";
        }
    }
}

