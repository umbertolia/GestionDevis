/**
 * 
 */
package hdn.projects.gestiondevis.service;

import java.util.List;

import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;

/**
 * @author Gandalf
 *
 */
public interface IUtilisateur<T> {
	
	T saveOrUpdateEntity(T entity, EtatOperation etatOperation) throws GestionDevisException;
	
	void deleteEntity(Long refEntity) throws GestionDevisException;
		
	List<T> getEntities() throws GestionDevisException;
	
	T getEntity(Long refEntity) throws GestionDevisException;
	
	T getEntityWithOptional(Long refEntity) throws GestionDevisException;
	
	T getEntity(String login, String password) throws GestionDevisException;
	
	void contacterEntity(T entity, String message) throws GestionDevisException;
}
