package donnees;

import java.io.*;
import java.util.*;

import donnees.*;

public class JeuDonnees {
    
	protected HashMap<String, ArrayList<String>> attributs;
	protected ArrayList<ArrayList<String>> donnees;

	/**
	 * Construit un jeu de données vide
	 */
	public JeuDonnees() {
		this.attributs = new HashMap<String, ArrayList<String>>();
		this.donnees = new ArrayList<ArrayList<String>>();
	}

	/**
	 * Construit un jeu de données à partir d'un fichier
	 * @param nom_fichier
	 */
	public JeuDonnees(String nom_fichier) {
		this();
		LectureFichier(this, nom_fichier);
	}

	/**
	 * Ajoute un attribut et ses valeurs possibles
	 * @param attribut
	 * @param valeurs
	 */
	public void ajouterAttribut(String attribut, ArrayList<String> valeurs) {
		this.attributs.put(attribut, valeurs);
	}

	/**
	 * Ajoute un exemple (une donnée, une ligne)
	 * @param exemple
	 */
	public void ajouterExemple(ArrayList<String> exemple) {
		this.donnees.add(exemple);
	}

	/**
	 * Retourne le nombre de classes différentes dans ce jeu de données
	 * @return int
	 */
	public int nombreDeClassesExemples() {
		ArrayList<String> classes = new ArrayList<String>();

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.donnees) {
			// Si le tableau récap ne contient pas encore la classe de cet exemple
			// La classe de cet exemple est la dernière valeur du tableau
			String classe_exemple = exemple.get(exemple.size()-1);
			if (!classes.contains(classe_exemple)) {
				// Ajouter au tableau récap la classe de l'exemple
				classes.add(classe_exemple);
			}
		}

		return classes.size();
	}

}

/*
	donnees =	[
					1 : [ "aaa", "bbb", "OUI" ],
					2 : [ "aaa", "bbb", "NON" ]
				]
*/
