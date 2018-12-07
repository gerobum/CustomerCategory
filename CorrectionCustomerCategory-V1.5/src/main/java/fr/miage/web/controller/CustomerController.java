package fr.miage.web.controller;

import fr.miage.core.entity.Customer;
import fr.miage.core.service.CategoryService;
import fr.miage.core.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// #### V0.0 Indicates that an annotated class is a "Controller" 
// #### V0.0 (e.g. a web controller).
// #### V0.0 This annotation serves as a specialization of @Component, allowing 
// #### V0.0 for implementation classes to be autodetected through classpath 
// #### V0.0 scanning. It is typically used in combination with annotated 
// #### V0.0 handler methods based on the RequestMapping annotation.

// #### V0.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html
@Controller
// #### V0.0 Toutes les méthodes de cette classe annotées @RequestMapping ou
// #### V0.0 @GetMapping ou @PostMapping seront mappées sur une url basée sur
// #### V0.0 /customer
// #### V0.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html
@RequestMapping("/customer")
public class CustomerController {


// #### V0.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html
// #### V0.0 Injection de dépendance (en l'occurrence CustomerServiceImpl)
    @Autowired
    CustomerService customerService;
    
// #### V1.4 Pour pouvoir envoyer la liste de catégories dans certaines vues.  
// #### V1.4 Injection de dépendance (en l'occurrence CategoryServiceImpl) 
    @Autowired  
    CategoryService categoryService;

// #### V0.0 La méthode findAll 
// #### V0.0 Comportement exécuté dans le cas d'une requête HTTP/GET sur
// #### V0.0 /customer ou  /customer/ 
    @RequestMapping(method = RequestMethod.GET)
// #### V0.0 @GetMapping({"","/"}) aurait pu être remplacé par 
// #### V0.0 @RequestMapping(method = RequestMethod.GET), une affaire de goût.

// #### V0.0 Spring utilise l'injection de dépendance pour exécuter un 
// #### V0.0 comportement défini par cette méthode.
// #### V0.0 Ce comportement est plutôt une "interprétation" de cette méthode.
// #### V0.0 Par exemple, la chaîne qu'elle retourne ne sera pas dans la
// #### V0.0 réponse HTTP (sauf si on utilise @ResponseBody). Cette chaîne doit
// #### V0.0 correspondre au nom d'un template html qui se trouve dans le dossier
// #### V0.0 src/main/resources/templates
// #### V0.0 Il s'agit du fichier src/main/resources/templates/customer/list.html
// #### V0.0 Par ailleurs, l'emploi du paramètre Model model permet d'envoyer
// #### V0.0 des données au template (à la vue). En l'occurrence, il s'agit de
// #### V0.0 la liste de tous les clients enregistrés dans la base de données
// #### V0.0 qui est envoyée à la vue pour être affichée.    
// #### V0.0 Le retour et les paramètres de cette fonction pilote le comportement 
// #### V0.0 de Spring. 
// #### V0.0 Pour en savoir plus : 
// #### V0.0  - https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-arguments  
// #### V0.0  - https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-return-types
    public String findAll(Model model) {
        // #### V0.0 Le but de l'URL /customer est d'afficher la liste des
        // #### V0.0 clients existants. La méthode customerService.findAll()
        // #### V0.0 interroge la BDD pour retourner cette liste qui est envoyée
        // #### V0.0 au template src/main/resources/templates/customer/list.html
        // #### V0.0 par l'intermédiaire du modèle dans lequel il est identifié
        // #### V0.0 par la clé "customers".
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

// #### V0.0 La méthode create demande le formulaire de création d'un nouveau
// #### V0.0 client. Ce formulaire présente des champs à remplir par 
// #### V0.0 l'utilisateur. Sa validation provoque le lancement d'une requête
// #### V0.0 HTTP/POST (méthode created un peu plus loin).
// #### V0.0 Ce comportement est exécuté dans le cas d'une requête HTTP/GET sur
// #### V0.0 /customer/create
    @GetMapping("/create")
    public String create(Model model) {
// #### V0.0 Un client (vide) est créé. Il est rare d'utiliser new en
// #### V0.0 Spring, sauf dans le cas des entités (comme c'est le cas ici)
        Customer customer = new Customer();
// #### V0.0 Il est envoyé à la "vue" par l'entremise du modèle (model)
// #### V0.0 Il sera renseigné dans le formulaire.
        model.addAttribute("customer", customer);
// #### V1.4 Pour pouvoir définir ou changer la catégorie d'un client, il faut
// #### V1.4 envoyer toutes la liste.
        model.addAttribute("categories", categoryService.findAll());
// #### V0.0 Le template src/main/resources/templates/customer/edit.html
        return "customer/edit";
    }

// #### V0.0 La méthode created enregistre le client saisi dans le  
// #### V0.0 formulaire (si ses contraintes sont respectées) 
// #### V0.0 Ce comportement est exécuté dans le cas d'une requête HTTP/POST sur
// #### V0.0 /customer/create (engendrée par le formulaire)
    @PostMapping("/create")
    public String created(@Valid Customer customer, BindingResult br, Model model) {
// #### V0.0 L'entité Customer (@Entity) possède un champs name qui doit 
// #### V0.0 éventuellement respecter des contraintes. 
// #### V0.0 L'annotation @Valid demande à Spring (ou plutôt Hibernate Validator)
// #### V0.0 de vérifier le respect de ces contraintes.
// #### V0.0 Le paramètre BindingResult permet de vérifier les éventuels erreurs.
        if (br.hasErrors()) {
// #### V0.0 En cas d'erreur, Le template 
// #### V0.0 src/main/resources/templates/customer/edit.html est invoqué

// #### V1.4 On constate qu'en cas d'erreur, la liste de catégories envoyée 
// #### V1.4 initialement a disparu. Il faut la recréer.
            model.addAttribute("categories", categoryService.findAll());
            return "customer/edit";
        }
// #### V0.0 Si tout s'est bien passé, la client est enregistré dans la BDD.
        customerService.save(customer);

// #### V0.0 "redirect:" indique à Spring de faire une redirection, en d'autres
// #### V0.0 termes, ça revient à une requête HTTP/GET sur /customer
        return "redirect:/customer";
    }

// #### V0.0 Les deux méthodes suivantes, edit et edited, servent à modifier un
// #### V0.0 client. La première (HTTP/GET) invoque le formulaire, la seconde
// #### V0.0 (HTTP/POST) réalise la modification s'il n'y a pas d'erreur.
    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id).get());
