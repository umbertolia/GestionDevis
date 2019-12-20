package hdn.projects.gestiondevis.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hdn.projects.gestiondevis.entities.Client;
import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.utils.EtatOperation;
import hdn.projects.gestiondevis.utils.TypeOuvrage;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@DataJpaTest
public class DevisRepositoryTest {

	@Autowired
	private IDevisRepository devisRepository;
	
	@Autowired
	private IUtilisateurRepository utilisateurRepository;

	@Test
	@DisplayName("test sur FindAllDevis")
	public void test1_FindAllDevis() {
		Stream<Devis> listDevis = devisRepository.getAllDevisStream();
		assertAll("listDevis",
				() -> assertNotNull(listDevis),
				() -> assertEquals(listDevis.count(), 17)
		);
	}

	@Test
	@DisplayName("test sur FindByDevisId")
	public void test2_FindByDevisId() {
		Optional<Devis> optDevis = devisRepository.findByReference(1L);
		optDevis.ifPresent(devis -> assertTrue(devis.getMontantHC() == 1200));
	}
	
	@Test
	@DisplayName("test sur FindByClientId")
	public void test3_FindByClientId() {
		Stream<Devis> listDevisClient = devisRepository.getDevisStream(2L);
		assertAll("listDevisClient",
				() -> assertNotNull(listDevisClient),
				() -> assertEquals(listDevisClient.count(), 3)
		);
	}

	@Test	
	@DisplayName("test sur SaveDevis")
	public void test4_SaveDevis() {
		Optional <Utilisateur> optUser = utilisateurRepository.findById(3L);
		optUser.ifPresent( user -> {
			if (user != null && Client.class.isInstance(user)) {
				LocalDate currentTime = LocalDate.now();
				Devis devisToSave = new Devis(currentTime, 854d, 5.5, 315.0, true, 11);
				devisToSave.setClient((Client)user);
				Devis devisSaved = devisRepository.save(devisToSave);
				assertNotNull(devisSaved.getReference());
				Devis devisFromDB = devisRepository.getOne(devisSaved.getReference());
				assertEquals(11, devisFromDB.getDureeEstimeeJours());
			}
			
		});
	}

	@Test
	@DisplayName("test sur UpdateDevis")
	public void test5_UpdateDevis() {
		Optional<Devis> optDevis = devisRepository.findByReference(2L);
		optDevis.ifPresent((devis) -> {
			Utilisateur utilisateur = utilisateurRepository.getOne(4L);
			devis.setDureeEstimeeJours(18);
			if (utilisateur != null && Client.class.isInstance(utilisateur)) {
				devis.setClient((Client)utilisateur);
			}
			devisRepository.save(devis);	
			Devis devisUpdatedFromDB = devisRepository.getOne(2L);
			assertAll("UpdateDevis",
					() -> assertNotNull(devisUpdatedFromDB),
					() -> assertEquals(devisUpdatedFromDB.getDureeEstimeeJours(), 18)
			);
		});
	}
	
	@Test
	@DisplayName("test sur SaveDevisWithTravaux")
	public void test6_SaveDevisWithTravaux() {
		Optional <Utilisateur> optUser = utilisateurRepository.findById(3L);
		optUser.ifPresent( user -> {
			if (user != null && Client.class.isInstance(user)) {
				LocalDate currentDate = LocalDate.now();
				Travaux travaux = new Travaux(currentDate.plusDays(2), EtatOperation.IN_PROGRESS, TypeOuvrage.CHARPENTE);
				Devis devisToSave = new Devis(currentDate, 854d, 5.5, 315.0, true, 11);
				devisToSave.setTravaux(travaux);
				devisToSave.setClient((Client)user);
				Devis devisSaved = devisRepository.save(devisToSave);
				assertNotNull(devisSaved.getReference());
				Devis devisFromDB = devisRepository.getOne(devisSaved.getReference());
				assertEquals(EtatOperation.IN_PROGRESS, devisFromDB.getTravaux().getEtat());
			}
		});
	}

}