package mj.cocoa.instance;

import java.util.Date;
import java.util.List;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Status {
    private Date createdDate;
    private Session session;
    private List<Tablespace> tablespaceList;

    public Status() {
    }

    public Status(Date createdDate, Session session, List<Tablespace> tablespaceList) {
        this.createdDate = createdDate;
        this.session = session;
        this.tablespaceList = tablespaceList;
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

    @Override
    public String toString() {
        return "Status{" +
                "createdDate=" + createdDate +
                ", session=" + session +
                ", tablespaceList=" + tablespaceList +
                '}';
    }
}
