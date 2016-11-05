package mj.kokoa.instance.service;

import mj.kokoa.web.instance.dto.ChartDataDto;

import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 10. 28..
 */
public interface TablespaceService {
    List<String> findDistinctTablespaceNameList(long instanceId, Date from, Date to);

    ChartDataDto findTablespaceList(long instanceSeq, String[] tablespaceNameList, Date from, Date to);
}
