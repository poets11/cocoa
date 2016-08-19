package mj.cocoa.instance;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Connection {
    private String host;
    private String port;
    private String sid;
    private String userName;
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

    public java.sql.Connection getConnection() throws SQLException {
        String url = getJDBCUrl();

        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, getUserName(), getUserPassword());
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");

        return dataSource.getConnection();
    }

    private static final String JDBC_URL = "jdbc:oracle:thin:@%s:%s:%s";
    private String getJDBCUrl() {
        return String.format(JDBC_URL, getHost(), getPort(), getSid());
    }
}
