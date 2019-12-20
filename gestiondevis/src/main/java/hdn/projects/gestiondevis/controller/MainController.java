package hdn.projects.gestiondevis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Gandalf
 * 
 * La classe est annotée @Controller afin de permettre à Spring d'enregistrer cette classe 
 * comme un contrôleur, et surtout de mémoriser les requêtes que cette classe est capable de gérer
 *
 */
@Controller
public class MainController {
	 private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	    
	    @GetMapping(value = "/test")
	    public ResponseEntity<String> serverTest() 
	    {
	        logger.info("Démarrage des services OK .....");
	        return new ResponseEntity<String>("Réponse du serveur: "+HttpStatus.OK.name(), HttpStatus.OK);
	    }
	    
	    
}
