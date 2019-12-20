/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gandalf
 *
 */

@Entity
@Table(name = "GERANT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@XmlRootElement(name = "gerant")
public class Gerant extends Utilisateur {

	private static final long serialVersionUID = 8190736152477141320L;

	@Column(name = "SIREN", unique=true, updatable = true, nullable = false)
	private long numeroSiren;

	@OneToMany(mappedBy = "gerant", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Client> clients = new HashSet<Client>();

	public Gerant() {
		
	}

	public Gerant(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);
	}

	public Gerant(String nom, String prenom) {
		super(nom, prenom);
	}

	public Set<Client> getClients() {
		return clients;
	}

	@XmlElement
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public long getNumeroSiren() {
		return numeroSiren;
	}
	
	@XmlElement
	public void setNumeroSiren(long numeroSiren) {
		this.numeroSiren = numeroSiren;
	}

}
