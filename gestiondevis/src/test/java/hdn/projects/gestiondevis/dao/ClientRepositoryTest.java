package hdn.projects.gestiondevis.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hdn.projects.gestiondevis.entities.Adresse;
import hdn.projects.gestiondevis.entities.Client;
import hdn.projects.gestiondevis.entities.Utilisateur;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	private IUtilisateurRepository userRepository;

	@Test
	@Order(1)
	@DisplayName("test sur FindAllUsers")
	public void test_FindAllUsers() {
		List<Utilisateur> users = userRepository.findAll();
		assertAll("users",
				() -> assertNotNull(users),
				() -> assertEquals(users.size(), 7)
		);
	}

	@Test
	@Order(2)
	@DisplayName("test sur test_FindByLogin")
	public void test_FindByLogin() {
		Optional<Utilisateur> optUser = userRepository.findByLoginPassword("admin");
		optUser.ifPresent(user -> assertEquals("admin", user.getLogin()));
	}

	@Test
	@Order(3)
	@DisplayName("test sur SaveUser")
	public void test_SaveUser() {
		Utilisateur utilisateur = new Client("fillo", "jean", "client2", "sdfg@frz");
		Utilisateur userSaved = userRepository.save(utilisateur);
		List<Utilisateur> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		assertAll("saveuser",
				() -> assertNotNull(users),
				() -> assertNotNull(userSaved),
				() -> assertEquals("fillo", userSaved.getNom())
		);			
	}

	@Test
	@Order(4)
	@DisplayName("test sur DeleteUser")
	public void test_DeleteUser() {
		Utilisateur user = userRepository.getOne(7L);
		if (user != null) {
			userRepository.delete(user);
			assertTrue(!userRepository.findById(user.getId()).isPresent());
		}
	}

	@Test
	@Order(5)
	@DisplayName("test sur UpdateUser")
	public void test_UpdateUser() {// Test si le compte utilisateur est
									// désactivé
		Optional<Utilisateur> optUser = userRepository.findById(2L);
		optUser.ifPresent((user) -> {
			Adresse adresse = new Adresse(44, "rue Balzac", 95260, "Mours");
			user.setAdresse(adresse);
			userRepository.save(user);
			Utilisateur userUpdatedFromDB = userRepository.getOne(2L);
			assertAll("UpdateUser",
					() -> assertNotNull(userUpdatedFromDB),
					() -> assertEquals("rue Balzac", userUpdatedFromDB.getAdresse().getRue())
			);	
		});
	}
	
	@Test
	@Order(6)
	@DisplayName("test sur test_FindUsersFromAnotherUser")
	public void test_FindUsersFromAnotherUser() {
		Stream<Utilisateur> users = userRepository.getClientsStream(1L);
		assertAll("FindUsersFromAnother",
				() -> assertNotNull(users),
				() -> assertEquals(6, users.count())
		);	
	}

}