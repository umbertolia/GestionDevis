package hdn.projects.gestiondevis.util;

import org.assertj.core.api.AbstractAssert;

import hdn.projects.gestiondevis.entities.Utilisateur;

/**
 * @author Gandalf : classe pour des assertions personnalisées
 *
 */
class UtilisateurAssert extends AbstractAssert<UtilisateurAssert, Utilisateur> {

	  UtilisateurAssert(Utilisateur utilisateur) {
	    super(utilisateur, UtilisateurAssert.class);
	  }

	  static UtilisateurAssert assertThat(Utilisateur actual) {
	    return new UtilisateurAssert(actual);
	  }

	  UtilisateurAssert hasRoles() {
	    isNotNull();
	    if (actual.getRoles() == null) {
	      failWithMessage(
	        "L'utilisateur doit posseder des roles"
	      );
	    }
	    return this;
	  }
	  
	  UtilisateurAssert NomPrenomIdIdentiques(Utilisateur utilisateur) {
		    isEqualTo(utilisateur);
		    if (actual.getRoles() == null) {
		      failWithMessage(
		        "L'utilisateur est different de " + utilisateur
		      );
		    }
		    return this;
		  }
	}