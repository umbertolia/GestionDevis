package hdn.projects.gestiondevis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Gandalf
 *
 *         L'annotation @SpringBootApplication est centrale dans une application
 *         Spring Boot et permet de scanner le package courant et ses
 *         sous-packages. Elle existe depuis SpringBoot-1.2.0 et est équivalente
 *         à l'ensemble des annotations @Configuration, @EnableAutoConfiguration
 *         et @ComponentScan
 * 
 * 
 */
@SpringBootApplication
@EnableSwagger2
// @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class}) -> configuré dans properties en mode dev
public class GestionDevisApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GestionDevisApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("hdn.projects.gestiondevis")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("GestionDevis Spring Boot REST API Documentation")
				.description("REST APIs pour gérer les devis des clients").contact(new Contact("Umberto De Amorin",
						"https://hdn.developpez.com/", "noreply.gestiondevis.test@gmail.com"))
				.version("1.0").build();
	}
}
