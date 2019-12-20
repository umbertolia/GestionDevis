package hdn.projects.gestiondevis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.service.IUtilisateur;
import hdn.projects.gestiondevis.utils.EtatOperation;

@RestController
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/client/*")
public class ClientController {


	@Autowired
	private IUtilisateur<Utilisateur> userService;
	

	/**
	 * @return
	 */
	@GetMapping(value = "/users")
	public ResponseEntity<Collection<Utilisateur>> getUsers() throws GestionDevisException {
		Collection<Utilisateur> users = userService.getEntities();	
		return new ResponseEntity<Collection<Utilisateur>>(users, HttpStatus.FOUND);
	}


	/**
	 * @param utilisateur
	 * @return
	 */
	@PostMapping(value = "/users")
	@Transactional
	public ResponseEntity<Utilisateur> saveUser(@RequestBody Utilisateur utilisateur) {
		final Utilisateur userSave = userService.saveOrUpdateEntity(utilisateur, EtatOperation.CREATE);
		return new ResponseEntity<Utilisateur>(userSave, HttpStatus.CREATED);
	}
	
	/**
	 * @param login
	 * @return
	 */
	@GetMapping(value = "/users/{refClient}")
	public ResponseEntity<Utilisateur> findUserByReference(@PathVariable("refClient") Long login) {
		Utilisateur utilisateur = userService.getEntityWithOptional(login);
			    return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.FOUND);
	}


	/**
	 * @param reference
	 * @param user
	 * @return
	 */
	@PutMapping(value = "/users/{refUser}")
    public ResponseEntity<Utilisateur> updateClient(@PathVariable(value = "refUser") Long refUser, @RequestBody Utilisateur utilisateur) {
        
		Utilisateur userToUpdate = userService.getEntityWithOptional(refUser);
        if (userToUpdate == null) {
            return new ResponseEntity<Utilisateur>(utilisateur,HttpStatus.NOT_FOUND);
        } 
        Utilisateur userUpdated = userService.saveOrUpdateEntity(utilisateur, EtatOperation.UPDATE);
        return new ResponseEntity<Utilisateur>(userUpdated, HttpStatus.OK);
    }
	
	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/users/{refUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "refUser") Long refUser) {
        userService.deleteEntity(refUser);
    return new ResponseEntity<Void>(HttpStatus.GONE);
     }
	
	
}