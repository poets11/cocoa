package mj.kokoa.instance.entity;

/**
 * Created by poets11 on 2016. 8. 22..
 */
public class Query {
    private String session;
    private String tablespace;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTablespace() {
        return tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    @Override
    public String toString() {
        return "Query{" +
                "session='" + session + '\'' +
                ", tablespace='" + tablespace + '\'' +
                '}';
    }
}
