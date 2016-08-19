package mj.cocoa.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@RestController
public class InstanceRestController {
    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/instance/{id}/reload.mj")
    public Map<String, Object> realodInstanceInfo(@PathVariable String id) {
        Map<String, Object> responseData = new HashMap<String, Object>();

        try {
            boolean result = instanceService.reloadInstanceInfo(id);
            responseData.put("result", result);
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("message", e.getMessage());
        }

        return responseData;
    }

    @RequestMapping("/instance/{id}/session/latest.mj")
    public Session getLatestSessionInfo(@PathVariable String id) {
        return new Session(25, 40, 0);
    }

    @RequestMapping("/instance/{id}/tablespace/latest.mj")
    public Tablespace getLatestTablespaceInfo(@PathVariable String id) {
        return null;
    }
}
