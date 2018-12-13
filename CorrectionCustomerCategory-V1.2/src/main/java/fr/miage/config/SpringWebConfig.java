// #### V0.2 Pour visualiser le changement de localisation des "resolvers"
// #### V0.2 sont ajoutés pour intercepter les changements.
package fr.miage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"fr.miage.web"})
public class SpringWebConfig implements WebMvcConfigurer {

    // #### V0.2 Demande à Spring d'intercepter les urls 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    // #### V0.2 Afin de rendre la localisation possible, il faut ajouter un 
    // #### V0.2 bean "LocaleResolver"
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver result = new SessionLocaleResolver();
        result.setLocaleAttributeName("lang");
        result.setDefaultLocale(Locale.FRENCH);
        return result;
    }
    // #### V0.2 Des implémentations de LocaleResolver permettre de connaître 
    // #### V0.2 la localisation par défaut à partir de la session, de cookies...

    // #### V0.2 Dans notre cas, la localisation par défaut est associé à la session.
    
    
    // #### V0.3 Le dossier src/main/resources/static est déclarée ici comme
    // #### V0.3 un conteneur de ressources pour y mettre par exemple du js et Bootstrap
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
