package hdn.projects.gestiondevis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hdn.projects.gestiondevis.dao.IDevisRepository;
import hdn.projects.gestiondevis.entities.Devis;
import hdn.projects.gestiondevis.entities.Travaux;
import hdn.projects.gestiondevis.utils.EtatOperation;
import hdn.projects.gestiondevis.utils.TypeOuvrage;

@ExtendWith(SpringExtension.class)
public class GestionServiceImplTest {

	
	@Mock // création d'un mockBean pour DevisRepository
	private IDevisRepository devisRepository;
	
	@InjectMocks
	private IGestion gestionService = new GestionImpl();

	@Test
	@Disabled("pas pertinent a tester!")
	public void test1_CreerModifierDevis() throws Exception {
		// pas pertinent a tester
	}

	@Test
	@Disabled("pas pertinent a tester!")
	public void test2_getAllDevis() throws Exception {
		// pas pertinent a tester
	}

	@Test
	@Disabled("pas pertinent a tester!")
	public void test3_recupererDevis() throws Exception {
		// pas pertinent a tester
	}

	@Test
	public void test4_gererTravaux() throws Exception {
		Devis devisMock = new Devis(LocalDate.now(), 150.25, 5.5, 25.75, true, 25);
		Optional<Devis> optDevis = Optional.of(devisMock);
		Mockito.when(devisRepository.findByReference(1L)).thenReturn(optDevis);
		//
		Travaux travaux = new Travaux(LocalDate.now(), EtatOperation.IN_PROGRESS, TypeOuvrage.CHARPENTE);
		Travaux travauxDB = gestionService.gererTravaux(1L, travaux, EtatOperation.CREATE);
		assertEquals(travaux, travauxDB);
	}

	@Test
	public void test5_recupererTravauxEnCours() throws Exception {

		List<Devis> listDevis = new ArrayList<Devis>();
		Long nbrTravauxEnCours = 0L;
		for (int i = 0; i < 30; i++) {
			EtatOperation etat = generateEtatOp();
			if (etat.equals(EtatOperation.IN_PROGRESS)) {
				nbrTravauxEnCours++;
			}

			Devis devis = new Devis(LocalDate.now(), 1000 * new Random().nextDouble(), 5.5,
					15 * new Random().nextDouble(), new Random().nextBoolean(), new Random().nextInt(100));
			Travaux travaux = new Travaux(LocalDate.now(), etat, generateTypeOuvr(), new Random().nextBoolean());
			devis.setTravaux(travaux);
			listDevis.add(devis);
		}
		Mockito.when(devisRepository.getAllDevisStream()).thenReturn(listDevis.stream());
		//
		List<Travaux> travauxDB = gestionService.recupererTravauxEnCours();
		Long nbrTravEnCoursDB = travauxDB.stream().map(Travaux::getEtat)
				.filter(etat -> etat.equals(EtatOperation.IN_PROGRESS)).count();
		 assertNotNull(travauxDB);
		 assertEquals(nbrTravauxEnCours, nbrTravEnCoursDB);
	}

	@Test
	public void test6_recupererTravauxTerminesNonRegles() throws Exception {

		List<Devis> listDevis = new ArrayList<Devis>();
		Long nbrTravauxTerminesEtNonPayes = 0L;
		for (int i = 0; i < 30; i++) {
			EtatOperation etat = generateEtatOp();
			boolean travauxPayes = new Random().nextBoolean();
			if (etat.equals(EtatOperation.FINISHED) && !travauxPayes) {
				nbrTravauxTerminesEtNonPayes++;
			}

			Devis devis = new Devis(LocalDate.now(), 1000 * new Random().nextDouble(), 5.5,
					15 * new Random().nextDouble(), new Random().nextBoolean(), new Random().nextInt(100));
			Travaux travaux = new Travaux(LocalDate.now(), etat, generateTypeOuvr(), travauxPayes);
			devis.setTravaux(travaux);
			listDevis.add(devis);
		}
		Mockito.when(devisRepository.getAllDevisStream()).thenReturn(listDevis.stream());
		//
		List<Travaux> travauxDB = gestionService.recupererTravauxTerminesNonRegles();
		Long nbrTravTerminesNonPayesDB = travauxDB.stream()
				.filter(travaux -> travaux.getEtat().equals(EtatOperation.FINISHED))
				.filter(travaux -> !travaux.isTravauxPayes()).count();

		assertEquals(nbrTravauxTerminesEtNonPayes, nbrTravTerminesNonPayesDB);

	}

	private EtatOperation generateEtatOp() {
		EtatOperation[] tabEtats = { EtatOperation.IN_PROGRESS, EtatOperation.FINISHED };
		Random rand = new Random();
		return tabEtats[rand.nextInt(tabEtats.length)];
	}

	private TypeOuvrage generateTypeOuvr() {
		Random rand = new Random();
		return TypeOuvrage.values()[rand.nextInt(TypeOuvrage.values().length)];
	}

}
