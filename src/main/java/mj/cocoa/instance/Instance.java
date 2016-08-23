package mj.cocoa.instance;

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

    private String description;
    private String version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Embedded
    private Connection connection;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "instance_seq")
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

    @Override
    public String toString() {
        return "Instance{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", updatedDate=" + updatedDate +
                ", connection=" + connection +
                ", statusList=" + statusList +
                '}';
    }
}
