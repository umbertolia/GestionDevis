package hdn.projects.gestiondevis.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hdn.projects.gestiondevis.entities.Utilisateur;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
	Optional<Utilisateur> findById(Long userId);
	
	@Query(name="query.user.findbyloginpassword")
	Optional<Utilisateur> findByLoginPassword(@Param("login") String login, @Param("password") String password);
	
	@Query(name="query.user.getallusersstream")
	Stream<Utilisateur> getAllUsersStream();
	
	@Query(name="query.user.getclientsbygerant")
	Stream<Utilisateur> getClientsStream(@Param("gerantID") Long idGerant);
}
