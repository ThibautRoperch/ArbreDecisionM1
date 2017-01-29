/**
 * Au fur et à mesure de l'évolution d'une branche de l'arbre, un jeu de donénes est dupliqué pour un noeud fils
 * et modifié en fonction de l'attribut utilisé par ce noeud fils
 * 
 * Par exemple, l'ensemble des données va être modifié de cette façon : le noeud applique la valeur "soleil" à l'attribut "ensoleillement"
 * - La liste des exemples ne contient plus que les exemples où "ensoleillement" = "soleil"
 * - L'attribut "ensoleillement" n'a comme valeur possible plus que "soleil" dans la liste des attributs
 */

package donnees;

import java.util.Map;
import java.util.HashMap;
import java.sql.Array;
import java.util.ArrayList;

import modele.Modele;

public class JeuDonnees {
    
	protected ArrayList<Attribut> attributs;
	protected ArrayList<ArrayList<String>> exemples;

	/**
	 * Construit un jeu de données vide
	 */
	public JeuDonnees() {
		this.attributs = new ArrayList<Attribut>();
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
	public JeuDonnees(ArrayList<Attribut> attributs, ArrayList<ArrayList<String>> exemples) {
		this.attributs = attributs;
		this.exemples = exemples;
	}

	/**
	 * Construit un jeu de données à partir d'exemples
	 * Le jeu de données n'aura pas d'attributs, il sera donc jeu mal consturit
	 * Ce constructeur est appellé dans le cadre de calculs d'entropie, où seuls les exemples sont nécessaires
	 */
	public JeuDonnees(ArrayList<ArrayList<String>> exemples) {
		this();
		this.exemples = exemples;
	}

	/**
	 * Retourne une copie des attributs
	 * @return ArrayList<Attribut>
	 */
	public ArrayList<Attribut> attributs() {
		ArrayList<Attribut> res = new ArrayList<Attribut>();

		// Pour chaque attribut
		for (Attribut attribut : this.attributs) {
			res.add(attribut.clone());
		}

		return res;
	}

	/**
	 * Retourne une copie des exemples
	 * @return ArrayList<ArrayList<String>>
	 */
	public ArrayList<ArrayList<String>> exemples() {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
			res.add(exemple);
		}

		return res;
	}

	/**
	 * Retourne le nombre d'attributs
	 */
	public int nombreAttributs() {
		return this.attributs.size();
	}

	/**
	 * Retourne le nombre d'exemples
	 */
	public int nombreExemples() {
		return this.exemples.size();
	}

	/**
	 * Retourne vrai si le jeu de données est bien construit, c'est à dire s'il contient des attributs
	 */
	public boolean estBienConstruit() {
		return this.attributs.size() > 0;
	}

	/**
	 * Retourne vrai si le jeu de données en paramètre a les mêmes attributs que le jeu de données this
	 */
	public boolean estConstruitComme(JeuDonnees donnees) {
		/*for (Attribut a : this.attributs) {
			if (donnes)
		}*/
		return this.attributs.equals(donnees.attributs);
	}
	
