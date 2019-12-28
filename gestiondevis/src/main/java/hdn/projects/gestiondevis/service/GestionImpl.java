package hdn.projects.gestiondevis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hdn.projects.gestiondevis.dao.IDevisRepository;
import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Statistiques;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;
import hdn.projects.gestiondevis.utils.Utilitaire;

/**
 * @author Gandalf
 *
 */
//l'annotation @Service est optionnelle ici, car il n'existe qu'une
//seule implémentation de l'interface IGestion
@Service(value = "gestionService")
public class GestionImpl implements IGestion {

	private static final Logger logger = LoggerFactory.getLogger(GestionImpl.class);

	@Autowired
	private IDevisRepository devisRepository;

	@Override
	@Transactional(readOnly = false)
	public Devis saveOrUpdateEntity(Devis devis, EtatOperation typeOperation) throws GestionDevisException {

		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), devis);

		switch (typeOperation) {

		case CREATE: {
			// le devis ne doit pas exister
			Devis devisDB = this.getEntity(devis.getReference());
			if (devisDB.equals(devis)) {
				// le user avec ce login existe
				throw new GestionDevisException("Le devis avec la re. " + devis.getReference() + " existe déja",
						HttpStatus.CONFLICT);
			}
			try {
				return devisRepository.save(devis);
			} catch (Exception ex) {
				throw new GestionDevisException("Create Devis Error", "Erreur de création du devis : ",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		case UPDATE: {
			// le devis doit exister -> on remonte une exception a defaut
			this.getEntityWithOptional(devis.getReference());
			return devisRepository.save(devis);
		}

		default: {
			return null;
		}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Devis> getEntities() {
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), null);
		List<Devis> listDevis = devisRepository.getAllDevisStream().collect(Collectors.toList());
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), listDevis);
		return listDevis;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Devis> getEntitiesFrom(Long idUser) {

		// verif si le user existe
		this.getEntityWithOptional(idUser);

		List<Devis> listDevis = devisRepository.getDevisStream(idUser).collect(Collectors.toList());

		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), listDevis);
		return listDevis;
	}

	@Override
	@Transactional(readOnly = true)
	public Devis getEntity(Long refDevis) throws GestionDevisException {
		return devisRepository.getOne(refDevis);
	}

	@Override
	@Transactional(readOnly = true)
	public Devis getEntityWithOptional(Long refEntity) throws GestionDevisException {
		Optional<Devis> optDevis = devisRepository.findByReference(refEntity);
		optDevis.orElseThrow(() -> new GestionDevisException("Get Devis Error",
				"Erreur de recuperation du devis avec la ref. : " + refEntity, HttpStatus.INTERNAL_SERVER_ERROR));
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), optDevis.get());
		return optDevis.get();
	}

	@Override
	@Transactional(readOnly = false)
	public EtatOperation deleteEntity(Long refEntity) throws GestionDevisException {
		this.getEntityWithOptional(refEntity);
		//
		devisRepository.deleteById(refEntity);
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), refEntity);
		
		return EtatOperation.VALID;
	}

	@Override
	@Transactional(readOnly = false)
	public Travaux gererTravaux(Long refDevis, Travaux travaux, EtatOperation etatOperation)
			throws GestionDevisException {
		// recup du devis
		Devis devisDB = this.getEntity(refDevis);
		devisDB.setTravaux(travaux);
		try {
			devisRepository.save(devisDB);
			return travaux;
		} catch (Exception ex) {
			switch (etatOperation) {
			case CREATE:
				throw new GestionDevisException("Create Devis Error", "Erreur de création des travaux : ",
						HttpStatus.INTERNAL_SERVER_ERROR);
			default:
				throw new GestionDevisException("Update Devis Error",
						"Erreur de mise à jour des travaux : " + HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Travaux> recupererTravauxEnCours() throws GestionDevisException {
		return (this.getEntities().stream().map(devis -> devis.getTravaux())
				.filter(travaux -> travaux.getEtat().equals(EtatOperation.IN_PROGRESS)).collect(Collectors.toList()));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Travaux> recupererTravauxTerminesNonRegles() throws GestionDevisException {
		return (this.getEntities().stream().map(devis -> devis.getTravaux()).filter(trav -> !trav.isTravauxPayes())
				.filter(trav -> trav.getEtat().equals(EtatOperation.FINISHED)).collect(Collectors.toList()));
	}

	@Override
	@Transactional(readOnly = true)
	public Statistiques dresserBilan() throws GestionDevisException {
		// TODO Auto-generated method stub
		return null;
	}

}
