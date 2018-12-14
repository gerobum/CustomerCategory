
package fr.miage.web.controller;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;


// #### V0.0 RootController permet de gérer les appels à l'url /
@Controller
@RequestMapping()
public class RootController {
    @RequestMapping(method = RequestMethod.GET)
// #### V0.0 Une requête HTTP/GET sur / utilise le template src/main/resources/templates/home.html
    public String home() {       
        return "home";
    }
}
