package fr.miage;

import fr.miage.core.entity.Category;
import fr.miage.core.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// #### V1.2 DemoCategories est une implémentation de CommandLineRunner qui
// #### V1.2 indique que c'est un Bean qui doit être exécuté à son lancement.
// #### V1.2 Plusieurs CommandLineRunner peuvent coexister. Si un ordre 
// #### V1.2 d'exécution est nécessaire, il peut être indiqué par @Order
@Order(1)
@Component 
// #### V2.2 Le chargement au démarrage de quelques exemples de catégories dans
// #### V2.2 la BDD n'existe (heureusement) qu'en mode développement ("dev").
// #### V2.2 Ce bean ne sera instancié qu'en mode (profil) dev.
// #### V2.2 Remarque: on aurait pu écrire @Profile("!prod") pour dire que 
// #### V2.2 Ce bean sera instancié si on n'est pas en mode prod.
@Profile("dev")
public class DemoCategories implements CommandLineRunner {
    // #### V1.1 Utilisation de slf4j pour faire du log
    // #### V1.1 https://www.slf4j.org/manual.html
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoCategories.class);

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... arg0) throws Exception {

        createCategory("A");
        createCategory("B");
        createCategory("Z");
    }

    private void createCategory(String name) {
        Category c = categoryService.findByName(name);
        if (c == null) {
            c = new Category(name);
            categoryService.save(c);
            // #### V1.1 Log pour indiquer que la catégorie a été créée
            LOGGER.info("BDD DEMO - Création de la catégorie {}", name);
        } else {
            LOGGER.info("BDD DEMO - La catégorie {} existait déjà", name);
            // #### V1.1 Remarque : {} est remplacé par name dans la chaîne.
            // #### V1.1 C'est une façon de formater.
        }
    }
}
