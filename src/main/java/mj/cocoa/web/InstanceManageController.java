package mj.cocoa.web;

import mj.cocoa.instance.Connection;
import mj.cocoa.instance.Instance;
import mj.cocoa.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/manage.mj")
    public String index(Model model) {
        List<Instance> instanceList = instanceService.getAllInstanceList();
        model.addAttribute("instanceList", instanceList);

        return "manage/main";
    }

    @RequestMapping("/connect-check.mj")
    @ResponseBody
    public Map<String, Object> testConnect(Instance instance) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            boolean valid = instance.getConnection().isValid();
            result.put("result", valid);
        } catch (SQLException e) {
            result.put("result", false);
            result.put("message", e.getMessage());

            e.printStackTrace();
        }

        return result;
    }
}
