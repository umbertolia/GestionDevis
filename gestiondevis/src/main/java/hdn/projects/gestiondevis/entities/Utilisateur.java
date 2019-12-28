/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Gandalf
 *
 *L'annotation @Entity permet d'indiquer que cette classe sera une table de la base de données 
 *et l'annotation @Table(name = "UTILISATEUR") permet de donner le nom UTILISATEUR à la table. 
 *Grâce à ces annotations, on n'a plus besoin du fichier de configuration persistence.xml.
 *
 *L'annotation @XmlRootElement(name = "user") permettra de construire un objet XML lors des tests 
 *de communications entre le client et le serveur. Voici un exemple d'objet XML qu'on pourra utiliser
 * lors des tests pour créer un utilisateur :
 */

@Entity
@Table(name = "UTILISATEUR")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement(name = "user")
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "type")
		@JsonSubTypes({
		  @Type(value = Gerant.class, name = "gerant"),
		  @Type(value = Client.class, name = "client")
		})
public abstract class Utilisateur implements Serializable{

	private static final long serialVersionUID = -3768170504974209774L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NOM", insertable=true, updatable=true, nullable=false)
    @NotEmpty(message = "Nom de l'utilisateur non renseigné")
	private String nom;
    
    @Column(name = "PRENOM", insertable=true, updatable=true, nullable=false)
    @NotEmpty(message = "Prénom de l'utilisateur non renseigné")
	private String prenom;
	
    @Column(name = "LOGIN", unique=true, insertable=true, updatable=true, nullable=false)
    @NotEmpty(message = "Login de l'utilisateur non renseigné")
    private String login;
    
    @Column(name = "USER_PASSWORD", insertable=true, updatable=true, nullable=false)
    @NotEmpty(message = "Mot de passe de l'utilisateur non renseigné")
    private String password;
    
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @Valid
    private Set<Role> roles= new HashSet<>();	

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @Valid
    private Adresse adresse;
    
    public Utilisateur() {
    }
    
    public Utilisateur(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public Utilisateur(String nom, String prenom, String login, String password) {
		this(nom, prenom);
        this.login = login;
        this.password = password;
    }
	

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    
    
    public String getNom() {
		return nom;
	}

    @XmlElement
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	@XmlElement
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
        return login;
    }
    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }

    @XmlElement
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Adresse getAdresse() {
		return adresse;
	}

    @XmlElement
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" +prenom + " roles="
                + roles + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	 if (obj == this) return true;
         if (!(obj instanceof Utilisateur)) {
             return false;
         }
         Utilisateur user = (Utilisateur) obj;
         return user.login.equalsIgnoreCase(this.login) ;        
    }	

}