	/**
	 * Ajoute un attribut et ses valeurs possibles
	 * @param attribut
	 * @param valeurs
	 */
	public void ajouterAttribut(String attribut, ArrayList<String> valeurs) {
		this.attributs.add(new Attribut(attribut, valeurs));
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
	 * @return ArrayList<Attribut>
	 */
	public ArrayList<Attribut> attributsCandidats() {
		ArrayList<Attribut> res = new ArrayList<Attribut>();

		// Pour chaque attribut
		for (Attribut attribut : this.attributs) {
			// Ajoute l'attribut à la liste res si celui-ci a au moins deux valeurs possibles et si ce n'est pas l'attribut classe
			if (attribut.valeurs().size() > 1 && !attribut.equals(this.attributClasse())) {
				res.add(attribut.clone());
			}
		}
		
		return res;
	}

	/**
	 * Retourne l'attribut de classe
	 * @return Attribut
	 */
	public Attribut attributClasse() {
		return this.attributs.get(attributs.size() - 1);
	}

	/**
	 * Retourne la liste des différentes valeurs de l'attribut de classe parmi exemples
	 * La liste est un tableau associatif avec en clef la classe et en valeur le nombre d'exemples ayant cette classe
	 * @return HashMap<String, int>
	 */
	public HashMap<String, Integer> valeursClasseExemples() {
		HashMap<String, Integer> classes = new HashMap<String, Integer>();

		// Pour chaque exemple
		for (ArrayList<String> exemple : this.exemples) {
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
		}

		return classes;
	}

	/**
	 * Retourne la classe majoritaire parmi les exemples
	 * @return String
	 */
	public String classeMajoritaire() {
		HashMap<String, Integer> classes = this.valeursClasseExemples();
		String classe_majoritaire = "";
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

		// S'il y a au moins une classe dans la map
		if (classes.size() > 0) {
			// Récupérer le nom de la classe (dernier indice de la liste des clefs de la map)
			classe_majoritaire = (String) classes.keySet().toArray()[classes.keySet().toArray().length - 1];
		}

		return classe_majoritaire;
	}

	/**
	 * Retourne le taux d'erreur de la classe majoritaire
	 * Le taux d'erreur est le nombre d'exemples où l'attribut de classe vaut la classe majoritaire
	 * divisé par le nombre total d'exemples
	 * @return double
	 */
	public double tauxErreur() {
		if (this.nombreExemples() > 0) {
			JeuDonnees exemples_ou_classe_majoritaire = new JeuDonnees(this.selectionnerExemplesOu(this.attributClasse(), this.classeMajoritaire()));
			return 1 - (double) exemples_ou_classe_majoritaire.nombreExemples() / (double) this.nombreExemples();
		} else {
			return 0;
		}
	}
	
	/**
	 * Retourne les exemples du jeu de données où attribut = valeur donnée en paramètre
	 * @param attribut
	 * @param valeur
	 */
	public ArrayList<ArrayList<String>> selectionnerExemplesOu(Attribut attribut, String valeur) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();

		// Recherche de l'indice de l'attribut dans la liste d'attributs
		int indice_attribut = this.attributs.indexOf(attribut);

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
	public void choisirAttributValeur(Attribut attribut, String valeur) {
		this.attributs.get(this.attributs.indexOf(attribut)).fixerValeur(valeur);
	}

	/**
	 * Calcule l'entropie du jeu de données
	 * L'entropie se calcul avec la proportion de chaque classe dans l'ensemble des exemples du jeu de données
	 * @return double
	 */
	public double entropie() {
		double entropie = 0;

		// Récupérer les différentes valeurs de classe des exemples et le nombre d'exemples total
		// Proportion d'une classe dans un jeu de données = nombre d'exemples de la classe / nombre d'exemple total
		HashMap<String, Integer> nombre_exemples_classes = this.valeursClasseExemples();
		int nombre_exemples_total = this.nombreExemples();

		// Calculer l'entropie du jeu de données
		// Pour chaque classe
		for (Map.Entry<String, Integer> classe : nombre_exemples_classes.entrySet()) {
			// Calculer la proportion de cette classe
			double proportion_classe = (double) classe.getValue() / (double) nombre_exemples_total;
			// Calculer l'entropie de cette classe, si la proportion n'est pas égale à 0
			double entropie_classe = (proportion_classe > 0) ? (double) proportion_classe * log2(proportion_classe) : 0;
			// Soustraire l'entropie de la calsse à l'entropie du noeud
			entropie -= entropie_classe;
		}

		return entropie;
	}

	/**
	 * Calcule le logarithme de base 2 d'un nombre donné en paramètre
	 * @param n
	 */
	private double log2(double n) {
		return Math.log(n) / Math.log(2);
	}

	public String toString() {
		String res = "\n# Affichage du jeu de données\n";

		// Pour chaque attribut
		for (Attribut attribut : this.attributs) {
			// Ajoute le nom de l'attribut à la chaine
			res += attribut.nom() + "\t";
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

		res += "\n# Caractéristiques du jeu de données\n";
		res += "\nNombre d'exemples :\t" + this.nombreExemples() + "\n";
		res += "\nNombre d'attributs :\t" + this.nombreAttributs() + "\n";

		return res;
	}

	public static void main (String[] args) {
		// JeuDonnees jd = new JeuDonnees("jeux/vote.arff");
		JeuDonnees jd = new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff");
		System.out.println(jd);
	}

}

/*
	exemples =	[
					1 : [ "aaa", "bbb", "OUI" ],
					2 : [ "aaa", "bbb", "NON" ]
				]
*/
