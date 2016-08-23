package mj.cocoa.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@Embeddable
public class Connection implements Serializable{
    private static final long serialVersionUID = 1946879710477755570L;

    private static final String JDBC_URL = "jdbc:oracle:thin:@%s:%s:%s";

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private String port;

    @Column(nullable = false)
    private String sid;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    public Connection() {
    }

    public Connection(String host, String port, String sid, String userName, String userPassword) {
        this.host = host;
        this.port = port;
        this.sid = sid;
        this.userName = userName;
        this.userPassword = userPassword;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", sid='" + sid + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }

    @JsonIgnore
    public boolean isValid() throws SQLException {
        java.sql.Connection connection = createConnection();
        Statement statement = connection.createStatement();
        boolean valid = statement.execute("select sysdate from dual");
        connection.close();

        return valid;
    }

    @JsonIgnore
    public java.sql.Connection createConnection() throws SQLException {
        return DriverManager.getConnection(getJdbcUrl(), getUserName(), getUserPassword());
    }

    private String getJdbcUrl() {
        return String.format(JDBC_URL, getHost(), getPort(), getSid());
    }
}
