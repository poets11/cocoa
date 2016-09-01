package mj.kokoa.instance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.dbutils.DbUtils;

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
    private String ip;

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

    public Connection(String ip, String port, String sid, String userName, String userPassword) {
        this.ip = ip;
        this.port = port;
        this.sid = sid;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", sid='" + sid + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }

    @JsonIgnore
    public boolean isValid() throws SQLException {
        java.sql.Connection connection = null;
        Statement statement = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();

            return statement.execute("select sysdate from dual");
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }
    }

    @JsonIgnore
    public java.sql.Connection createConnection() throws SQLException {
        return DriverManager.getConnection(getJdbcUrl(), getUserName(), getUserPassword());
    }

    private String getJdbcUrl() {
        return String.format(JDBC_URL, getIp(), getPort(), getSid());
    }
}
