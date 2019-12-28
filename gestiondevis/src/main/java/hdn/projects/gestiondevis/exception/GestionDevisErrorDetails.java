/**
 * 
 */
package hdn.projects.gestiondevis.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Gandalf
 *
 */
public class GestionDevisErrorDetails {

	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 'à' hh:mm:ss")
	private LocalDateTime timestamp;

	private List<String> messages = new ArrayList<String>();

	private String typeException;

	private GestionDevisErrorDetails() {
		super();
		messages = new ArrayList<String>();
		timestamp = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' hh:mm:ss");
		timestamp.format(formatter);
	}

	public GestionDevisErrorDetails(Throwable throwable, String... messages) {
		this();
		this.typeException = throwable.getClass().getCanonicalName();
		this.messages = messages != null ? Arrays.asList(messages)
				: Arrays.asList(new String[] { "Erreur inattendue / inconnue" });
	}

	GestionDevisErrorDetails(Throwable throwable, HttpStatus status, String... messages) {
		this(throwable, messages);
		this.status = status;
	}

	public GestionDevisErrorDetails(Throwable throwable, Stream<FieldError> messagesStream) {
		this();
		this.typeException = throwable.getClass().getCanonicalName();
		this.messages = messagesStream != null ? messagesStream.map(field -> field.getDefaultMessage()).collect(Collectors.toList())
				: Arrays.asList(new String[] { "Erreur inattendue / inconnue" });
	}

	public GestionDevisErrorDetails(HttpStatus status) {
		this();
		this.status = status;
	}

	public GestionDevisErrorDetails(Throwable throwable, HttpStatus status) {
		this();
		this.status = status;
		this.typeException = throwable.getClass().getCanonicalName();
		this.messages.add("Erreur inattendue / inconnue");
	}

	GestionDevisErrorDetails(Throwable throwable, HttpStatus status, Stream<FieldError> messagesStream) {
		this(throwable, messagesStream);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getTypeException() {
		return typeException;
	}

	public void setTypeException(String typeException) {
		this.typeException = typeException;
	}

}
