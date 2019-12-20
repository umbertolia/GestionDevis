/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gandalf
 *
 */

@Entity
@Table(name = "DEVIS")
@XmlRootElement(name = "devis")
public class Devis implements Serializable {

	private static final long serialVersionUID = -3006660845201662369L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REF", updatable = false, nullable = false)
	private Long reference;

	@Column(name = "DATE_DEVIS", insertable = true, updatable = false, nullable = true)
	private LocalDate dateDevis;
	
	@Column(name = "MONTANT_HC", insertable = true, updatable = false, nullable = false)
	private Double montantHC; 

	@Column(name = "TAXE", insertable = true, updatable = false, nullable = false)
	private Double taxeAppliquee;
	
	@Column(name = "DEVIS_ACCEPTE", insertable = true, updatable = false, nullable = false)
	private boolean devisAccepte;
	
	@Column(name = "MONTANT_APPORT", insertable = true, updatable = false, nullable = true)
	private Double montantApport;
	
	@Column(name = "APPORT_RECU", insertable = true, updatable = false, nullable = true)
	private boolean apportRecu;
	
	@Column(name = "DUREE_ESTIMEE", insertable = true, updatable = true, nullable = false)
	private int dureeEstimeeJours;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "travaux_id", referencedColumnName = "id")
	private Travaux travaux;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
	@JsonIgnore
	private Client client;

	public Devis() {
		
	}

	public Devis(LocalDate dateDevis, Double montantHC, int dureeEstimeeJours, Client client) {
		this.dateDevis = dateDevis;
		this.montantHC = montantHC;
		this.dureeEstimeeJours = dureeEstimeeJours;
		this.client = client;
	}
	
	public Devis(LocalDate dateDevis, Double montantHC, Double taxeAppliquee, Double montantApport, boolean apportRecu,
			int dureeEstimeeJours) {
		this.dateDevis = dateDevis;
		this.montantHC = montantHC;
		this.taxeAppliquee = taxeAppliquee;
		this.montantApport = montantApport;
		this.apportRecu = apportRecu;
		this.dureeEstimeeJours = dureeEstimeeJours;
	}

	public Devis(Long reference, LocalDate dateDevis) {
		this.reference = reference;
		this.dateDevis = dateDevis;
	}

	public Long getReference() {
		return reference;
	}

	@XmlElement
	public void setReference(Long reference) {
		this.reference = reference;
	}

	public LocalDate getDateDevis() {
		return dateDevis;
	}

	@XmlElement
	public void setDateDevis(LocalDate dateDevis) {
		this.dateDevis = dateDevis;
	}

	public Double getMontantHC() {
		return montantHC;
	}
	
	@XmlElement
	public void setMontantHC(Double montantHC) {
		this.montantHC = montantHC;
	}

	public Double getTaxeAppliquee() {
		return taxeAppliquee;
	}

	@XmlElement
	public void setTaxeAppliquee(Double taxeAppliquee) {
		this.taxeAppliquee = taxeAppliquee;
	}
	
	public Double getMontantApport() {
		return montantApport;
	}

	@XmlElement
	public void setMontantApport(Double montantApport) {
		this.montantApport = montantApport;
	}

	public boolean isApportRecu() {
		return apportRecu;
	}

	@XmlElement
	public void setApportRecu(boolean apportRecu) {
		this.apportRecu = apportRecu;
	}

	public int getDureeEstimeeJours() {
		return dureeEstimeeJours;
	}

	@XmlElement
	public void setDureeEstimeeJours(int dureeEstimeeJours) {
		this.dureeEstimeeJours = dureeEstimeeJours;
	}

	@Override
	public String toString() {
		return "Devis [reference=" + reference + ", montantHC=" + montantHC + ", taxeAppliquee=" + taxeAppliquee
				+ ", montantApport=" + montantApport + ", dureeEstimeeJours=" + dureeEstimeeJours + "]";
	}

	public Travaux getTravaux() {
		return travaux;
	}

	@XmlElement
	public void setTravaux(Travaux travaux) {
		this.travaux = travaux;
	}

	public Client getClient() {
		return client;
	}

	@XmlElement
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
}

