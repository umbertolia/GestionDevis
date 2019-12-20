package hdn.projects.gestiondevis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hdn.projects.gestiondevis.dao.IDevisRepository;
import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Statistiques;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;

//l'annotation @Service est optionnelle ici, car il n'existe qu'une
//seule implémentation de l'interface IGestion
@Service(value = "gestionService")
public class GestionImpl implements IGestion {

	@Autowired
	private IDevisRepository devisRepository;
	
	@Override
	@Transactional(readOnly = false)
	public EtatOperation creerModifierDevis(Devis devis, EtatOperation etatOperation) throws GestionDevisException{
		try {
			devisRepository.save(devis);
			return EtatOperation.VALID;
		} catch (Exception ex) {
			switch (etatOperation) {
			case CREATE:
				throw new GestionDevisException("Create Devis Error",
						"Erreur de création du devis : ", HttpStatus.INTERNAL_SERVER_ERROR);
			default:
				throw new GestionDevisException("Update Devis Error",
						"Erreur de mise à jour du devis: " + HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Stream<Devis> getAllDevis() {
		return devisRepository.getAllDevisStream();
	}

	@Override
	public Stream<Devis> recupererDevis(Utilisateur utilisateur) {
		return devisRepository.getDevisStream(utilisateur.getId());
	}

	@Override
	public Devis recupererDevis(Long refDevis) throws GestionDevisException {
		Optional <Devis> optDevis = devisRepository.findByReference(refDevis);
		optDevis.orElseThrow(() -> new GestionDevisException("Get Devis Error",
				"Erreur de recuperation du devis  : " + refDevis, HttpStatus.INTERNAL_SERVER_ERROR));
		return optDevis.get();
	}

	@Override
	public EtatOperation gererTravaux(Long refDevis, Travaux travaux, EtatOperation etatOperation) throws GestionDevisException{
		// recup du devis
		Devis devisDB = this.recupererDevis(refDevis);
		devisDB.setTravaux(travaux);
		try {
			devisRepository.save(devisDB);
			return EtatOperation.VALID;
		} catch (Exception ex) {
			switch (etatOperation) {
			case CREATE:
				throw new GestionDevisException("Create Devis Error",
						"Erreur de création des travaux : ", HttpStatus.INTERNAL_SERVER_ERROR);
			default:
				throw new GestionDevisException("Update Devis Error",
						"Erreur de mise à jour des travaux : " + HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	public List<Travaux> recupererTravauxEnCours() throws GestionDevisException{
		return (this.getAllDevis()
		.map(devis -> devis.getTravaux())
		.filter(travaux -> travaux.getEtat().equals(EtatOperation.IN_PROGRESS))
		.collect(Collectors.toList()));
	}

	@Override
	public List<Travaux> recupererTravauxTerminesNonRegles() throws GestionDevisException{
		return (this.getAllDevis()
				.map(devis -> devis.getTravaux())
				.filter(trav -> !trav.isTravauxPayes())
				.filter(trav -> trav.getEtat().equals(EtatOperation.FINISHED))
				.collect(Collectors.toList()));
	}

	@Override
	public Statistiques dresserBilan() throws GestionDevisException{
		// TODO Auto-generated method stub
		return null;
	}
	
}
