package mj.cocoa.instance;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Entity
public class Instance implements Serializable {
    private static final long serialVersionUID = 247777622189902977L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    private String host;
    private String description;
    private String version;
    private String branch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Embedded
    private Connection connection;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_seq")
    @OrderBy(value = "createdDate DESC")
    private List<Status> statusList;

    public Instance() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", branch='" + branch + '\'' +
                ", updatedDate=" + updatedDate +
                ", connection=" + connection +
                ", statusList=" + statusList +
                '}';
    }
}
