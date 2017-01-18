package donnees;

import java.io.*;
import java.util.*;

import modele.Modele;

public class JeuDonnees {
    
	protected HashMap<String, ArrayList<String>> attributs;
	protected ArrayList<ArrayList<String>> exemples;
	protected Modele modele;

	/**
	 * Construit un jeu de données vide
	 */
	public JeuDonnees() {
		this.attributs = new HashMap<String, ArrayList<String>>();
		this.exemples = new ArrayList<ArrayList<String>>();
		this.modele = new Modele();
	}

	/**
	 * Construit un jeu de données à partir d'un fichier
	 * @param nom_fichier
	 */
	public JeuDonnees(String nom_fichier) {
		this();
		// LectureFichier(this, nom_fichier);
	}

	/**
	 * Construit un jeu de données à partir d'attributs et d'exemples
	 * @param attributs
	 * @param exemples
	 */
	public JeuDonnees(HashMap<String, ArrayList<String>> attributs, ArrayList<ArrayList<String>> exemples) {
		this();
		this.attributs = attributs;
		this.exemples = exemples;
	}

	/**
	 * Retourne une copie des attributs
	 * @return HashMap<String, ArrayList<String>>
	 */
	public HashMap<String, ArrayList<String>> attributs() {
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		res.putAll(this.attributs);
		return res;
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
		this.exemples.add(exemple);
	}

	/**
	 * Retourne les attributs candidats à une évaluation
	 * La liste des attributs candidats est la liste des attributs - l'attribut classe (qui est le dernier attribut de la liste)
	 * @return ArrayList<String>
	 */
	public ArrayList<String> attributsCandidats() {
		ArrayList<String> res = new ArrayList<String>();
		String[] attributs = (String[]) this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)

		// Pour chaque attribut
		for (int i = 0; i < attributs.length - 1; ++i) {
			res.add(attributs[i]);
		}

		return res;
	}

	/**
	 * Retourne les valeurs possibles pour un candidat donné en paramètre
	 * @param attribut
	 * @return ArrayList<String>
	 */
	public ArrayList<String> valeursPossibles(String attribut) {
		return this.attributs.get(attribut);
	}

	/**
	 * Retourne le nombre de classes différentes dans les exemples
	 * @return int
	 */
	public int nombreDeClassesExemples() {
		ArrayList<String> classes = new ArrayList<String>();

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
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

	/**
	 * Retourne les exemples du jeu de données où attribut = valeur donnés en paramètre
	 * @param attribut
	 * @param valeur
	 */
	public ArrayList<ArrayList<String>> selectionnerExemplesOu(String attribut, String valeur) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		String[] attributs = (String[]) this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		int indice_attribut = Arrays.asList(attributs).indexOf("Lily Monte Negro");

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			// Si la valeur de l'attribut de l'exemple = la valeur du paramètre
			if (exemple.get(indice_attribut).equals(valeur)) {
				// Enregistrer l'exemple
				res.add(exemple);
			}
		}

		return res;
	}

	/**
	 * Ajoute au modèle l'attribut et sa valeur
	 * Enlève de la liste des attributs l'attribut donné en paramètre, ainsi que des exemples
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterAuModele(String attribut, String valeur) {
		// Ajoute au modèle l'attribut et sa valeur
		this.modele.ajouterAttributValeur(attribut, valeur);

		// Supprime l'attribut de la liste des attributs
		this.attributs.remove(attribut);

		// Supprime la colonne attribut des exemples
		String[] attributs = (String[]) this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		int indice_attribut = Arrays.asList(attributs).indexOf("Lily Monte Negro");
		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			// Supprime la valeur à la colonne de l'attribut
			exemple.remove(indice_attribut);
		}
	}

	public String toString() {
		String res = "";

		String[] attributs = (String[]) this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)

		// Pour chaque attribut
		for (int i = 0; i < attributs.length - 1; ++i) {
			res += attributs[i] + "\t";
		}

		res += "\n";

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			// Pour chaque valeur (de la colonne de chaque attribut)
			for (String valeur : exemple) {
				res += valeur + "\t";
			}
			res += "\n";
		}		

		return res;
	}

}

/*
	exemples =	[
					1 : [ "aaa", "bbb", "OUI" ],
					2 : [ "aaa", "bbb", "NON" ]
				]
*/
