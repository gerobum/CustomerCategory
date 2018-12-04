// #### V0.0 Application "Customers" qui permet de lister, ajouter et modifier
// #### V0.0 des clients dans une base de données.
package fr.miage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// #### V0.0 @SpringBootApplication est un raccourci pour 
// #### V0.0 @EnableAutoConfiguration
// #### V0.0 @ComponentScan
// #### V0.0 @Configuration
// #### V0.0 https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html
@SpringBootApplication(scanBasePackages = "fr.miage")
// #### V0.0 "fr.miage" précise à partir d'où commence le scan des beans.
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
