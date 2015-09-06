package net.jacqg.tvshow.library.organizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class);
        OrganizeProcess process = applicationContext.getBean(OrganizeProcess.class);
        process.organize();
        applicationContext.close();
    }


}
