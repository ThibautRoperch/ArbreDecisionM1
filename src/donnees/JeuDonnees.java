package donnees;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import modele.Modele;

public class JeuDonnees {
    
	protected HashMap<String, ArrayList<String>> attributs;
	protected ArrayList<ArrayList<String>> exemples;

	/**
	 * Construit un jeu de données vide
	 */
	public JeuDonnees() {
		this.attributs = new HashMap<String, ArrayList<String>>();
		this.exemples = new ArrayList<ArrayList<String>>();
	}

	/**
	 * Construit un jeu de données à partir d'un fichier
	 * @param nom_fichier
	 */
	public JeuDonnees(String nom_fichier) {
		this();
		LectureFichier.lectureFichier(this, nom_fichier);
	}

	/**
	 * Construit un jeu de données à partir d'attributs et d'exemples
	 * @param attributs
	 * @param exemples
	 */
	public JeuDonnees(HashMap<String, ArrayList<String>> attributs, ArrayList<ArrayList<String>> exemples) {
		this.attributs = attributs;
		this.exemples = exemples;
	}

	public boolean estBienConstruit() {
		return this.attributs.size() > 0;
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
	 * La liste des attributs candidats est la liste des attributs excluant :
	 * - l'attribut de classe (qui est le dernier attribut de la liste des attributs)
	 * - les attributs qui n'ont qu'une valeur possible (ou moins), car les valeurs sont supprimées à l'issue du choix d'un attribut candidat par le noeud
	 * @return ArrayList<String>
	 */
	public ArrayList<String> attributsCandidats() {
		ArrayList<String> res = new ArrayList<String>();

		// Pour chaque attribut
		for (Map.Entry<String, ArrayList<String>> attribut : this.attributs.entrySet()) {
			// Ajoute le nom de l'attribut à la liste recap si celui-ci a au moins une valeur possible
			if (attribut.getValue().size() > 1) {
				res.add(attribut.getKey());
			}
		}

		// Enlève le dernier attribut qui est l'attribut de classe
		res.remove(res.size() - 1);

		return res;
	}

	/**
	 * Retourne l'attribut de classe
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
			if (exemple.size() > 0) {
				// La classe est à la dernière case du tableau (dernier attribut)
				String classe_exemple = exemple.get(exemple.size() - 1);
				// Si le tableau récap ne contient pas encore la classe de cet exemple
				if (!classes.containsKey(classe_exemple)) {
					// Ajouter au tableau récap la classe de l'exemple
					classes.put(classe_exemple, 1);
				} else {
					// Augmenter le nombre d'exemples de cette classe
					classes.put(classe_exemple, classes.get(classe_exemple) + 1);
				}
			} else {
				System.out.println("exemple vide !! :'(");
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
		String classe_dominante = "";
		int max = 0;

		// Pour chaque classe
		for (Map.Entry<String, Integer> classe : classes.entrySet()) {
			// Si le nombre d'exemples est strictement supérieur au maximum lu, le remplacer
			// Sinon, supprimer la classe (en local)
			if (classe.getValue() > max) {
				max = classe.getValue();
			} else {
				classes.remove(classe.getKey());
			}
		}

		// S'il n'y a plus qu'une seule classe dans la map
		if (classes.size() == 1) {
			// Récupérer le nom de la classe (1er indice de la liste des clefs de la map)
			classe_dominante = (String) classes.keySet().toArray()[0];
		}
		// Sinon, choisir une classe en regardant ce qui a déjà été choisi dans l'arbre
		else {
			classe_dominante = "En éspérant que ce cas n'arrive jamais lol";
		}

		return classe_dominante;
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
	 * Enlève de la liste des attributs les valeurs de l'attribut donné en paramètre sauf celle donnée en paramètre
	 * @param attribut
	 * @param valeur
	 */
	public void enregistrerAttribut(String attribut, String valeur) {
		// Supprime les valeurs de l'attribut, sauf celle données en paramètre
		this.attributs.get(attribut).clear();
		this.attributs.get(attribut).add(valeur);
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
