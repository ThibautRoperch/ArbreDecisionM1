package modele;

import java.util.ArrayList;

import donnees.Attribut;

public class Regle {

	protected ArrayList<Attribut> conditions;
	protected Attribut conclusion;

	/**
	 * Construit une règle vide
	 */
	public Regle() {
		this.conditions = new ArrayList<Attribut>();
		this.conclusion = null;
	}

	/**
	 * Retourne vrai si la règle a une conclusion, c'est-à-dire une valeur pour l'attribut de classe
	 * La règle a une conclusion si la valeur de l'attribut de classe de la conclusion n'est pas vide
	 * Il y a une valeur de classe vide lorsqu'il n'y a pas de classe majoritaire dans le jeu de données, ce qui arrive lorsque le jeu est vide d'exemples
	 * @return boolean
	 */
	public boolean conclut() {
		return !this.conclusion.valeurs().get(0).equals(""); // il n'y a qu'une valeur, en théorie, get(0) est la première et la dernière
	}

	/**
	 * Ajoute aux conditions un attribut et sa valeur
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterCondition(Attribut attribut) {
		this.conditions.add(attribut);
	}

	/**
	 * Ajoute la conclusion
	 * Supprime l'ancienne s'il y en avait une (ce qui n'est pas censé arriver)
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterConclusion(Attribut attribut) {
		this.conclusion = attribut;
	}

	public String toString() {
		String res = "SI ";
		int nb_conditions = 0;

		// Pour chaque condition
		for (Attribut condition : this.conditions) {
			// Ajoute l'attribut à la chaine
			if (nb_conditions > 0) res += " ET ";
			res += condition.nom() + " = " + condition.valeurs().get(0); // il n'y a qu'une valeur, en théorie, get(0) est la première et la dernière;
			++nb_conditions;
		}

		// Pour la conclusion
		res += " ALORS " + this.conclusion.nom() + " = " + this.conclusion.valeurs().get(0); // il n'y a qu'une valeur, en théorie, get(0) est la première et la dernière;
		
		return res;
	}

}
