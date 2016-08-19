package mj.cocoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by poets11 on 2016. 8. 18..
 */
@SpringBootApplication
public class BootStrap {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootStrap.class);
        application.run(args);
    }
}
