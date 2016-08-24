package mj.cocoa.web;

import mj.cocoa.instance.Instance;
import mj.cocoa.instance.InstanceService;
import mj.cocoa.instance.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Controller
public class DailyCheckController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/daily-check.mj")
    public String index(Model model) {
        List<Instance> instanceList = instanceService.getAllInstanceList();
        model.addAttribute("instanceList", instanceList);

        return "daily/main";
    }

    @RequestMapping("/daily-check/{id}/snippet.mj")
    public String snippet(@PathVariable String id, Model model) {
        try {
            instanceService.reloadInstanceInfo(id);

            Instance instance = instanceService.getInstanceById(id);
            model.addAttribute("curInst", instance);
        } catch (Exception e) {
            Instance instance = instanceService.getInstanceById(id);
            model.addAttribute("curInst", instance);
            model.addAttribute("message", e.getMessage());

            return "daily/fail-snippet";
        }

        return "daily/success-snippet";
    }
}

