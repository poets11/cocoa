package mj.cocoa.instance;

import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Instance {
    private int seq;
    private String id;
    private String name;
    private String description;
    private String version;
    private Connection connection;
    private List<Status> statusList;

    public Instance() {
    }

    public Instance(int seq, String id, String name, String description, String version, Connection connection, List<Status> statusList) {
        this.seq = seq;
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.connection = connection;
        this.statusList = statusList;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", connection=" + connection +
                ", statusList=" + statusList +
                '}';
    }
}
