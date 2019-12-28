package hdn.projects.gestiondevis.exception;

import org.springframework.http.HttpStatus;

public class GestionDevisException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public GestionDevisException(String message) {
        super(message);
    }
    
    public GestionDevisException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
    public GestionDevisException(Long resourceId, String errorCode, String message) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }
    
    public GestionDevisException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public GestionDevisException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public GestionDevisException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }    
    
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
