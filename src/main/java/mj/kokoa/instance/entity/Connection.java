package mj.kokoa.instance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Connection implements Serializable{
    private static final String JDBC_URL = "jdbc:oracle:thin:@%s:%s:%s";

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String port;

    @Column(name = "SERVICE_NM", nullable = false)
    private String sid;

    @Column(name = "USER_NM", nullable = false)
    private String userName;

    @Column(name = "PW", nullable = false)
    private String userPassword;

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
