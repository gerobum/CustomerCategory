package fr.miage.core.service;

import fr.miage.core.entity.Category;
import java.util.List;
import java.util.Optional;


// #### V0.1 On y définit les méthodes dont on a besoin.
// #### V0.1 Cette interface nécessite une implémentation, mais elle est très
// #### V0.1 simple à faire. Voir le paquetage impl
public interface CategoryService {
    Category save(Category entity);
    void delete(Long id);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category findByName(String name);
    Category getOne(Long id);
}
