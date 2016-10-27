package mj.kokoa.web.instance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.repository.InstanceRepository;
import mj.kokoa.web.instance.dto.status.InstanceGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Controller
public class StatusController {
    @Autowired
    private InstanceRepository instanceRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping("/status/main.mj")
    public String statusMain(Model model) throws JsonProcessingException {
        List<InstanceGroupDto> instanceGroupDtoList = new ArrayList<InstanceGroupDto>();
        Map<String, Map<String, Object>> instanceSummaryMap = new HashMap<String, Map<String, Object>>();

        List<Instance> instanceList = instanceRepository.findAll(new Sort(Sort.Direction.ASC, "branch", "id"));
        for (int i = 0; i < instanceList.size(); i++) {
            Instance instance = instanceList.get(i);

            putInstanceSummary(instanceSummaryMap, instance);

            InstanceGroupDto group = findInstanceGroup(instanceGroupDtoList, instance.getBranch());
            group.getInstanceList().add(instance);
        }


        String summary = mapper.writeValueAsString(instanceSummaryMap);

        model.addAttribute("instanceSummary", summary);
        model.addAttribute("instanceGroupList", instanceGroupDtoList);

        return "status/main";
    }

    private void putInstanceSummary(Map<String, Map<String, Object>> instanceSummaryMap, Instance instance) {
        final String format = "<b>[%s] %s(%s)</b><br>" +
                "%s<br>" +
                "<b>Host : </b>%s<br>" +
                "<b>Address : </b>%s:%s:s<br>" +
                "<b>Version : %s";

        String sumary = String.format(format, instance.getBranch(), instance.getName(), instance.getId(),
                instance.getDescription(),
                instance.getHost(),
                instance.getConnection().getIp(), instance.getConnection().getPort(), instance.getConnection().getSid(),
                instance.getVersion()
        );

        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", instance.getSeq());
        map.put("summary", sumary);

        instanceSummaryMap.put(instance.getId(), map);
    }

    private InstanceGroupDto findInstanceGroup(List<InstanceGroupDto> instanceGroupDtoList, String branch) {
        for (int i = 0; i < instanceGroupDtoList.size(); i++) {
            InstanceGroupDto instanceGroupDto = instanceGroupDtoList.get(i);

            if (branch.equals(instanceGroupDto.getBranch())) {
                return instanceGroupDto;
            }
        }

        InstanceGroupDto instanceGroupDto = new InstanceGroupDto();
        instanceGroupDto.setBranch(branch);
        instanceGroupDtoList.add(instanceGroupDto);

        return instanceGroupDto;
    }

}

