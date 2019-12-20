/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gandalf
 *
 */

@Entity
@Table(name = "CLIENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@XmlRootElement(name = "client")
public class Client extends Utilisateur {

	private static final long serialVersionUID = 4263650123248454996L;

	@ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
	@JoinColumn(name = "GERANT_ID")
	@JsonIgnore
	private Gerant gerant;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Devis> devis = new HashSet<Devis>();

	public Client() {
	}

	public Client(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);
	}

	public Client(String nom, String prenom) {
		super(nom, prenom);
	}

	public Gerant getGerant() {
		return gerant;
	}

	@XmlElement
	public void setGerant(Gerant gerant) {
		this.gerant = gerant;
	}

	public Set<Devis> getDevis() {
		return devis;
	}

	@XmlElement
	public void setDevis(Set<Devis> devis) {
		this.devis = devis;
	}

}
