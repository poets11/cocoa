package mj.kokoa.instance.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import mj.kokoa.instance.entity.QInstance;
import mj.kokoa.instance.entity.QStatus;
import mj.kokoa.instance.entity.QTablespace;
import mj.kokoa.instance.repository.TablespaceRepository;
import mj.kokoa.web.instance.dto.ChartDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by poets11 on 2016. 10. 28..
 */
@Service
public class TablespaceServiceImpl implements TablespaceService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TablespaceRepository tablespaceRepository;


    @Override
    public List<String> findDistinctTablespaceNameList(long instanceSeq, Date from, Date to) {
        QInstance instance = QInstance.instance;
        QStatus status = QStatus.status;
        QTablespace tablespace = QTablespace.tablespace;

        JPAQuery<String> query = new JPAQuery<>(entityManager);

        query = query.select(tablespace.tablespaceId.name).distinct();
        query = query.from(instance, status, tablespace);
        query = query.where(instance.seq.eq(instanceSeq), status.statusId.createdDate.between(from, to));
        query.orderBy(tablespace.tablespaceId.name.asc());

        List<String> tablespaceNameList = query.fetch();

        return tablespaceNameList;
    }

    @Override
    public ChartDataDto findTablespaceList(long instanceSeq, String[] tablespaceNameList, Date from, Date to) {
        QInstance instance = QInstance.instance;
        QStatus status = QStatus.status;
        QTablespace tablespace = QTablespace.tablespace;

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        query = query.select(tablespace.tablespaceId.name, tablespace.usedSize, status.statusId.createdDate).distinct();
        query = query.from(instance, status, tablespace);
        query = query.where(status.statusId.instance.seq.eq(instanceSeq), status.statusId.createdDate.between(from, to), tablespace.tablespaceId.name.in(tablespaceNameList));
        query.orderBy(status.statusId.createdDate.asc());

        List<Tuple> tuples = query.fetch();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        List<String> labelList = new ArrayList<String>();
        Map<String, ChartDataDto.DataDto> valueMap = new HashMap<>();

        for (int i = 0; i < tuples.size(); i++) {
            Tuple tuple = tuples.get(i);

            Date createdDate = tuple.get(status.statusId.createdDate);
            String tablespaceName = tuple.get(tablespace.tablespaceId.name);
            Double usedSize = tuple.get(tablespace.usedSize);

            System.out.println(tablespaceName + " / " + usedSize + " / " + createdDate);

            String date = format.format(createdDate);
            if (labelList.contains(date) == false) {
                labelList.add(date);
            }

            if (valueMap.containsKey(tablespaceName) == false) {
                ChartDataDto.DataDto dataDto = new ChartDataDto.DataDto();
                dataDto.setLabel(tablespaceName);

                valueMap.put(tablespaceName, dataDto);
            }

            ChartDataDto.DataDto dataDto = valueMap.get(tablespaceName);
            dataDto.appendValue(usedSize);
        }

        ChartDataDto chartDataDto = new ChartDataDto();
        chartDataDto.setLabelList(labelList);

        List<ChartDataDto.DataDto> dataDtos = new ArrayList<>();
        dataDtos.addAll(valueMap.values());
        chartDataDto.setDataList(dataDtos);

        return chartDataDto;
    }
}
