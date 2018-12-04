package fr.miage.core.repository;

import fr.miage.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
// #### V1.0 Couche repository
        
// #### V1.0 (HTTP/POST) Pour rendre persistant l'entité Category en utilisant
// #### V1.0 Hibernate à travers JPA, il suffit, pour l'essentiel, de déclarer    
// #### V1.0 une interface qui hérite de JpaRepository<Category, Long>   
// #### V1.0 dont le 1er paramètre générique est le type à "persister" (Customer)  
// #### V1.0 et le 2eme est le type de son identifiant.

// #### V1.0 L'interface JpaRepository possède déjà un certain nombres de 
// #### V1.0 méthodes pour faire des requêtes dans la table, comme par exemple
// #### V1.0   - findAll() 
// #### V1.0   - deleteById(ID id)
// #### V1.0   - save(S entity)
// #### V1.0   - ...
// #### V1.0 https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

public interface CategoryRepository extends JpaRepository<Category, Long> {
// #### V1.0 Mais en plus il est possible de composer sa propre méthode
// #### V1.0 Par exemple, findByName parce que le champ name existe dans Customer.
    Category findByName(String name);
// #### V1.0 Il est possible d'imaginer des méthodes plus compliquées comme
// #### V1.0 par exemple findByNameAndSurname(String name, String surname);
// #### V1.0 pour peu que surname soit un champ.    
// #### V1.0 https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
// #### V1.0 Et le plus important et qu'il n'est même pas nécessaire d'écrire 
// #### V1.0 l'implémentation de cette interface. Spring s'en charge ! 
}
