package fr.miage.web.controller;

import fr.miage.core.entity.Category;
import fr.miage.core.service.CategoryService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

// #### V1.0 Indicates that an annotated class is a "Controller" 
// #### V1.0 (e.g. a web controller).
// #### V1.0 This annotation serves as a specialization of @Component, allowing 
// #### V1.0 for implementation classes to be autodetected through classpath 
// #### V1.0 scanning. It is typically used in combination with annotated 
// #### V1.0 handler methods based on the RequestMapping annotation.
// #### V1.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html
@Controller
// #### V1.0 Toutes les méthodes de cette classe annotées @RequestMapping ou
// #### V1.0 @GetMapping ou @PostMapping seront mappées sur une url basée sur
// #### V1.0 /category
// #### V1.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html
@RequestMapping("/category")
public class CategoryController {

// #### V1.1 LOGGER Pour faire du Log (qui s'applique à cette classe) 
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

// #### V1.0 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html
// #### V1.0 Injection de dépendance (en l'occurrence CategoryServiceImpl)
    @Autowired
    CategoryService categoryService;

// #### V1.0 La méthode findAll 
// #### V1.0 Comportement exécuté dans le cas d'une requête HTTP/GET sur
// #### V1.0 /category ou  /category/ 
    @RequestMapping(method = RequestMethod.GET)
// #### V1.0 @GetMapping({"","/"}) aurait pu être remplacé par 
// #### V1.0 @RequestMapping(method = RequestMethod.GET), une affaire de goût.

// #### V1.0 Spring utilise l'injection de dépendance pour exécuter un 
// #### V1.0 comportement défini par cette méthode.
// #### V1.0 Ce comportement est plutôt une "interprétation" de cette méthode.
// #### V1.0 Par exemple, la chaîne qu'elle retourne ne sera pas dans la
// #### V1.0 réponse HTTP (sauf si on utilise @ResponseBody). Cette chaîne doit
// #### V1.0 correspondre au nom d'un template html qui se trouve dans le dossier
// #### V1.0 src/main/resources/templates
// #### V1.0 Il s'agit du fichier src/main/resources/templates/category/list.html
// #### V1.0 Par ailleurs, l'emploi du paramètre Model model permet d'envoyer
// #### V1.0 des données au template (à la vue). En l'occurrence, il s'agit de
// #### V1.0 la liste de tous les clients enregistrés dans la base de données
// #### V1.0 qui est envoyée à la vue pour être affichée.    
// #### V1.0 Le retour et les paramètres de cette fonction pilote le comportement 
// #### V1.0 de Spring. 
// #### V1.0 Pour en savoir plus : 
// #### V1.0  - https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-arguments  
// #### V1.0  - https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-return-types
    public String findAll(Model model) {
        // #### V1.0 Le but de l'URL /category est d'afficher la liste des
        // #### V1.0 clients existants. La méthode categoryService.findAll()
        // #### V1.0 interroge la BDD pour retourner cette liste qui est envoyée
        // #### V1.0 au template src/main/resources/templates/category/list.html
        // #### V1.0 par l'intermédiaire du modèle dans lequel il est identifié
        // #### V1.0 par la clé "categories".
        model.addAttribute("categories", categoryService.findAll());

        // #### V1.1 Utilisation du LOGGER pour montrer son utilisation qui peut
        // #### V1.1 servir à faire des stats ou du déboguage. 
        // #### V1.1 flot de log qui affiche l'URL invoquée.
        LOGGER.info("HTTP/GET - /category");
        // #### V1.1 ...
        LOGGER.info("TEMPLATE - category/list.html");
        return "category/list";
    }

// #### V1.0 La méthode create demande le formulaire de création d'un nouveau
// #### V1.0 client. Ce formulaire présente des champs à remplir par 
// #### V1.0 l'utilisateur. Sa validation provoque le lancement d'une requête
// #### V1.0 HTTP/POST (méthode created un peu plus loin).
// #### V1.0 Ce comportement est exécuté dans le cas d'une requête HTTP/GET sur
// #### V1.0 /category/create
    @GetMapping("/create")
    public String create(Model model) {
// #### V1.0 Un client (vide) est créé. Il est rare d'utiliser new en
// #### V1.0 Spring, sauf dans le cas des entités (comme c'est le cas ici)
        Category category = new Category();
// #### V1.0 Il est envoyé à la "vue" par l'entremise du modèle (model)
// #### V1.0 Il sera renseigné dans le formulaire.
        model.addAttribute("category", category);
// #### V1.0 Le template src/main/resources/templates/category/edit.html

        // #### V1.1 ...
        LOGGER.info("HTTP/GET - /category/create");
        // #### V1.1 ...
        LOGGER.info("TEMPLATE - category/edit.html");
        return "category/edit";
    }

// #### V1.0 La méthode created enregistre le client saisi dans le  
// #### V1.0 formulaire (si ses contraintes sont respectées) 
// #### V1.0 Ce comportement est exécuté dans le cas d'une requête HTTP/POST sur
// #### V1.0 /category/create (engendrée par le formulaire)
    @PostMapping("/create")
    public String created(@Valid Category category, BindingResult br) {
// #### V1.0 L'entité Category (@Entity) possède un champs name qui doit 
// #### V1.0 éventuellement respecter des contraintes. 
// #### V1.0 L'annotation @Valid demande à Spring (ou plutôt Hibernate Validator)
// #### V1.0 de vérifier le respect de ces contraintes.
// #### V1.0 Le paramètre BindingResult permet de vérifier les éventuels erreurs.
        if (br.hasErrors()) {
// #### V1.0 En cas d'erreur, Le template 
// #### V1.0 src/main/resources/templates/category/edit.html est invoqué
            return "category/edit";
        }
        // #### V2.3 Certaines erreurs sont évitées grâce à @Valid et BindingResult,
        // #### V2.3 Mais pas toutes. D'autres erreurs, impossible à contrôler
        // #### V2.3 dans le formulaire peuvent survenir. On peut les gérer ici
        // #### V2.3 dans un "try catch".
        try {
            categoryService.save(category);
        } catch (DataIntegrityViolationException ex) {
            // #### V2.3 Si une exception survient ici, c'est, a priori, parce        
            // #### V2.3 qu'on a essayé de créer une catégorie dont le nom existe      
            // #### V2.3 déjà et la contrainte d'unicité du champ name de la   
            // #### V2.3 table Category a été violée. 

            // #### V2.3 On retourne au formulaire la clé qui correspond à
            // #### V2.3 au message de cette erreur dans messages.properties.
            br.rejectValue("name", "error.category.name.unique");
            return "category/edit";
        } catch (Exception ex) {
            // #### V2.3 Si une autre exception survient ici, c'est une erreur             
            // #### V2.3 inattendue. On retourne au formulaire la clé qui
            // #### V2.3 correspond à un message plus général.
            br.rejectValue("name", "error.creating.category");
            return "category/edit";
        }
// #### V1.0 Si tout s'est bien passé, la client est enregistré dans la BDD.

// #### V1.0 "redirect:" indique à Spring de faire une redirection, en d'autres
// #### V1.0 termes, ça revient à une requête HTTP/GET sur /category
        return "redirect:/category";
    }

// #### V1.0 Les deux méthodes suivantes, edit et edited, servent à modifier un
// #### V1.0 client. La première (HTTP/GET) invoque le formulaire, la seconde
// #### V1.0 (HTTP/POST) réalise la modification s'il n'y a pas d'erreur.
    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id).get());
        return "category/edit";
    }

    @PostMapping("/edit")
    public String edited(@Valid Category category, BindingResult br) {
        if (br.hasErrors()) {
            return "category/edit";
        }
// #### V1.0 (HTTP/POST) save() peut être utilisé soit
// #### V1.0    - pour la création en l'absence d'identifiant (dans ce cas, 
// #### V1.0      il calculé automatiquement)
// #### V1.0    - pour la modification avec un identifiant.
        // #### V2.3 Certaines erreurs sont évitées grâce à @Valid et BindingResult,
        // #### V2.3 Mais pas toutes. D'autres erreurs, impossible à contrôler
        // #### V2.3 dans le formulaire peuvent survenir. On peut les gérer ici
        // #### V2.3 dans un "try catch".
        try {
            categoryService.save(category);
        } catch (DataIntegrityViolationException ex) {
            // #### V2.3 Si une exception survient ici, c'est, a priori, parce        
            // #### V2.3 qu'on a essayé de créer une catégorie dont le nom existe      
            // #### V2.3 déjà et la contrainte d'unicité du champ name de la   
            // #### V2.3 table Category a été violée. 

            // #### V2.3 On retourne au formulaire la clé qui correspond à
            // #### V2.3 au message de cette erreur dans messages.properties.
            br.rejectValue("name", "error.category.name.unique");
            return "category/edit";
        } catch (Exception ex) {
            // #### V2.3 Si une autre exception survient ici, c'est une erreur             
            // #### V2.3 inattendue. On retourne au formulaire la clé qui
            // #### V2.3 correspond à un message plus général.
            br.rejectValue("name", "error.updating.category");
            return "category/edit";
        }
        return "redirect:/category";
    }

