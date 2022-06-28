package edu.sombra.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:app_config/application.properties"),
        @PropertySource("classpath:app_config/application-${spring.profiles.active}.properties")
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}