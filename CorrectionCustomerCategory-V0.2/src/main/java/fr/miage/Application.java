// #### V0.0 Application "Customers" qui permet de lister, ajouter et modifier
// #### V0.0 des clients dans une base de données.

// #### V0.1 Internationalisation

// #### V0.2 Les effets de l'internalisation faite précédemment ne sont visibles
// #### V0.2 que si l'on change réellement la localisation par exemple en modifiant 
// #### V0.2 les préférences de firefox.
// #### V0.2 Dans cette version, un menu est rajouté sur toutes les pages pour
// #### V0.2 permettre le changement de localisation à l'aide de simples boutons.

// #### V0.2 On profitera pour ajouter des titres, en-tête et pied de page
// #### V0.2 sur toutes les pages.
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
