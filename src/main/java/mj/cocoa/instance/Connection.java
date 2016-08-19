package mj.cocoa.instance;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Connection {
    private String host;
    private String port;
    private String sid;
    private String userid;
    private String userpassword;

    public Connection(String host, String port, String sid, String userid, String userpassword) {
        this.host = host;
        this.port = port;
        this.sid = sid;
        this.userid = userid;
        this.userpassword = userpassword;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
