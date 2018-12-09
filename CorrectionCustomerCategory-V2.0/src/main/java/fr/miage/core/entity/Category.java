package fr.miage.core.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// #### V1.0 Pas grand changement par rapport avec Customer.
@Entity
// #### V1.0 C'est une entité (donc une table)
// #### V1.0 Le champ "name" doit être unique
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
public class Category implements Serializable {

    // #### V1.0 Il faut définir un identifiant,
    @Id
    // #### V1.0 et son mode de génération.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // #### V1.0 Il doit exister. 
    @NotNull
    // #### V1.0 Mais, à la différence du nom des clients. Le nom d'une catégorie 
    // #### V1.0 ne possède qu'un et un seul caractère (A, e, $, ...)  
    @Size(min = 1, max = 1)
    private String name;
    
    // #### V1.4 Une catégorie peut catégoriser plusieurs clients @OneToMany
    // #### V1.4 C'est une relation bidirectionnelle. 
    // #### V1.4 Le paramètre (mappedBy = "category") indique que Category est 
    // #### V1.4 la table esclave. C'est-à-dire que c'est la table Customer qui
    // #### V1.4 contient une clé étrangère "id_catégorie".
    @OneToMany(mappedBy = "category")
    private List<Customer> customers;

    public Category(String name) {
        this.name = name;
    }

    // #### V1.0 Category est une entité (@Entity) donc un bean. Un bean doit 
    // #### V1.0 posséder un contructeur par défaut, afin que Spring puisse
    // #### V1.0 l'instancier.
    public Category() {
    }

    // #### V1.0 ainsi que des getters et des setters pour tous les attributs.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
