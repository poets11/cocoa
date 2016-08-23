package mj.cocoa.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@RestController
public class InstanceRestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InstanceService instanceService;

    @RequestMapping("/instance/save.mj")
    public Map<String, Object> saveInstanceInfo(Instance instance) {
        Map<String, Object> responseData = new HashMap<String, Object>();

        try {
            instance.setUpdatedDate(new Date());

            instance = instanceService.save(instance);
            logger.debug("신규 저장된 인스턴스 정보 : " + instance);

            responseData.put("result", true);
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("message", e.getMessage());
        }

        return responseData;
    }

    @RequestMapping("/instance/{id}.mj")
    public Object getInstanceInfo(@PathVariable String id) {
        Instance instance = instanceService.getInstanceById(id);
        return instance;
    }

    @RequestMapping("/instance/delete.mj")
    public Object deleteInstance(String id) {
        Map<String, Object> responseData = new HashMap<String, Object>();

        try {
            instanceService.deleteInstanceById(id);
            responseData.put("result", true);
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("message", e.getMessage());
        }

        return responseData;
    }

    @RequestMapping("/instance/{id}/reload.mj")
    public Map<String, Object> realodInstanceInfo(@PathVariable String id) {
        Map<String, Object> responseData = new HashMap<String, Object>();

        try {
            Instance instance = instanceService.reloadInstanceInfo(id);
            if (instance != null) {
                responseData.put("result", true);
            } else {
                responseData.put("result", false);
            }
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("message", e.getMessage());
        }

        return responseData;
    }
}
