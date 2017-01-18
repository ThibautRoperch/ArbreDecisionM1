package modele;

import java.util.HashMap;

public class Regle {

	protected HashMap<String, String> conditions;
	protected HashMap<String, String> conclusion;

	/**
	 * Construit une règle vide
	 */
	public Regle() {
		this.conditions = new HashMap<String, String>();
		this.conclusion = new HashMap<String, String>();
	}

	/**
	 * Construit une règle à partir de conditions et d'une conclusion
	 * @param conditions
	 * @param conclusion
	 */
	public Regle(HashMap<String, String> conditions, HashMap<String, String> conclusion) {
		this.conditions = conditions;
		this.conclusion = conclusion;
	}

	/**
	 * Ajoute aux conditions un attribut et sa valeur
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterCondition(String attribut, String valeur) {
		this.conditions.put(attribut, valeur);
	}

	/**
	 * Ajoute la conclusion
	 * Supprime l'ancienne s'il y en avait une (ce qui n'est pas censé arriver)
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterConclusion(String attribut, String valeur) {
		this.conclusion.clear();
		this.conclusion.put(attribut, valeur);
	}

}
