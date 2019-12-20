package hdn.projects.gestiondevis.utils;

public enum TypeOuvrage {

	DIVERS("Divers Travaux"), ELECTRICITE("Electricité"), MACONNERIE("Maçonnerie"), CHARPENTE("Charpente"), PLOMBERIE(
			"Plomberie");

	private String nomOuvrage;

	private TypeOuvrage(String nomOuvrage) {
		this.nomOuvrage = nomOuvrage;
	}

	public String getNomOuvrage() {
		return nomOuvrage;
	}

	public void setNomOuvrage(String nomOuvrage) {
		this.nomOuvrage = nomOuvrage;
	}

}
