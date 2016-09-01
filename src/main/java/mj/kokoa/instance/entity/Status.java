package mj.kokoa.instance.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Entity
public class Status {
    @Id
    @GeneratedValue
    private long seq;

    @Column(name = "instance_seq")
    private long instanceSeq;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Embedded
    private Session session;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_seq")
    @OrderBy(value = "usedRatio DESC, totalSize DESC, name ASC")
    private List<Tablespace> tablespaceList;

    public Status() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Tablespace> getTablespaceList() {
        return tablespaceList;
    }

    public void setTablespaceList(List<Tablespace> tablespaceList) {
        this.tablespaceList = tablespaceList;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getInstanceSeq() {
        return instanceSeq;
    }

    public void setInstanceSeq(long instanceSeq) {
        this.instanceSeq = instanceSeq;
    }

    @Override
    public String toString() {
        return "Status{" +
                "seq=" + seq +
                ", instanceSeq=" + instanceSeq +
                ", createdDate=" + createdDate +
                ", session=" + session +
                ", tablespaceList=" + tablespaceList +
                '}';
    }
}
