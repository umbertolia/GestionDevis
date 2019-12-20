package hdn.projects.gestiondevis.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hdn.projects.gestiondevis.entities.Devis;

public interface IDevisRepository extends JpaRepository<Devis, Long> {
	
	Optional<Devis> findByReference(Long idDevis);
	
	@Query(name="query.devis.getallstream")
	Stream<Devis> getAllDevisStream();
	
	@Query(name="query.devis.fromclient")
	Stream<Devis> getDevisStream(@Param("clientID") Long idClient);
}
