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
// #### V0.3 - Les templates .html sont agrémentés de décorations bootstap

// #### V1.0 Rajout d'une entité Category à gérer comme c'est fait pour les clients.
// #### V1.0 C'est-à-dire la possibilité de créer, de modifier et de supprimer
// #### V1.0 des catégories. La seule différente est que le nom d'une catégorie
// #### V1.0 n'a qu'une et une seule lettre.
// #### V1.0 Ajout d'un menu visible sur toutes les pages qui permet de choisir
// #### V1.0 entre la page qui affiche les clients et celle qui affiche les 
// #### V1.0 catégories, à l'instar de celui qui permet de basculer entre 
// #### V1.0 "anglais" et "français". 

// #### V1.1 Remplissage de quelques clients et quelques catégories de démo
// #### V1.1 dans la BDD au démarrage de l'application. Et utilisation d'un  
// #### V1.1 "logger" pour afficher des messages.

// #### V1.2 Ajout de quelques tests unitaires.
// #### V1.2 Les modifications se voient "pom.xml" et surtout dans "Test Packages".
// #### V1.2 https://docs.spring.io/spring-batch/3.0.x/reference/html/testing.html

// #### V1.3 La liste des clients comme des catégories doit s'afficher dans l'ordre alphabétique.
// #### V1.3 C'est seulement le findAll de la couche service qui sera modifié.

// #### V1.4 Un client appartient à 0 ou 1 catégorie, une catégorie est associée à 0, 1
// #### V1.4 ou plusieurs clients. 
// #### V1.4 Le formulaire de création et de modification de client offre maintenant
// #### V1.4 la possibilité d'affecter une catégorie à une client ou de la changer.

// #### V1.5 Chaque client s'affiche avec sa catégorie, si elle existe.
// #### V1.5 La seule chose à faire est de redéfinir la méthode toString()
// #### V1.5 de Customer.
    
// #### V2.0 La liste de tous les clients d'une catégorie donnée s'affiche
// #### V2.0 Par exemple /customer/X affiche les clients de catégorie X.
// #### V2.0 Si X n'existe pas, la liste est vide.

// #### V2.1 Mise en place d'une BDD MySql plutôt que H2.

// #### V2.2 Création de deux profils : 
// #### V2.2    1. Un profil "dev", dédié au développement, pourvu d'une BDD H2 
// #### V2.2       (en mémoire) et qui réalise des tests unitaires
// #### V2.2    2. Un profil "prod", dédié à la production, pourvu d'une BDD  
// #### V2.2       mysql et qui ne s'encombre pas des dépendances liées à JUnit 
// #### V2.2
// #### V2.2 Remarque : le changement ne se fait pas à chaud. Il faut faire un
// #### V2.2 build pour le valider.

// #### V2.3 Gestion de certaines erreurs provoquées certaines tentatives de
// #### V2.3 modifications dans la BDD comme la création d'un client dont le nom 
// #### V2.3 existe déjà. 

// #### V3.0 Cette version ne différence de la V2.3 que par son port, 7373 au lieu de 7272
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
