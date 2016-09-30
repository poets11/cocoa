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
import java.sql.Connection;
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
                fillSegmentInfo(connection, tablespaceList);
                return tablespaceList;
            } else {
                throw new KokoaException("조회된 데이터가 없습니다.");
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
        }
    }

    private void fillSegmentInfo(Connection connection, List<Tablespace> tablespaceList) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.getSegment());

            while (resultSet.next()) {
                Tablespace tablespace = findTablespaceByName(resultSet.getString("TABLESPACE_NAME"), tablespaceList);

                List<Segment> segmentList = tablespace.getSegmentList();
                if (segmentList == null) {
                    segmentList = new ArrayList<Segment>();
                    tablespace.setSegmentList(segmentList);
                }

                Segment segment = new Segment();
                segment.setSegmentId(new SegmentId(tablespace.getTablespaceId(), resultSet.getString("SEGMENT_NAME")));

                segment.setOwner(resultSet.getString("OWNER"));
                segment.setPartitionName(resultSet.getString("PARTITION_NAME"));
                segment.setSegmentType(resultSet.getString("SEGMENT_TYPE"));
                segment.setSegmentSubtype(resultSet.getString("SEGMENT_SUBTYPE"));
                segment.setHeaderFile(resultSet.getLong("HEADER_FILE"));
                segment.setHeaderBlock(resultSet.getLong("HEADER_BLOCK"));
                segment.setBytes(resultSet.getLong("BYTES"));
                segment.setBlocks(resultSet.getLong("BLOCKS"));
                segment.setExtents(resultSet.getLong("EXTENTS"));
                segment.setInitialExtent(resultSet.getLong("INITIAL_EXTENT"));
                segment.setNextExtent(resultSet.getLong("NEXT_EXTENT"));
                segment.setMinExtents(resultSet.getLong("MIN_EXTENTS"));
                segment.setMaxExtents(resultSet.getLong("MAX_EXTENTS"));
                segment.setMaxSize(resultSet.getLong("MAX_SIZE"));
                segment.setRetention(resultSet.getString("RETENTION"));
                segment.setMinretention(resultSet.getLong("MINRETENTION"));
                segment.setPctIncrease(resultSet.getLong("PCT_INCREASE"));
                segment.setFreelists(resultSet.getLong("FREELISTS"));
                segment.setFreelistGroups(resultSet.getLong("FREELIST_GROUPS"));
                segment.setRelativeFno(resultSet.getLong("RELATIVE_FNO"));
                segment.setBufferPool(resultSet.getString("BUFFER_POOL"));
                segment.setFlashCache(resultSet.getString("FLASH_CACHE"));
                segment.setCellFlashCache(resultSet.getString("CELL_FLASH_CACHE"));

                segmentList.add(segment);
            }
        } finally {
            DbUtils.close(resultSet);
            DbUtils.close(statement);
        }
    }

    private Tablespace findTablespaceByName(String tablespaceName, List<Tablespace> tablespaceList) {
        for (int i = 0; i < tablespaceList.size(); i++) {
            Tablespace tablespace = tablespaceList.get(i);

            if (tablespace.getTablespaceId().getName().equals(tablespaceName) == true) {
                return tablespace;
            }
        }

        throw new KokoaException("세그먼트와 연결된 테이블 스페이스를 못찾았습니다. 테이블 스페이명 : " + tablespaceName);
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
