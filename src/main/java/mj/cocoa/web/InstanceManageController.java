package mj.cocoa.web;

import mj.cocoa.instance.Connection;
import mj.cocoa.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
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

        return "manage/main";
    }

    @RequestMapping("/connect-check.mj")
    @ResponseBody
    public Map<String, Object> testConnect(Connection conInfo) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            java.sql.Connection connection = conInfo.getConnection();

            if (connection != null) {
                result.put("result", true);
            } else {
                result.put("result", false);
            }
        } catch (SQLException e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
}