// #### V1.0 La méthode delete permet de retirer un client de la BDD
// #### V1.0 à partir de par son identifiant.
// #### V1.0 Ce comportement est exécuté dans le cas d'une requête HTTP/GET sur
// #### V1.0 /category/delete/{id} où est un identifiant entier (long).
    @GetMapping("/delete/{id}")
// #### V1.0 Une autre façon de passer des arguments. Elle est précisée grâce à
// #### V1.0 @PathVariable("id") où "id" correspond à {id}
    public String delete(@PathVariable("id") Long id, Model model) {
        // #### V2.3 Il est impossible de supprimer une catégorie tant qu'un client
        // #### V2.3 y est associé. Mais il est difficile de le vérifier dans le 
        // #### V2.3 formulaire. C'est fait ici, en attrapant les éventuelles
        // #### V2.3 exceptions. 
        try {
            categoryService.delete(id);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("error", "error.category.has.customers");
            return "category/list";

        } catch (Exception ex) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("error", "error.deleting.category");
            return "category/list";
        }
        return "redirect:/category";
    }

    // #### V2.3 Même en l'absence de ce controller, 
    // #### V2.3 si le template error.html existe
    // #### V2.3 alors Spring l'ouvre en cas d'erreur.
    // #### V2.3 Mais ce contrôleur peut servir à ajouter de l'information,
    // #### V2.3 Comme par exemple, ici, l'exception elle-même.
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String constraintViolationException(HttpServletRequest req, Model model, Exception ex) {
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());
        return "errors/error";
    }
}
