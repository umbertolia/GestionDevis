package hdn.projects.gestiondevis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hdn.projects.gestiondevis.utils.Utilitaire;

@ControllerAdvice
public class GestionDevisControllerAdvice extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GestionDevisControllerAdvice.class);

	/**
	 * @param gestionDevisException
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> resourceEntityNotFound(GestionDevisException gestionDevisException) {
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(gestionDevisException,
				HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
	}
	
	
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(ex, status,
				ex.getLocalizedMessage());

		return new ResponseEntity<>(errorDetails, headers, status);
	}
	

	/**
	 * @param gestionDevisException
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(GestionDevisException.class)
	public ResponseEntity<Object> handleNotFound(GestionDevisException gestionDevisException) {
		Utilitaire.loguer(logger, this.getClass().getSimpleName(), "handleException()", "GestionDevisException");
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(gestionDevisException,
				gestionDevisException.getStatus(), gestionDevisException.getMessage());
		return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
	}

	/**
	 * permet de lever l'exception du a un bean unvalide
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// Get all errors
		GestionDevisErrorDetails errorDetails = new GestionDevisErrorDetails(ex, status,
				ex.getBindingResult().getFieldErrors().stream());

		return new ResponseEntity<>(errorDetails, headers, status);
	}

}
