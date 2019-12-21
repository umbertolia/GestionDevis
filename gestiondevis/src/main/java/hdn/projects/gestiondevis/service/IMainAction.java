package hdn.projects.gestiondevis.service;

import java.util.List;

import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;

public interface IMainAction<T, V> {
	

	List<T> getEntities() throws GestionDevisException;
	
	List<T> getEntitiesFrom(V v);
	
	T getEntity(Long refEntity) throws GestionDevisException;
	
	T getEntityWithOptional(Long refEntity) throws GestionDevisException;
	
	T saveOrUpdateEntity(T entity, EtatOperation etatOperation) throws GestionDevisException;
	
	void deleteEntity(Long refEntity) throws GestionDevisException;
	
}
