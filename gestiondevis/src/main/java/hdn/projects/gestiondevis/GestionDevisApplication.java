package hdn.projects.gestiondevis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gandalf
 *
 *L'annotation @SpringBootApplication est centrale dans une application Spring Boot 
 *et permet de scanner le package courant et ses sous-packages. Elle existe depuis SpringBoot-1.2.0
 * et est équivalente à l'ensemble des annotations @Configuration, @EnableAutoConfiguration et @ComponentScan
 * 
 * 
 */
@SpringBootApplication
// @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class}) -> configuré dans properties en mode dev
public class GestionDevisApplication {
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GestionDevisApplication.class, args);
	}

}
