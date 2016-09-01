package mj.kokoa.instance.web;

import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@RestController
public class InstanceAPIController {
    @Autowired
    private InstanceService instanceService;

    @RequestMapping(value = "/instance/{id}.mj", method = RequestMethod.POST)
    public Map<String, Object> saveInstanceInfo(Instance instance, @PathVariable String id) {
        Map<String, Object> responseData = new HashMap<String, Object>();

        try {
            instance.setUpdatedDate(new Date());

            instanceService.save(instance);

            responseData.put("result", true);
        } catch (Exception e) {
            responseData.put("result", false);
            responseData.put("message", e.getMessage());
        }

        return responseData;
    }

    @RequestMapping(value = "/instance/{id}.mj", method = RequestMethod.GET)
    public Object getInstanceInfo(@PathVariable String id) {
        Instance instance = instanceService.getInstanceById(id);
        return instance;
    }

    @RequestMapping(value = "/instance/{id}.mj", method = RequestMethod.DELETE)
    public Object deleteInstance(@PathVariable  String id) {
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
}