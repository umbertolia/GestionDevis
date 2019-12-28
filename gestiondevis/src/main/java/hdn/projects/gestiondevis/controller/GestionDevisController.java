package hdn.projects.gestiondevis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.exception.GestionDevisErrorDetails;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.service.IGestion;
import hdn.projects.gestiondevis.utils.EtatOperation;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("${controller.devisPath}")
public class GestionDevisController {

	@Autowired
	private IGestion devisService;


	/**
	 * @return
	 * @throws GestionDevisException
	 */
	@GetMapping(value = "/liste")
	public ResponseEntity<Collection<Devis>> getListeDevis() throws GestionDevisException {
		Collection<Devis> liste = devisService.getEntities();
		return new ResponseEntity<Collection<Devis>>(liste, HttpStatus.FOUND);
	}
	
	/**
	 * @param devis
	 * @return
	 * @throws GestionDevisException
	 */
	@PostMapping(value = "/liste")
	@Transactional
	public ResponseEntity<Devis> saveDevis(@RequestBody Devis devis) throws GestionDevisException {

		final Devis devisDB = devisService.saveOrUpdateEntity(devis, EtatOperation.CREATE);
		return new ResponseEntity<Devis>(devisDB, HttpStatus.CREATED);
	}

	/**
	 * @param reference
	 * @return
	 * @throws GestionDevisException
	 */
	@GetMapping(value = "/liste/{refDevis}")
	public ResponseEntity<Devis> findDevisByReference(@PathVariable("refDevis") Long reference)
			throws GestionDevisException {

		Devis devisDB = devisService.getEntityWithOptional(reference);
		return new ResponseEntity<Devis>(devisDB, HttpStatus.FOUND);
	}

	/**
	 * @param idUser
	 * @return
	 * @throws GestionDevisException
	 */
	@GetMapping(value = "/user/{refUser}")
	public ResponseEntity<Collection<Devis>> findDevisByUser(@PathVariable("refUser") Long idUser)
			throws GestionDevisException {

		Collection<Devis> liste = devisService.getEntitiesFrom(idUser);
		return new ResponseEntity<Collection<Devis>>(liste, HttpStatus.FOUND);
	}


	/**
	 * @param reference
	 * @param devis
	 * @return
	 * @throws GestionDevisException
	 */
	@PutMapping(value = "/liste/{refDevis}")
	public ResponseEntity<Devis> updateDevis(@PathVariable(value = "refDevis") Long reference, @RequestBody Devis devis)
			throws GestionDevisException {

		Devis devisToUpdate = devisService.getEntityWithOptional(reference);
		if (devisToUpdate == null) {
			return new ResponseEntity<Devis>(devisToUpdate, HttpStatus.NOT_FOUND);
		}
		Devis devisUpdated = devisService.saveOrUpdateEntity(devis, EtatOperation.UPDATE);
		return new ResponseEntity<Devis>(devisUpdated, HttpStatus.OK);
	}


	/**
	 * @param reference
	 * @return
	 * @throws GestionDevisException
	 */
	@DeleteMapping(value = "/liste/{refDevis}")
	public ResponseEntity<Void> deleteDevis(@PathVariable(value = "refDevis") Long reference)
			throws GestionDevisException {

		devisService.deleteEntity(reference);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


	/**
	 * @param referenceDevis
	 * @param travaux
	 * @return
	 * @throws GestionDevisException
	 */
	@PostMapping(value = "/liste/{refDevis}")
	public ResponseEntity<Travaux> saveTravaux(@PathVariable(value = "refDevis") Long referenceDevis,
			@RequestBody Travaux travaux) throws GestionDevisException {

		Travaux travauxDB = devisService.gererTravaux(referenceDevis, travaux, EtatOperation.CREATE);
		return new ResponseEntity<Travaux>(travauxDB, HttpStatus.CREATED);
	}
	
	/**
	 * @return
	 * @throws GestionDevisException
	 */
	@GetMapping(value = "/travaux/encours")
	public ResponseEntity<Collection<Travaux>> getTravauxEnCours()
			throws GestionDevisException {

		Collection<Travaux> listeTravauxEnCours = devisService.recupererTravauxEnCours();
		return new ResponseEntity<Collection<Travaux>>(listeTravauxEnCours, HttpStatus.FOUND);
	}

	/**
	 * @return
	 * @throws GestionDevisException
	 */
	@GetMapping(value = "/travaux/nonpayes")
	public ResponseEntity<Collection<Travaux>> getTravauxTerminesNonPayes()
			throws GestionDevisException {

		Collection<Travaux> listeTravauxTerminesNonPayes = devisService.recupererTravauxTerminesNonRegles();
		return new ResponseEntity<Collection<Travaux>>(listeTravauxTerminesNonPayes, HttpStatus.FOUND);
	}

	
	 @ExceptionHandler({ RuntimeException.class })
	    public ResponseEntity<Object> handleException(Exception exception) throws GestionDevisException {
		 GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(exception, "GestionDevisException levée");
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	    }
	
}
