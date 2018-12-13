package fr.miage.core.repository;

import fr.miage.core.entity.Category;
import fr.miage.core.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
// #### V0.0 Couche repository
        
// #### V0.0 (HTTP/POST) Pour rendre persistant l'entité Category en utilisant
// #### V0.0 Hibernate à travers JPA, il suffit, pour l'essentiel, de déclarer    
// #### V0.0 une interface qui hérite de JpaRepository<Category, Long>   
// #### V0.0 dont le 1er paramètre générique est le type à "persister" (Customer)  
// #### V0.0 et le 2eme est le type de son identifiant.

// #### V0.0 L'interface JpaRepository possède déjà un certain nombres de 
// #### V0.0 méthodes pour faire des requêtes dans la table, comme par exemple
// #### V0.0   - findAll() 
// #### V0.0   - deleteById(ID id)
// #### V0.0   - save(S entity)
// #### V0.0   - ...
// #### V0.0 https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

public interface CustomerRepository extends JpaRepository<Customer, Long> {
// #### V0.0 Mais en plus il est possible de composer sa propre méthode
// #### V0.0 Par exemple, findByName parce que le champ name existe dans Customer.
    Customer findByName(String name);
// #### V0.0 Il est possible d'imaginer des méthodes plus compliquées comme
// #### V0.0 par exemple findByNameAndSurname(String name, String surname);
// #### V0.0 pour peu que surname soit un champ.    
// #### V0.0 https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
// #### V0.0 Et le plus important et qu'il n'est même pas nécessaire d'écrire 
// #### V0.0 l'implémentation de cette interface. Spring s'en charge ! 
    
 // #### V1.3 Notamment, voici comment rédiger l'en-tête d'une méthode pour  
 // #### V1.3 que son implémentation auto-générée retourne la liste de toutes 
 // #### V1.3 les catégories triée dans l'ordre alphabétique de leur nom.   
    List<Customer> findAllByOrderByName();
// #### V2.0 On veut tous les clients d'une catégorie par ordre de leur nom.
// #### V2.0 Il suffit d'écrire cette en-tête et Spring fait le reste.    
    public List<Customer> findByCategoryOrderByName(Category category);
}
