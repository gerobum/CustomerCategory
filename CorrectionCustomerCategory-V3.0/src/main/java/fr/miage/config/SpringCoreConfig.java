// #### V2.1 Fichier ajouté pour la configuration conditionnelle de la BDD selon
// #### V2.1 le profil dev ou prod.
package fr.miage.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan("fr.miage.core")
@EnableJpaRepositories(basePackages = {"fr.miage.core.repository"})
// #### V2.1 Les fichiers contenant les propriétés de configuration de persistence s'appellent
// #### V2.1 s'appellent persistence-dev.properties et persistence-prod.properties
// #### V2.1 La valeur PropertySource dépent de la variable ${spring.profiles.active}
// #### V2.1 qui vaut dev ou prod.
@PropertySource(value = {"classpath:persistence-${spring.profiles.active}.properties"})
@EnableTransactionManagement
public class SpringCoreConfig {

    @Autowired
    // #### V2.1 L'implémentation de l'interface Environment est injectée.
    // #### V2.1 Elle donne des informations importantes, notamment quel est
    // #### V2.1 le profil courant (dev ou prod) et d'autres propriétés.
    // #### V2.1 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/Environment.html
    private Environment environment;

    @Bean
    // #### V2.1 LocalContainerEntityManagerFactoryBean est un FactoryBean qui
    // #### V2.1 crée un JPA EntityManagerFactory pour manipuler la BDD.
    // #### V2.1 En général, les configurations sont lues à partir d'un fichier
    // #### V2.1 META-INF/persistence.xml
    // #### V2.1 Cependant, ce factory permet d'outrepasser le fichier par programmation.
    // #### V2.1 Comme c'est fait ici.
    // #### V2.1 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setPackagesToScan("fr.miage.core");
    // #### V2.1 La source de BDD dépend du profil.
        if (environment.getActiveProfiles().length > 0) {
            if ("prod".equals(environment.getActiveProfiles()[0])) {
                emfb.setDataSource(dataSourceProd());
            } else {
                emfb.setDataSource(dataSourceDev());
            }
        }

        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        emfb.setJpaPropertyMap(jpaPropertiesMap());
        return emfb;
    }
    // #### V2.1 La source de données en mode dev
    @Bean
    @Profile("dev")
    public DataSource dataSourceDev() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    // #### V2.1 La source de données en mode prod
    @Bean
    @Profile("prod")
    public DataSource dataSourceProd() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // #### V2.1 Les propriétés sont lues dans persistence-prod.properties
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    public Map<String, Object> jpaPropertiesMap() {
        Map<String, Object> properties = new HashMap<>();
        // #### V2.1 Les propriétés sont lues dans persistence-prod.properties ou persistence-dev.properties
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        if (environment.getActiveProfiles().length > 0) {
            if ("prod".equals(environment.getActiveProfiles()[0])) {
                transactionManager.setDataSource(dataSourceProd());
            } else {
                transactionManager.setDataSource(dataSourceDev());
            }
        }

        return transactionManager;
    }

}
