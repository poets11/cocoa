package mj.kokoa.web.instance;

import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.repository.InstanceRepository;
import mj.kokoa.web.instance.dto.status.InstanceGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Controller
public class StatusController {
    @Autowired
    private InstanceRepository instanceRepository;

    @RequestMapping("/status/main.mj")
    public String statusMain(Model model) {
        List<InstanceGroupDto> instanceGroupDtoList = new ArrayList<InstanceGroupDto>();

        List<Instance> instanceList = instanceRepository.findAll(new Sort(Sort.Direction.ASC, "branch", "id"));
        for (int i = 0; i < instanceList.size(); i++) {
            Instance instance = instanceList.get(i);
            InstanceGroupDto group = findInstanceGroup(instanceGroupDtoList, instance.getBranch());
            group.getInstanceList().add(instance);
        }

        model.addAttribute("instanceGroupList", instanceGroupDtoList);

        return "status/main";
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

