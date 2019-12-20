package hdn.projects.gestiondevis.service;

import java.util.List;
import java.util.stream.Stream;

import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Statistiques;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;

public interface IGestion {

	EtatOperation creerModifierDevis(Devis devis, EtatOperation etatOperation) throws GestionDevisException;
	
	Stream<Devis> getAllDevis();
	
	Stream<Devis> recupererDevis(Utilisateur utilisateur);
	
	Devis recupererDevis(Long refDevis) throws GestionDevisException;
	
	EtatOperation gererTravaux(Long refDevis, Travaux travaux, EtatOperation etatOperation) throws GestionDevisException;
	
	List<Travaux> recupererTravauxEnCours() throws GestionDevisException; // permet de voir si l'avoir a ete recu
	
	List<Travaux> recupererTravauxTerminesNonRegles() throws GestionDevisException;
	
	Statistiques dresserBilan() throws GestionDevisException;
 	
	
}
