package modele;

import java.util.ArrayList;

import donnees.JeuDonnees;

public class Modele {

	protected ArrayList<Regle> regles;

	/**
	 * Construit un modèle vide
	 */
	public Modele() {
		this.regles = new ArrayList<Regle>();
	}

	/**
	 * Ajoute une règle à la liste de règles
	 * @param regle
	 */
	public void ajouterRegle(Regle regle) {
		this.regles.add(regle);
	}

	public String toString() {
		String res = "\n# Affichage du modèle\n\n";

		// Pour chaque règle
		int indice_regle = 0;
		for (Regle r : this.regles) {
			res += "R " + ++indice_regle + "\t" + r + "\n";
		}
		
		return res;
	}

}
