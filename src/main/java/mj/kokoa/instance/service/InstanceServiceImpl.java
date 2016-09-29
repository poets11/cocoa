package mj.kokoa.instance.service;

import mj.kokoa.common.KokoaException;
import mj.kokoa.instance.entity.*;
import mj.kokoa.instance.repository.InstanceRepository;
import mj.kokoa.instance.repository.StatusRepository;
import mj.kokoa.instance.web.vo.InstanceCondition;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Query query;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private StatusRepository statusRepository;

    private String readFileFromPropertyOrClassPath(String propKey, String pathInClassPath) throws IOException {
        String filePath = System.getProperty(propKey);
        if (StringUtils.hasText(filePath) == true) {
            File file = new File(filePath);

            if (file.exists() == true) {
                logger.info("read file from property. key : " + propKey + " / value : " + filePath);
                return FileUtils.readFileToString(file, "UTF-8");
            }
        }

        logger.info("read file from classpath. file name : " + pathInClassPath);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream stream = loader.getResourceAsStream(pathInClassPath);
        String content = IOUtils.toString(stream, "UTF-8");

        IOUtils.closeQuietly(stream);
        return content;
    }

    @PostConstruct
    private void initQuery() throws IOException {
        query = new Query();

        query.setSession(readFileFromPropertyOrClassPath("sessionQueryPath", "session.sql"));
        query.setTablespace(readFileFromPropertyOrClassPath("tablespaceQueryPath", "tablespace.sql"));
        query.setSegment(readFileFromPropertyOrClassPath("segmentQueryPath", "segment.sql"));

        logger.info("load query complete. " + query);
    }

    private Session loadSessionInfo(java.sql.Connection connection) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.getSession());

            if (resultSet.next()) {
                Session session = new Session();
                session.setActiveCount(resultSet.getInt("active"));
                session.setLockedCount(resultSet.getInt("locked"));
                session.setTotalCount(resultSet.getInt("total"));

                return session;
            } else {
                throw new KokoaException("조회된 데이터가 없습니다.");
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
        }
    }

    private List<Tablespace> loadTablespaceInfo(java.sql.Connection connection, Status status) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            List<Tablespace> tablespaceList = new ArrayList<Tablespace>();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.getTablespace());

            while (resultSet.next()) {
                Tablespace tablespace = new Tablespace();

                tablespace.setTablespaceId(new TablespaceId(status.getStatusId(), resultSet.getString("tablespace_name")));
                tablespace.setFileCount(resultSet.getInt("file_cnt"));
                tablespace.setTotalSize(resultSet.getDouble("size_mb"));
                tablespace.setFreeSize(resultSet.getDouble("free_mb"));
                tablespace.setUsedSize(resultSet.getDouble("use_mb"));
                tablespace.setUsedRatio(resultSet.getDouble("use_rt"));

                tablespaceList.add(tablespace);
            }

            if (tablespaceList.size() > 0) {
                return tablespaceList;
            } else {
                throw new KokoaException("조회된 데이터가 없습니다.");
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
        }
    }

    private void setVariation(Instance instance) {
        List<Status> statusList = instance.getStatusList();

        List<Tablespace> preTablespaceList = null;
        for (int i = 0; i < statusList.size(); i++) {
            Status status = statusList.get(i);
            List<Tablespace> tablespaceList = status.getTablespaceList();

            if (preTablespaceList == null) {
                preTablespaceList = tablespaceList;
                continue;
            }

            for (int j = 0; j < tablespaceList.size(); j++) {
                Tablespace tablespace = tablespaceList.get(j);

                for (int k = 0; k < preTablespaceList.size(); k++) {
                    Tablespace preTablespace = preTablespaceList.get(k);

                    if (tablespace.getTablespaceId().getName().equals(preTablespace.getTablespaceId().getName()) == true) {
                        double variationAmount = tablespace.getUsedSize() - preTablespace.getUsedSize();

                        tablespace.setVariationAmount(variationAmount);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Instance reloadInstanceInfo(String id) {
        java.sql.Connection connection = null;
        try {
            Instance instance = instanceRepository.findById(id);

            connection = instance.getConnection().createConnection();
            connection.setReadOnly(true);

            Status status = new Status();
            status.setStatusId(new StatusId(instance.getSeq(), new Date()));
            status.setSession(loadSessionInfo(connection));

            List<Status> statusList = instance.getStatusList();
            statusList.add(status);

            instance = instanceRepository.save(instance);
            statusList = instance.getStatusList();

            status = statusList.get(statusList.size() - 1);
            status.setTablespaceList(loadTablespaceInfo(connection, status));

            statusRepository.save(status);

            setVariation(instance);

            return instance;
        } catch (SQLException e) {
            throw new KokoaException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public Instance getInstanceById(String id) {
        return instanceRepository.findById(id);
    }

    @Override
    public List<Instance> getAllInstanceList(InstanceCondition instanceCondition) {
        List<Instance> instanceList = (List<Instance>) instanceRepository.findAll(instanceCondition.convertPredicate());

        for (int i = 0; i < instanceList.size(); i++) {
            Instance instance = instanceList.get(i);
            setVariation(instance);
        }

        return instanceList;
    }

    @Override
    public Instance save(Instance instance) {
        return instanceRepository.save(instance);
    }

    @Override
    public boolean deleteInstanceById(String id) {
        Instance instance = instanceRepository.findById(id);
        instanceRepository.delete(instance);
        return true;
    }
}
