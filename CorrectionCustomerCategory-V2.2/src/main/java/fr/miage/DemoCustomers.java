package fr.miage;

import fr.miage.core.entity.Customer;
import fr.miage.core.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


// #### V1.1 Demo est une implémentation de CommandLineRunner qui indique que
// #### V1.1 c'est un Bean qui doit être exécuté à son lancement.
// #### V1.1 Plusieurs CommandLineRunner peuvent coexister. Si un ordre 
// #### V1.1 d'exécution est nécessaire, il peut être indiqué par @Order
@Component
@Order(2)
// #### V2.1 Le chargement au démarrage de quelques exemples de clients dans
// #### V2.1 la BDD n'existe (heureusement) qu'en mode développement ("dev").
// #### V2.1 Ce bean ne sera instancié qu'en mode (profil) dev.
// #### V2.1 Remarque: on aurait pu écrire @Profile("!prod") pour dire que 
// #### V2.1 Ce bean sera instancié si on n'est pas en mode prod.
@Profile("dev")
public class DemoCustomers implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoCustomers.class);

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public void run(String... arg0) throws Exception {
        createCustomer("Client 1");
        createCustomer("Client 2");

    }

    private void createCustomer(String name) {
        Customer c = customerService.findByName(name);
        if (c == null) {
            c = new Customer(name);
            customerService.save(c);
            LOGGER.info("BDD DEMO - Création du client {}", name);
        } else {
            LOGGER.info("BDD DEMO - Le client {} existait déjà", name);
        }
    }
}