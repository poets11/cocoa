package mj.kokoa.web.instance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mj.kokoa.instance.entity.Instance;
import mj.kokoa.instance.repository.InstanceRepository;
import mj.kokoa.instance.service.TablespaceService;
import mj.kokoa.web.instance.dto.ChartDataDto;
import mj.kokoa.web.instance.dto.InstanceGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by poets11 on 2016. 9. 29..
 */
@Controller
public class StatusController {
    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private TablespaceService tablespaceService;

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

    @RequestMapping("/status/{instanceSeq}/tablespace-chart-snippet.mj")
    @ResponseBody
    public Object getTablespaceChart(HttpServletRequest req, @PathVariable long instanceSeq, String period, String tablespaceName) {
        Calendar from = Calendar.getInstance(Locale.KOREA);
        Calendar to = Calendar.getInstance(Locale.KOREA);

        String[] split = period.split("[-]");

        String[] fromSplit = split[0].trim().split("[/]");
        from.set(Calendar.YEAR, Integer.parseInt(fromSplit[0]));
        from.set(Calendar.MONTH, Integer.parseInt(fromSplit[1]) - 1);
        from.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fromSplit[2]));
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);

        String[] toSplit = split[1].trim().split("[/]");
        to.set(Calendar.YEAR, Integer.parseInt(toSplit[0]));
        to.set(Calendar.MONTH, Integer.parseInt(toSplit[1]) - 1);
        to.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toSplit[2]));
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);

        ChartDataDto chartDataDto = tablespaceService.findTablespaceList(instanceSeq, tablespaceName, from.getTime(), to.getTime());

        return chartDataDto;

    }

    @RequestMapping("/status/{instanceSeq}/tablespace-list-snippet.mj")
    public String getTablespaceList(ModelMap modelMap, @PathVariable long instanceSeq, String period) {
        Calendar from = Calendar.getInstance(Locale.KOREA);
        Calendar to = Calendar.getInstance(Locale.KOREA);

        String[] split = period.split("[-]");

        String[] fromSplit = split[0].trim().split("[/]");
        from.set(Calendar.YEAR, Integer.parseInt(fromSplit[0]));
        from.set(Calendar.MONTH, Integer.parseInt(fromSplit[1]) - 1);
        from.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fromSplit[2]));
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);

        String[] toSplit = split[1].trim().split("[/]");
        to.set(Calendar.YEAR, Integer.parseInt(toSplit[0]));
        to.set(Calendar.MONTH, Integer.parseInt(toSplit[1]) - 1);
        to.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toSplit[2]));
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);

        List<String> tablespaceNameList = tablespaceService.findDistinctTablespaceNameList(instanceSeq, from.getTime(), to.getTime());
        modelMap.put("tablespaceNameList", tablespaceNameList);

        return "status/tablespace-list-snippet";
    }

    private void putInstanceSummary(Map<String, Map<String, Object>> instanceSummaryMap, Instance instance) {
        final String format = "<b>[%s] %s(%s)</b><br>" +
                "%s<br>" +
                "<b>Host : </b>%s<br>" +
                "<b>Address : </b>%s:%s:%s<br>" +
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

