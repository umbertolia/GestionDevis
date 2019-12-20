/**
 * 
 */
package hdn.projects.gestiondevis.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import hdn.projects.gestiondevis.utils.TypeOuvrage;

/**
 * @author Gandalf
 *
 */
public class Statistiques implements Serializable {

	private static final long serialVersionUID = 2079332646101222654L;
	
	private LocalDate dateDebutActivite;

	private Map<String, Double> pourcentageInteventions = new HashMap<String, Double>(TypeOuvrage.values().length);
	
	public Statistiques(LocalDate dateDebutActivite) {
		this.dateDebutActivite = dateDebutActivite;
	}

	public Map<String, Double> getPourcentageInteventions() {
		return pourcentageInteventions;
	}

	public void setPourcentageInteventions(Map<String, Double> pourcentageInteventions) {
		this.pourcentageInteventions = pourcentageInteventions;
	}

	public LocalDate getDateDebutActivite() {
		return dateDebutActivite;
	}

	public void setDateDebutActivite(LocalDate dateDebutActivite) {
		this.dateDebutActivite = dateDebutActivite;
	}

}
