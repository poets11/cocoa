package mj.cocoa.instance;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    @Override
    public boolean reloadInstanceInfo(String id) {
        return true;
    }

    @Override
    public Instance getInstanceById(String id) {
        Instance instance = createDummyInstance(id);

        return instance;
    }

    @Override
    public List<Instance> getAllInstanceList() {
        String[] ids = {"A", "B", "C", "D", "E", "F", "G"};

        List<Instance> instances = new ArrayList<Instance>();
        for (int i = 0; i < ids.length; i++) {
            instances.add(createDummyInstance(ids[i]));
        }

        return instances;
    }

    private Instance createDummyInstance(String id) {
        Instance instance = new Instance();
        instance.setId(id);
        instance.setName(id);
        instance.setSeq(0);

        Status status = new Status();
        status.setCreatedDate(new Date());
        status.setSession(new Session(3, 40, 1));
        status.setTablespaceList(createDummyTablespaces());

        ArrayList<Status> statusList = new ArrayList<>();
        statusList.add(status);

        instance.setStatusList(statusList);

        return instance;
    }

    private List<Tablespace> createDummyTablespaces() {
        List<Tablespace> tablespaces = new ArrayList<>();

        tablespaces.add(new Tablespace("SYSAUX", 15, 11700, 49, 11651, 99.55));
        tablespaces.add(new Tablespace("UNDOTBS1", 14, 490, 22.56, 467.441, 95.40));
        tablespaces.add(new Tablespace("USERS", 70, 7000, 93.31, 6905.69, 98.65));
        tablespaces.add(new Tablespace("SYSTEM", 1, 360, 4.5, 355.5, 98.75));
        tablespaces.add(new Tablespace("PMS_DATA", 1, 50, 46.12, 3.88, 7.75));
        tablespaces.add(new Tablespace("TEMP", 1, 20, 15, 5, 25));
        tablespaces.add(new Tablespace("PMS_DATA_TEMP", 1, 199, 99, 1, 1));

        return tablespaces;
    }
}
