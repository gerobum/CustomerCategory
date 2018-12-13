package fr.miage.core.service.impl;

import fr.miage.core.entity.Category;
import fr.miage.core.entity.Customer;
import fr.miage.core.repository.CustomerRepository;
import fr.miage.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// #### V0.0 @Service est une annotation qui spécialise @Component.
// #### V0.0 Elle indique une classe métier.
// #### V0.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html
@Service
public class CustomerServiceImpl implements CustomerService {

// #### V0.0 @Autowired permet d'injecter automatiquement la bonne dépendance.
// #### V0.0 Elle est fabriquée automatiquement par Spring (voir CustomerRepository)
    @Autowired
    CustomerRepository customerRepository;

// #### V0.0 Le reste s'écrit facilement.
    @Override
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

// #### V1.3 L'avantage de modifier à l'implémentation de findAll dans la couche
// #### V1.3 service est que toute invocation à cette méthode aura pour effet
// #### V1.3 de toujours retourner la liste dans l'ordre alphabétique. 
// #### V1.3 Il n'y aura rien d'autres à toucher. 
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAllByOrderByName();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer getOne(Long id) {
        return customerRepository.getOne(id);
    }
    // #### V2.0 Cette méthode en invoque une de la couche Repo, et tant qu'à
    // #### V2.0 faire, autant invoquer findByCategoryOrderByName pour avoir une
    // #### V2.0 liste triée.
    @Override
    public List<Customer> findByCategory(Category category) {
        return customerRepository.findByCategoryOrderByName(category);
    }
}