// #### V1.4 Pour pouvoir définir ou changer la catégorie d'un client, il faut
// #### V1.4 envoyer toutes la liste.
        model.addAttribute("categories", categoryService.findAll());
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String edited(@Valid Customer customer, BindingResult br, Model model) {
        if (br.hasErrors()) {
// #### V1.4 On constate qu'en cas d'erreur, la liste de catégories envoyée 
// #### V1.4 initialement a disparu. Il faut la recréer.
            model.addAttribute("categories", categoryService.findAll());
            return "customer/edit";
        }
// #### V0.0 (HTTP/POST) save() peut être utilisé soit
// #### V0.0    - pour la création en l'absence d'identifiant (dans ce cas, 
// #### V0.0      il calculé automatiquement)
// #### V0.0    - pour la modification avec un identifiant.
        customerService.save(customer);
        return "redirect:/customer";
    }

// #### V0.0 La méthode delete permet de retirer un client de la BDD
// #### V0.0 à partir de par son identifiant.
// #### V0.0 Ce comportement est exécuté dans le cas d'une requête HTTP/GET sur
// #### V0.0 /customer/delete/{id} où est un identifiant entier (long).
    @GetMapping("/delete/{id}")
// #### V0.0 Une autre façon de passer des arguments. Elle est précisée grâce à
// #### V0.0 @PathVariable("id") où "id" correspond à {id}
    public String delete(@PathVariable("id") Long id) {
        customerService.delete(id);
        return "redirect:/customer";
    }
}
