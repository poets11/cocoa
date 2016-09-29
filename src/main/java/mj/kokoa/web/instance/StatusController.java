package mj.kokoa.web.instance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Controller
public class StatusController {
    @RequestMapping("/status/main.mj")
    public String statusMain(Model model) {
        return "status/main";
    }
}

