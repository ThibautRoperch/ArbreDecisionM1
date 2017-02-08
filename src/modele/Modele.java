/**
 * Classe Modele
 * 
 * Cette classe permet d'instancier un modèle de décision
 * 
 * Un modèle possède un ensemble de règles de décisions
 * 
 * Un modèle est généré par un arbre de décision
 */

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

	/**
	 * Retourne le nombre de règles
	 * @return int
	 */
	public int nombreDeRegles() {
		return this.regles.size();
	}

	public String toString() {
		String res = "";

		// Pour chaque règle
		int indice_regle = 0;
		for (Regle r : this.regles) {
			res += "R" + ++indice_regle + "\t" + r + "\n";
		}
		
		return res;
	}

}
