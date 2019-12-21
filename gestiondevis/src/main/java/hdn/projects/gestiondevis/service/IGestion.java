package hdn.projects.gestiondevis.service;

import java.util.List;

import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Statistiques;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;

public interface IGestion extends IMainAction<Devis, Long> {

	Travaux gererTravaux(Long refDevis, Travaux travaux, EtatOperation etatOperation) throws GestionDevisException;
	
	List<Travaux> recupererTravauxEnCours() throws GestionDevisException; // permet de voir si l'avoir a ete recu
	
	List<Travaux> recupererTravauxTerminesNonRegles() throws GestionDevisException;
	
	Statistiques dresserBilan() throws GestionDevisException;
 	
	
}
