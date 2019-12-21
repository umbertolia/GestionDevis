/**
 * 
 */
package hdn.projects.gestiondevis.service;

import hdn.projects.gestiondevis.exception.GestionDevisException;

/**
 * @author Gandalf
 *
 */
public interface IUtilisateur<Utilisateur> extends IMainAction<Utilisateur, Utilisateur> {
					
	Utilisateur getEntity(String login, String password) throws GestionDevisException;
	
	void contacterEntity(Utilisateur entity, String message) throws GestionDevisException;
	
	
}
