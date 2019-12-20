package hdn.projects.gestiondevis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import hdn.projects.gestiondevis.entities.Client;
import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.service.IUtilisateur;
import hdn.projects.gestiondevis.utils.EtatOperation;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	IUtilisateur<Utilisateur> userService;

	@Value("${controller.clientPath}")
	private String clientControllerPath;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private String getURL(String uri) {
		uri = clientControllerPath + uri;
		return uri;
	}

	private static List<Utilisateur> liste = new ArrayList<Utilisateur>();

	@BeforeAll
	public static void init() {
		liste.add(new Client("john", "doe", "johnDoe", "123456"));
		liste.add(new Client("client2", "truc", "client2Login", "748547"));
	}

	@Test
	@Order(1)
	@WithMockUser(username = "admin", password = "admin")
	public void test_getUsers() throws Exception {

		// prepare data and mock's behaviour
		when(userService.getEntities()).thenReturn(liste);

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getURL("users")).accept(MediaType.APPLICATION_JSON)).andReturn();

		// verify
		int status = result.getResponse().getStatus();

		assertTrue(status == HttpStatus.OK.value() || status == HttpStatus.FOUND.value());

		// verify that service method was called once
		verify(userService).getEntities();

		List<Utilisateur> listeResult = Arrays
				.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Utilisateur[].class));

		assertNotNull(listeResult, "Aucun client");
		assertEquals(liste.size(), listeResult.size(), "Incorrect Employee List");
	}

	@Test
	@Order(2)
	@WithMockUser(username = "admin", password = "admin")
	public void test_saveUser() throws Exception {

		when(userService.saveOrUpdateEntity(any(Utilisateur.class), any(EtatOperation.class))).thenReturn(liste.get(0));

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(getURL("users"))
				.with(csrf()) // sinon 403
				.content(objectMapper.writeValueAsString(liste.get(0)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

		Utilisateur resultclient = objectMapper.readValue(result.getResponse().getContentAsString(), Utilisateur.class);
		assertNotNull(resultclient);
		assertEquals("johnDoe", resultclient.getLogin());

		// verify that service method was called once
		verify(userService).saveOrUpdateEntity(any(Utilisateur.class), any(EtatOperation.class));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "admin", password = "admin")
	public void test_findUserByReference() throws Exception {

		when(userService.getEntityWithOptional(any(Long.class))).thenReturn(liste.get(0));

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(getURL("users/1"))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect((status().isFound()))
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.login").value("johnDoe")).andReturn();

		Utilisateur resultclient = objectMapper.readValue(result.getResponse().getContentAsString(), Utilisateur.class);
		assertNotNull(resultclient);

		// verify that service method was called once
		verify(userService).getEntityWithOptional(any(Long.class));

	}

	@Test
	@Order(4)
	@WithMockUser(username = "admin", password = "admin")
	public void test_updateClient() throws Exception {
		
		
		when(userService.getEntityWithOptional(any(Long.class))).thenReturn(liste.get(0));

		when(userService.saveOrUpdateEntity(any(Utilisateur.class), any(EtatOperation.class))).thenReturn(liste.get(1));

		mockMvc.perform(MockMvcRequestBuilders.put(getURL("users/1"))
				.with(csrf()) // sinon 403
			    .content(objectMapper.writeValueAsString(liste.get(0)))
			    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.login").value(liste.get(1).getLogin()))
			    .andExpect(jsonPath("$.password").value(liste.get(1).getPassword()))
			    .andReturn();
		
		// verify that service method was called once
		verify(userService).saveOrUpdateEntity(any(Utilisateur.class), any(EtatOperation.class));
	}

	@Test
	@Order(5)
	@WithMockUser(username = "admin", password = "admin")
	public void test_deleteUser() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.delete(getURL("users/1"))
			.with(csrf())) // sinon 403
			.andExpect(status().isGone());	
		
		// verify that service method was called once
		verify(userService).deleteEntity(any(Long.class));
	}
	
	@Test
	@Order(6)
	@WithMockUser(username = "admin", password = "admin")
	public void test_callGestionDevisControllerAdvice() throws Exception {
		
		Long refClient = 1L;
		
		GestionDevisException devisException = new GestionDevisException("Get User Error", "Erreur de recuperation de l'utilisateur avec id : " + refClient, HttpStatus.NOT_FOUND);
		when(userService.getEntityWithOptional(any(Long.class))).thenThrow(devisException);
		
		// execute
		mockMvc.perform(MockMvcRequestBuilders.get(getURL("users/1"))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect((status().isInternalServerError()))
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR")).andReturn();
		
		// verify that service method was called once
		verify(userService).getEntityWithOptional(any(Long.class));
	}
	
	
}
