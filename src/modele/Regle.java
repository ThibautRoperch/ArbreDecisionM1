package modele;

import java.util.HashMap;
import java.util.Map;

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

	public boolean conclut() {
		return !this.conclusion.values().toArray()[0].equals("");
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

	public String toString() {
		String res = "SI ";
		int nb_conditions = 0;

		// Pour chaque condition
		for (Map.Entry<String, String> condition : this.conditions.entrySet()) {
			// Ajoute l'attribut à la chaine " = " sa valeur
			if (nb_conditions > 0) res += " ET ";
			res += condition.getKey() + " = " + condition.getValue();
			++nb_conditions;
		}

		// Pour la conclusion
		res += " ALORS " + this.conclusion.keySet().toArray()[0] + " = " + this.conclusion.values().toArray()[0];
		
		return res;
	}

}
