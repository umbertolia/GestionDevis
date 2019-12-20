package hdn.projects.gestiondevis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hdn.projects.gestiondevis.entities.Gerant;
import hdn.projects.gestiondevis.entities.Utilisateur;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ClientControllerTestRestTemplateIT {

	private static final Logger logger = LoggerFactory.getLogger(ClientControllerTestRestTemplateIT.class);

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort // permet d'utiliser le port local du serveur, sinon une erreur "Connection refused"
	private int port;
	
	private static final String serverName = "http://localhost:";// url du serveur REST. Cette URL peut être celle d'un serveur distant

	@Value("${server.contextPath}")
	private String urlBase;
	
	@Value("${controller.clientPath}")
	private String clientControllerPath;
	
	
	private String getURLWithPort(String uri) {
		uri = serverName + port + urlBase + clientControllerPath + uri;
		return uri;
	}

	
	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void test_getAllUsers() throws Exception {
			
		logger.debug("test_getAllUsers...");
		
		
		ResponseEntity<String> responseEntity = testRestTemplate
				.withBasicAuth("admin", "admin")
				.getForEntity(getURLWithPort("users"), String.class);
		

		// On vérifie le code de réponse HTTP, en cas de différence entre les deux valeurs, le message "Réponse inattendue" est affiché
		assertEquals(HttpStatus.FOUND.value(), responseEntity.getStatusCodeValue(), "Réponse inattendue");

		/*if (responseEntity.getBody() instanceof Collection<?>) {
			Collection<Utilisateur> userCollections = (Collection<Utilisateur>) responseEntity.getBody();
			logger.info("Utilisateur trouvé : " + userCollections.toString());
			assertNotNull(userCollections);
			assertEquals(7, userCollections.size());// on a bien 3 utilisateurs initialisés par les scripts data.sql au	 démarrage des tests
		}
		else {
			fail("Pb de recup de la liste");
		}*/
		
	}
	
	@Test
	public void test_findUserByReference() throws Exception {
			
		logger.debug("test_findUserByReference...");
		
		ResponseEntity<Gerant> responseEntity = testRestTemplate
				.withBasicAuth("admin", "admin")
				.getForEntity(getURLWithPort("users/1"), Gerant.class);

		// On vérifie le code de réponse HTTP, en cas de différence entre les deux valeurs, le message "Réponse inattendue" est affiché
		assertEquals(HttpStatus.FOUND.value(), responseEntity.getStatusCodeValue(), "Réponse inattendue");

		if (responseEntity.getBody() instanceof Collection<?>) {
			Collection<Utilisateur> userCollections = (Collection<Utilisateur>) responseEntity.getBody();
			logger.info("Utilisateur trouvé : " + userCollections.toString());
			assertNotNull(userCollections);
			assertEquals(7, userCollections.size());// on a bien 3 utilisateurs initialisés par les scripts data.sql au	 démarrage des tests
		}
		else {
			fail("Pb de recup de la liste");
		}
		
	}
}
