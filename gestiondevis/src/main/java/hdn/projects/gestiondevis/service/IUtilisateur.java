/**
 * 
 */
package hdn.projects.gestiondevis.service;

import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;

/**
 * @author Gandalf
 *
 */
public interface IUtilisateur extends IMainAction<Utilisateur, Long> {
					
	Utilisateur getEntity(String login) throws GestionDevisException;
	
	boolean contacterEntity(Utilisateur entity, String message) throws GestionDevisException;
	
	
}
