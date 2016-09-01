package mj.kokoa.instance.service;

import mj.kokoa.common.KokoaException;
import mj.kokoa.instance.entity.*;
import mj.kokoa.instance.repository.InstanceRepository;
import mj.kokoa.instance.repository.StatusRepository;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    private void initDefaultSessionSql() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream stream = loader.getResourceAsStream("session.sql");
        query.setSession(IOUtils.toString(stream, "UTF-8"));
        IOUtils.closeQuietly(stream);
    }

    private void initDefaultTablespaceSql() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream stream = loader.getResourceAsStream("tablespace.sql");
        query.setTablespace(IOUtils.toString(stream, "UTF-8"));
        IOUtils.closeQuietly(stream);
    }

    @PostConstruct
    private void initQuery() throws IOException {
        query = new Query();

        String sessionQueryPath = System.getProperty("sessionQueryPath");
        if (StringUtils.isEmpty(sessionQueryPath) == true) {
            initDefaultSessionSql();
        } else {
            File file = new File(sessionQueryPath);

            if (file.exists() == false) {
                initDefaultSessionSql();
            } else {
                logger.info("Session Query Read From : " + sessionQueryPath);
                query.setSession(FileUtils.readFileToString(file, "UTF-8"));
            }
        }

        String tablespaceQueryPath = System.getProperty("tablespaceQueryPath");
        if (StringUtils.isEmpty(tablespaceQueryPath) == true) {
            initDefaultTablespaceSql();
        } else {
            File file = new File(tablespaceQueryPath);

            if (file.exists() == false) {
                initDefaultTablespaceSql();
            } else {
                logger.info("Tablespace Query Read From : " + tablespaceQueryPath);
                query.setTablespace(FileUtils.readFileToString(file, "UTF-8"));
            }
        }
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

                tablespace.setStatusSeq(status.getSeq());
                tablespace.setName(resultSet.getString("tablespace_name"));
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

    @Override
    public Instance reloadInstanceInfo(String id) {
        java.sql.Connection connection = null;
        try {
            Instance instance = instanceRepository.findInstanceById(id);

            connection = instance.getConnection().createConnection();
            connection.setReadOnly(true);

            Status status = new Status();
            status.setCreatedDate(new Date());
            status.setInstanceSeq(instance.getSeq());
            status.setSession(loadSessionInfo(connection));

            List<Status> statusList = instance.getStatusList();
            statusList.add(status);

            instance = instanceRepository.save(instance);
            statusList = instance.getStatusList();

            status = statusList.get(statusList.size() - 1);
            status.setTablespaceList(loadTablespaceInfo(connection, status));

            statusRepository.save(status);

            return instance;
        } catch (SQLException e) {
            throw new KokoaException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public Instance getInstanceById(String id) {
        return instanceRepository.findInstanceById(id);
    }

    @Override
    public List<Instance> getAllInstanceList() {
        Iterable<Instance> instanceList = instanceRepository.findAll(new Sort(Sort.Direction.ASC, "branch", "host", "id"));
        return (List<Instance>) instanceList;
    }

    @Override
    public Instance save(Instance instance) {
        return instanceRepository.save(instance);
    }

    @Override
    public boolean deleteInstanceById(String id) {
        Instance instance = instanceRepository.findInstanceById(id);
        instanceRepository.delete(instance);
        return true;
    }
}
