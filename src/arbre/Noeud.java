package arbre;

import java.io.*;
import java.util.*;

import donnees.JeuDonnees;

public class Noeud extends Thread {

	protected JeuDonnees jeu_de_donnees;
	protected ArrayList<Noeud> enfants;

	/**
	 * Construit un noeud à partir d'un jeu de données
	 * @param donnees
	 */
	public Noeud(JeuDonnees donnees) {
		this.jeu_de_donnees = donnees;
		this.enfants = new ArrayList<Noeud>();
	}

	public void run() {
		// Ne rien faire si le noeud n'est pas pure ou s'il n'y existe pas d'attributs candidats
		// Si le noeud est pure, alors c'est une feuille
		if (!this.estPure() && this.jeu_de_donnees.attributsCandidats().size() > 0) {
			// Choisir un attribut en fonction du gain d'information de chacune de ses valeurs
			String attribut_choisi = meilleurAttribut();
			// Récupérer auprès du jeu de données les valeurs possibles pour l'attribut choisi
			ArrayList<String> valeurs_possibles = jeu_de_donnees.valeursPossibles(attribut_choisi);
			// Créer autant de noeuds fils qu'il y a de valeurs pour l'attribut choisi
			for (String valeur : valeurs_possibles) {
				// Créer un nouveau jeu de données pour le fils à partir
				// Partir du jeu de données du noeud actuel et enlever de la liste l'attribut utilisé par le noeud

			}
		}
	}

	/**
	 * Retourne true si la feuille est pure
	 * Une feuille est pure si son jeu de données ne contient que des exemples de la même classe
	 * @return boolean
	 */
	public boolean estPure() {
		return this.jeu_de_donnees.nombreDeClassesExemples() == 1;
	}

	private String meilleurAttribut() {
		ArrayList<String> attributs_candidats = this.jeu_de_donnees.attributsCandidats();
		return new String();
	}

	//calculer le meilleur attribut à mettre en noeud racine, puis remplir les fils. Puis recommencer sur les fils.
	//Calcule le gain pour choisir le meilleur attribut.
	//Prendre le jeu d'apprentissage en paramètre maybe ? 
	private int gain(int positif, int negatif) {
		//return positif*(log(positif/positif+negatif)-log(P/P+N));

		//Appeler la fonction pour chaque attribut, et mettre à jour le
	}

}
