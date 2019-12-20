package hdn.projects.gestiondevis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hdn.projects.gestiondevis.utils.Utilitaire;

@ControllerAdvice
public class GestionDevisControllerAdvice extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GestionDevisControllerAdvice.class);

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> resourceEntityNotFound(GestionDevisException gestionDevisException) {
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(HttpStatus.NOT_FOUND,
				gestionDevisException);

		return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(GestionDevisException.class)
	public ResponseEntity<Object> handleNotFound(GestionDevisException gestionDevisException) {
		Utilitaire.loguer(logger, this.getClass().getSimpleName(), "handleException()", "GestionDevisException");
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,
				"GestionDevisException levée", gestionDevisException);
		return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
	}

}
