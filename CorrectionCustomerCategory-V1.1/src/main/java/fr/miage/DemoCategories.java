package fr.miage;

import fr.miage.core.entity.Category;
import fr.miage.core.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// #### V1.1 Demo est une implémentation de CommandLineRunner qui indique que
// #### V1.1 c'est un Bean qui doit être exécuté à son lancement.
// #### V1.1 Plusieurs CommandLineRunner peuvent coexister. Si un ordre 
// #### V1.1 d'exécution est nécessaire, il peut être indiqué par @Order
@Order(1)
@Component
public class DemoCategories implements CommandLineRunner {

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
