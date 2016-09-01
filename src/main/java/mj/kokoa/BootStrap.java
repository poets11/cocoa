package mj.kokoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@SpringBootApplication
public class BootStrap {
    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        DriverManager.setLoginTimeout(60);

        SpringApplication application = new SpringApplication(BootStrap.class);
        application.run(args);
    }
}
