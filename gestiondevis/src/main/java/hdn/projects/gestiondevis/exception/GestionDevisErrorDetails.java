/**
 * 
 */
package hdn.projects.gestiondevis.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Gandalf
 *
 */
public class GestionDevisErrorDetails {
	
	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	
	private LocalDateTime timestamp;
	
    private String message;
    
    private String details;
    
    private GestionDevisErrorDetails() {
		super();
	}

	public GestionDevisErrorDetails(LocalDateTime timestamp, String message, String details) {
		this();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
  
    public GestionDevisErrorDetails(HttpStatus status)
	{
		this();
		this.status = status;
	}

    public GestionDevisErrorDetails(HttpStatus status, Throwable ex)
	{
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.details = ex.getLocalizedMessage();
	}

    GestionDevisErrorDetails(HttpStatus status, String message, Throwable ex)
	{
		this();
		this.status = status;
		this.message = message;
		this.details = ex.getLocalizedMessage();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
    
    
    
}
