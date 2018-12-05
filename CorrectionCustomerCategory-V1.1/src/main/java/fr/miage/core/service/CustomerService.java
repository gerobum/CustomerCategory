package fr.miage.core.service;

import java.util.List;
import java.util.Optional;

import fr.miage.core.entity.Customer;
// #### V0.0 Couche service
// #### V0.0 C'est une couche intermédiaire

// #### V0.0 On y définit les méthodes dont on a besoin.
// #### V0.0 Cette interface nécessite une implémentation, mais elle est très
// #### V0.0 simple à faire. Voir le paquetage impl
public interface CustomerService {
    Customer save(Customer entity);
    void delete(Long id);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Customer findByName(String name);
    Customer getOne(Long id);
}
