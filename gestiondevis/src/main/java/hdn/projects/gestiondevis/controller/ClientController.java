package hdn.projects.gestiondevis.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("${controller.clientPath}")
@Api(value = "ClientController: contient les operations de traitement des users")
public class ClientController {

	@Autowired
	private IUtilisateur userService;

	/**
	 * @return
	 */
	@GetMapping(value = "/users")
	@ApiOperation(value = "Liste des uses", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK : liste de users recupérée"),
			@ApiResponse(code = 204, message = "No Content : liste des users vide") })
	public ResponseEntity<Collection<Utilisateur>> getUsers() throws GestionDevisException {

		Collection<Utilisateur> users = userService.getEntities();
		if (!users.isEmpty()) {
			return new ResponseEntity<Collection<Utilisateur>>(users, HttpStatus.OK);
		}
		return new ResponseEntity<Collection<Utilisateur>>(users, HttpStatus.NO_CONTENT);
	}

	/**
	 * @param reference
	 * @return
	 */
	@GetMapping(value = "/gerant/{refUser}")
	@ApiOperation(value = "Liste des uses selon 1 user", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK : liste de users recupérée selon 1 user"),
			@ApiResponse(code = 204, message = "No Content : Aucun user trouvé selon 1 user"),
			@ApiResponse(code = 404, response = GestionDevisException.class, message = "Not found : user de reference non trouvé") })
	public ResponseEntity<Collection<Utilisateur>> getClientsFrom(@PathVariable("refUser") Long idUser)
			throws GestionDevisException {

		Collection<Utilisateur> users = userService.getEntitiesFrom(idUser);
		if (!users.isEmpty()) {
			return new ResponseEntity<Collection<Utilisateur>>(users, HttpStatus.OK);
		}
		return new ResponseEntity<Collection<Utilisateur>>(users, HttpStatus.NO_CONTENT);
	}

	/**
	 * @param login
	 * @return
	 */
	@GetMapping(value = "/users/{refUser}")
	@ApiOperation(value = "Rechercher d'un user selon son id", response = Utilisateur.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK: user trouvé"),
			@ApiResponse(code = 404, response = GestionDevisException.class, message = "Not Found : Aucun user trouvé") })
	public ResponseEntity<Utilisateur> findUserByReference(@PathVariable("refUser") Long refUser)
			throws GestionDevisException {

		Utilisateur utilisateur = userService.getEntityWithOptional(refUser);
		return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.FOUND);
	}

	/**
	 * @param utilisateur
	 * @return
	 */
	@PostMapping(value = "/users")
	@ApiOperation(value = "sauvegarde d'un user", response = Utilisateur.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created: user crée"),
			@ApiResponse(code = 409, response = GestionDevisException.class, message = "Conflict : le user existe deja"),
			@ApiResponse(code = 304, message = "Not Modified : le user n'a pas été crée") })
	public ResponseEntity<Utilisateur> saveUser(@Valid @RequestBody Utilisateur utilisateur)
			throws GestionDevisException {

		final Utilisateur userSave = userService.saveOrUpdateEntity(utilisateur, EtatOperation.CREATE);
		if (userSave != null) {
			return new ResponseEntity<Utilisateur>(userSave, HttpStatus.CREATED);
		}
		return new ResponseEntity<Utilisateur>(userSave, HttpStatus.NOT_MODIFIED);
	}

	/**
	 * @param reference
	 * @param user
	 * @return
	 */
	@PutMapping(value = "/users/{refUser}")
	@ApiOperation(value = "Update d'un user", response = Utilisateur.class)
	@ApiResponses(value = {
			@ApiResponse(code = 404, response = GestionDevisException.class, message = "Not Found : le user a mettre à jour n'existe pas"),
			@ApiResponse(code = 200, message = "Ok: le user est mis a jour"),
			@ApiResponse(code = 304, message = "Not Modified: le user n'a pas été modifié") })
	public ResponseEntity<Utilisateur> updateClient(@PathVariable(value = "refUser") Long refUser,
			@RequestBody Utilisateur utilisateur) throws GestionDevisException {

		Utilisateur userUpdated = userService.saveOrUpdateEntity(utilisateur, EtatOperation.UPDATE);
		if (userUpdated != null) {
			return new ResponseEntity<Utilisateur>(userUpdated, HttpStatus.OK);
		}
		return new ResponseEntity<Utilisateur>(userUpdated, HttpStatus.NOT_MODIFIED);
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/users/{refUser}")
	@ApiOperation(value = "Suppression d'un user", response = String.class)
	@ApiResponses(value = @ApiResponse(code = 204, message = "EtatOperation: le user est supprimé"))
	public ResponseEntity<EtatOperation> deleteUser(@PathVariable(value = "refUser") Long refUser)
			throws GestionDevisException {
		userService.deleteEntity(refUser);
		return new ResponseEntity<EtatOperation>(EtatOperation.VALID, HttpStatus.NO_CONTENT);
	}

	/*
	 * public ResponseEntity<Boolean> sendMailToCustomer(@RequestBody MailDTO
	 * loanMailDto) {
	 * 
	 * Customer customer =
	 * customerService.findCustomerById(loanMailDto.getCustomerId()); if (customer
	 * == null) { String errorMessage =
	 * "The selected Customer for sending email is not found in the database";
	 * LOGGER.info(errorMessage); return new ResponseEntity<Boolean>(false,
	 * HttpStatus.NOT_FOUND); } else if (customer != null &&
	 * StringUtils.isEmpty(customer.getEmail())) { String errorMessage =
	 * "No existing email for the selected Customer for sending email to";
	 * LOGGER.info(errorMessage); return new ResponseEntity<Boolean>(false,
	 * HttpStatus.NOT_FOUND); }
	 * 
	 * SimpleMailMessage mail = new SimpleMailMessage();
	 * mail.setFrom(loanMailDto.MAIL_FROM); mail.setTo(customer.getEmail());
	 * mail.setSentDate(new Date()); mail.setSubject(loanMailDto.getEmailSubject());
	 * mail.setText(loanMailDto.getEmailContent());
	 * 
	 * try { javaMailSender.send(mail); } catch (MailException e) { return new
	 * ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN); } return new
	 * ResponseEntity<Boolean>(true, HttpStatus.OK); }
	 */

}