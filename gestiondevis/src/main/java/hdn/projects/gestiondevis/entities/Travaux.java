/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hdn.projects.gestiondevis.utils.EtatOperation;
import hdn.projects.gestiondevis.utils.TypeOuvrage;

/**
 * @author Gandalf
 *
 */

@Entity
@Table(name = "TRAVAUX")
@XmlRootElement(name = "travaux")
public class Travaux implements Serializable{

	private static final long serialVersionUID = 926476675333222667L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;
	
	@Column(name = "DATE_DEBUT", insertable = true, updatable = false, nullable = false)
	@NotEmpty(message = "Date de dédut des travaux non renseignée")
	private LocalDate dateDebut;
	
	@Column(name = "NBR_JOURS_EFFECTUES", insertable = true, updatable = true, nullable = true)
	private int nbrJoursEffetues;
	
	@Column(name = "ETAT", insertable = true, updatable = true, nullable = false)
	@NotEmpty(message = "Etat des travaux non renseigné")
	private EtatOperation etat;
	
	@Column(name = "TRAVAUX_PAYES", insertable = true, updatable = true, nullable = false)
	private boolean travauxPayes;
	
	@OneToOne(mappedBy = "travaux", fetch = FetchType.LAZY)
	@JsonIgnore
	private Devis devis;
	
	@Column(name = "TYPE_OUVRAGE", insertable = true, updatable = true, nullable = false)
	private TypeOuvrage typeOuvrage;

	
	public Travaux() {
	}

	public Travaux(LocalDate dateDebut, EtatOperation etat, TypeOuvrage typeOuvrage) {
		super();
		this.dateDebut = dateDebut;
		this.etat = etat;
		this.typeOuvrage = typeOuvrage;
	}
	
	public Travaux(LocalDate dateDebut, EtatOperation etat, TypeOuvrage typeOuvrage, boolean travauxPayes) {
		super();
		this.dateDebut = dateDebut;
		this.etat = etat;
		this.typeOuvrage = typeOuvrage;
		this.travauxPayes = travauxPayes;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	@XmlElement
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public int getNbrJoursEffetues() {
		return nbrJoursEffetues;
	}

	@XmlElement
	public void setNbrJoursEffetues(int nbrJoursEffetues) {
		this.nbrJoursEffetues = nbrJoursEffetues;
	}

	public EtatOperation getEtat() {
		return etat;
	}

	@XmlElement
	public void setEtat(EtatOperation etat) {
		this.etat = etat;
	}

	public boolean isTravauxPayes() {
		return travauxPayes;
	}

	@XmlElement
	public void setTravauxPayes(boolean travauxPayes) {
		this.travauxPayes = travauxPayes;
	}
	
	public TypeOuvrage getTypeOuvrage() {
		return typeOuvrage;
	}

	public void setTypeOuvrage(TypeOuvrage typeOuvrage) {
		this.typeOuvrage = typeOuvrage;
	}

	public Devis getDevis() {
		return devis;
	}

	@XmlElement
	public void setDevis(Devis devis) {
		this.devis = devis;
	}

	@Override
	public String toString() {
		return "Travaux [etat=" + etat + ", travauxPayes=" + travauxPayes + ", typeOuvrage=" + typeOuvrage + "]";
	}
	
}
