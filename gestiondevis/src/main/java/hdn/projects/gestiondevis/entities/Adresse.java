package hdn.projects.gestiondevis.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gandalf
 *
 */

@Entity
@Table(name = "ADRESSE")
@XmlRootElement(name = "adresse")
public class Adresse implements Serializable {

	private static final long serialVersionUID = -22509602031779569L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;
	
	@Column(name = "NUMERO", insertable=true, updatable=true, nullable=false)
	@Min(value = 1, message = "Le numéro de l'adresse ne doit pas etre 0")
	private int numero;
	
	@Column(name = "RUE", insertable=true, updatable=true, nullable=false)
	@NotEmpty(message = "Rue de l'adresse non renseigné")
	private String rue;
	
	@Column(name = "CP", insertable=true, updatable=true, nullable=false)
	@Digits(integer = 5, fraction = 0, message = "Le Code Postal doit contenir 5 digits")
	private int codePostal;
	
	@Column(name = "VILLE", insertable=true, updatable=true, nullable=false)
	@NotEmpty(message = "Ville de l'adresse non renseigné")
	private String ville;
	
	@Column(name = "DETAILS", insertable=true, updatable=true, nullable=true)
	private String details;

	@OneToOne(mappedBy = "adresse", fetch = FetchType.LAZY)
    @JsonIgnore
	private Utilisateur utilisateur;
	
	

	public Adresse() {
	}

	public Adresse(int numero, String rue, int codePostal, String ville) {
		super();
		this.numero = numero;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	@XmlElement
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getRue() {
		return rue;
	}

	@XmlElement
	public void setRue(String rue) {
		this.rue = rue;
	}

	public int getCodePostal() {
		return codePostal;
	}

	@XmlElement
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	@XmlElement
	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getDetails() {
		return details;
	}

	@XmlElement
	public void setDetails(String details) {
		this.details = details;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	@XmlElement
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
