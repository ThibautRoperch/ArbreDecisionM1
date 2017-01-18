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
		// LectureFichier.remplirDepuisFichier(this, nom_fichier);
	}

	/**
	 * Construit un jeu de données à partir d'attributs et d'exemples
	 * @param attributs
	 * @param exemples
	 */
	public JeuDonnees(HashMap<String, ArrayList<String>> attributs, ArrayList<ArrayList<String>> exemples, Modele modele) {
		this.attributs = attributs;
		this.exemples = exemples;
		this.modele = modele;
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
	 * Retourne une copie du modèle
	 * @return Modele
	 */
	 public Modele modele() {
		 return this.modele.clone();
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

		// Pour chaque attribut
		for (Map.Entry<String, ArrayList<String>> attribut : this.attributs.entrySet()) {
			// Ajoute le nom de l'attribut à la liste recap
			res.add(attribut.getKey());
		}
		
		return res;
	}

	/**
	 * Retourne l'attribut classe
	 * @return String
	 */
	public String attributClasse() {
		ArrayList<String> res = new ArrayList<String>();
		Object[] attributs = this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		return (String) attributs[attributs.length - 1];
	}

	/**
	 * Retourne les valeurs possibles pour un attribut donné en paramètre
	 * @param attribut
	 * @return ArrayList<String>
	 */
	public ArrayList<String> valeursPossibles(String attribut) {
		return this.attributs.get(attribut);
	}

	/**
	 * Retourne la liste des différentes valeurs de la classe des exemples
	 * La liste est un tableau associatif avec en clef la classe et en valeur le nombre d'exemples ayant cette classe
	 * @return HashMap<String, int>
	 */
	public HashMap<String, Integer> valeursClasseExemples() {
		HashMap<String, Integer> classes = new HashMap<String, Integer>();

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			// La classe est à la dernière case du tableau (dernier attribut)
			String classe_exemple = exemple.get(exemple.size()-1);
			// Si le tableau récap ne contient pas encore la classe de cet exemple
			if (!classes.containsKey(classe_exemple)) {
				// Ajouter au tableau récap la classe de l'exemple
				classes.put(classe_exemple, 1);
			} else {
				// Augmenter le nombre d'exemples de cette classe
				classes.put(classe_exemple, classes.get(classe_exemple)+1);
			}
		}

		return classes;
	}

	/**
	 * Retourne la classe dominante parmi les exemples
	 * @return String
	 */
	public String classeDominante() {
		HashMap<String, Integer> classes = this.valeursClasseExemples();
		int max = 0;
		// Pour chaque classe
		for (Map.Entry<String, Integer> classe : classes.entrySet()) {
			// Si le nombre d'exemples est strictement supérieur au maximum lu, le remplascer
			// Sinon, supprimer la classe (en local)
			if (classe.getValue() > max) {
				max = classe.getValue();
			} else {
				classes.remove(classe.getKey());
			}
		}
		// si egalité, comment on fait ? y'aura deux trucs ou + dans classes, comment choisir ?
		// get (dans l'arbre) les trucs édjà trouvé (faire une liste)
		return "";
	}

	/**
	 * Retourne les exemples du jeu de données où attribut = valeur donnés en paramètre
	 * @param attribut
	 * @param valeur
	 */
	public ArrayList<ArrayList<String>> selectionnerExemplesOu(String attribut, String valeur) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		Object[] attributs = this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		int indice_attribut = Arrays.asList(attributs).indexOf(attribut);

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
	 * Ajoute au modèle l'attribut et sa valeur en tant que condition
	 * Enlève de la liste des attributs l'attribut donné en paramètre, ainsi que des exemples
	 * @param attribut
	 * @param valeur
	 */
	public void enregistrerCondition(String attribut, String valeur) {
		// Ajoute au modèle l'attribut et sa valeur
		this.modele.ajouterCondition(attribut, valeur);

		// Supprime l'attribut de la liste des attributs
		this.attributs.remove(attribut);

		// Supprime la colonne attribut des exemples
		Object[] attributs = this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		int indice_attribut = Arrays.asList(attributs).indexOf(attribut);
		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			// Supprime la valeur à la colonne de l'attribut
			exemple.remove(indice_attribut);
		}
	}

	/**
	 * Ajoute au modèle l'attribut classe et sa valeur en tant que conclusion
	 * @param valeur
	 */
	public void enregistrerConclusion(String valeur) {
		Object[] attributs = this.attributs.keySet().toArray(); // converti la map en liste des clefs (récupération des attributs)
		String classe = (String) attributs[attributs.length - 1];
		this.modele.ajouterConclusion(classe, valeur);
	}

	public String toString() {
		String res = "";

		// Pour chaque attribut
		for (Map.Entry<String, ArrayList<String>> attribut : this.attributs.entrySet()) {
			// Ajoute le nom de l'attribut à la chaine
			res += attribut.getKey() + "\t";
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
