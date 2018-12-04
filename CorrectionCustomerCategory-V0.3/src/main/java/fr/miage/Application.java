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

// #### V0.3 Utilisation de Bootstrap et js pour avoir une jolie interface.
// #### V0.3 - Les fichiers js et Boostrap sont dans src/main/resources/static
// #### V0.3 - Le dossier src/main/resources/static est déclaré ressources dans SpringWebConfig 
package fr.miage;

import fr.miage.config.SpringWebConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// #### V0.0 @SpringBootApplication est un raccourci pour 
// #### V0.0 @EnableAutoConfiguration
// #### V0.0 @ComponentScan
// #### V0.0 @Configuration
// #### V0.0 https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html
@SpringBootApplication(scanBasePackages = "fr.miage")
// #### V0.0 "fr.miage" précise à partir d'où commence le scan des beans.
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {
    public static final String MAPPING_WEB = "/";
    public static final String CHARACTER_ENCODING = "UTF-8";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean webServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(SpringWebConfig.class);
        dispatcherServlet.setApplicationContext(applicationContext);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, MAPPING_WEB);
        servletRegistrationBean.setName("webServlet");

        return servletRegistrationBean;
    }

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(SpringCoreConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        //
        FilterRegistration characterEncodingFilter = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", CHARACTER_ENCODING);
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, MAPPING_WEB);
    }
}
