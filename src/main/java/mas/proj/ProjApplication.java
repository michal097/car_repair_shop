package mas.proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Collections;

@SpringBootApplication
@EntityScan
public class ProjApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ProjApplication.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.port","9999"));
        springApplication.run(args);
    }

}
