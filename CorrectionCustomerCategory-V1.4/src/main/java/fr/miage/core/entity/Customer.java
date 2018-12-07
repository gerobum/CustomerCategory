package fr.miage.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// #### V0.0 @Entity pour indiquer que c'est une entité JPA qui sera traduit
// #### V0.0 en table.
@Entity
// #### V0.0 En l'absence de nom de table, celle-ci s'appelle Customer (comme 
// #### V0.0 la classe)
// #### V0.0 Par ailleurs, il est précisé que le champ "name" doit être unique.
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
public class Customer implements Serializable {

    // #### V0.0 Pour définir l'identifiant de la table.
    @Id
    // #### V0.0 Pour laisser à Spring le soin de générer un identifiant unique.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // #### V0.0 javax.validation.constraints offre la possibilité de demander à
    // #### V0.0 Spring de vérifier le respect de certaines contraintes.   
    // #### V0.0 Par exemple, ici pour préciser que ce champ ne doit pas rester nul    
    @NotNull
    // #### V0.0 Pour préciser que sa taille est de un et un seul caractère.   
    @Size(min = 2, max = 30)
    private String name;
    
    // #### V1.4 Un client appartient à une et une seule catégorie @ManyToOne
    @ManyToOne
    private Category category;
    
    public Customer(String name) {
        this.name = name;
    }

    // #### V0.0 Customer est une entité (@Entity) donc un bean. Un bean doit 
    // #### V0.0 posséder un contructeur par défaut, afin que Spring puisse
    // #### V0.0 l'instancier.
    public Customer() {
    }

    // #### V0.0 ainsi que des getters et des setters pour tous les attributs.
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
    // #### V1.4 Chaque fois qu'un nouvel attribut est ajouté, il ne faut pas
    // #### V1.4 oublier les getters et les setters.
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
