package mj.cocoa.web;

import mj.cocoa.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by poets11 on 2016. 8. 19..
 */
@Controller
public class InstanceManageController {
    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/manage.mj")
    public String index(Model model) {

        return "manage/main";
    }
}
