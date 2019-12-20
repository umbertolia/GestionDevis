package hdn.projects.gestiondevis.utils;

public enum EtatOperation {

	CREATE("Création"), UPDATE("Mise à jour"), DELETE ("Suppression"), SUSPEND("En StandBy"), IN_PROGRESS("En Cours"), FINISHED("Terminés"), VALID("Opération valide"), INVALID("Opération invalide");
	
	private String nomOperation;
	
	private EtatOperation(String nomOperation) {
		this.nomOperation = nomOperation;
	}

	public String getNomOperation() {
		return nomOperation;
	}

	public void setNomOperation(String nomOperation) {
		this.nomOperation = nomOperation;
	}
}
