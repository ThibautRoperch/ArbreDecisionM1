package arbre;

import java.util.ArrayList;

import donnees.JeuDonnees;
import modele.Modele;
import modele.Regle;

public class Arbre {
	
	public static final int PREMIER = 1;
	public static final int HASARD = 2;
	public static final int GAIN_INFORMATION = 3;

	protected JeuDonnees jeu_apprentissage;
	protected JeuDonnees jeu_validation;
	protected Noeud noeud_racine;
	protected ArrayList<Noeud> feuilles;
	public static int methode_de_choix;
	
	/**
	 * Instancie un arbre vide
	 */
	public Arbre() {
		this.jeu_apprentissage = null;
		this.jeu_validation = null;
		this.noeud_racine = null;
		this.feuilles = new ArrayList<Noeud>();
		this.methode_de_choix = 0;
	}

	/**
	 * Retourne true si l'arbre est parfait
	 * Un arbre est dit parfait si toutes ses feuilles sont pures
	 * @return boolean
	 */
	public boolean estParfait() {
		// Pour chaque feuille de l'arbre
		for (Noeud n : this.feuilles) {
			// Si la feuille n'est pas pure, retourner false
			if (!n.estPur()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retourne la hauteur (profondeur) de l'arbre
	 * @return int
	 */
	public int hauteur() {
		return this.noeud_racine.nombreDescendances() - 1;
	}

	/**
	 * Ajoute une feuille à la liste des feuilles de l'arbre
	 * @param noeud
	 */
	public void ajouterFeuille(Noeud noeud) {
		this.feuilles.add(noeud); // this pour que les noeuds puissent appeller ajouterFeuille
	}

	/**
	 * Construit l'arbre de décision, en partant de son noeud racine
	 * Un noeud choisit le meilleur attribut en prenant le premier, ou au hasard, ou le gain d'information (et d'entropie) le plus élevé
	 * et construit ses noeuds fils en fonction des valeurs de l'attribut choisi, à raison d'un fils par valeur
	 * @param donnees_apprentissage
	 * @param methode_de_choix
	 */
	public void construire(JeuDonnees donnees_apprentissage, int methode_de_choix) {
		// Enregistrer le jeu d'apprentissage
		this.jeu_apprentissage = donnees_apprentissage;

		// Enregistrer la méthode de choix, récupérée par les noeuds de l'arbre
		this.methode_de_choix = methode_de_choix;

		// Si le jeu de données est bien construit, lancer la construction de l'arbre
		if (this.jeu_apprentissage.estBienConstruit()) {
			this.noeud_racine = new Noeud(this, null, "Racine", this.jeu_apprentissage);
			this.noeud_racine.start();
		} else {
			System.out.println("Erreur : Le jeu de données d'apprentissage est vide d'attributs, impossible de construire l'arbre");
		}
	}

	/**
	 * Post-élague l'arbre de décision, en partant de son noeud racine
	 * Un noeud vérifie si le jeu de validation entraine une augmentation du taux de bonnes réponses (>= coeff_v) par rapport au jeu d'apprentissage,
	 * auquel cas le noeud devient une feuille et ses fils sont enlevés de l'arbre
	 * @param donnees_validation
	 * @param coeff_v
	 */
	public void postElaguer(JeuDonnees donnees_validation, double coeff_v) {
		// Si le jeu de données de validation est du même acabit que le jeu de données d'apprentissage, lancer le post-élagage de l'arbre
		if (donnees_validation.estConstruitComme(this.jeu_apprentissage)) {
			// Supprimer les feuilles, car avec le regroupement des fils, des noeuds vont devenir feuille (et s'ajouter à la liste des feuilles)
			// et des feuilles vont disparaître sans pouvoir se supprimer de la liste des feuilles, donc remise à zéro de la liste des feuilles
			this.feuilles.clear();
			this.noeud_racine.regrouperFils(donnees_validation, coeff_v);
		} else {
			System.out.println("Erreur : Le jeu de données de validation n'a pas les mêmes attributs que le jeu de données d'apprentissage, impossible de post-élaguer l'arbre");
		}
	}

	/**
	 * Retourne le modèle de l'arbre de décision
	 * Le modèle est l'ensemble des règles de décision des feuilles de l'arbre
	 * @return Modele
	 */
	public Modele genererModele() {
		Modele m = new Modele();

		// Pour chaque feuille de l'arbre
		for (Noeud n : this.feuilles) {
			Regle r = n.genererRegle();
			// Si la règle conclut sur l'attribut de classe
			if (r.conclut()) {
				// Ajouter au modèle la règle de la feuille
				m.ajouterRegle(r);
			}
		}

		return m;
	}

	public String toString() {
		return jeu_apprentissage + "\n" + noeud_racine;
	}

	public String toTree() {
		String res = "\n# Affichage de l'arbre\n";

		res += this.noeud_racine.toTree(0);

		return res;
	}

	public String toCharacteristics() {
		String res = "\n\n# Caractéristiques de l'arbre\n";

		res += "\nArbre parfait :\t\t" + this.estParfait() + "\n";
		res += "\nNombre de feuilles :\t" + this.feuilles.size() + "\n";
		int surapprentissage = this.feuilles.size() * 100 / this.jeu_apprentissage.nombreExemples(); // surapprentissage = autant de feuilles que d'individus dans la population
		res += "\nSurapprentissage :\t" + surapprentissage + "%\n";
		res += "\nHauteur de l'arbre :\t" + this.hauteur() + "\n";
		// int surapprentissage = this.hauteur() * 100 / this.jeu_apprentissage.nombreAttributs();
		// res += "\nSurapprentissage :\t" + surapprentissage + "%\n";

		return res;
	}

	public String toStatistics() {
		String res = "\n\n# Statistiques de l'arbre\n";

		// Pour chaque feuille de l'arbre
		for (Noeud n : this.feuilles) {

		}

		/*Taux d'erreur moyen par classe (uniquement les feuilles)
		Taux d'erreur moyen total (uniquement les feuilles)*/

		return res;
	}

	public static void main(String[] args) {
		Arbre a = new Arbre();
		// a.construire(new JeuDonnees("jeux/vote.arff"), Arbre.GAIN_INFORMATION);
		a.construire(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"), Arbre.GAIN_INFORMATION);
		// System.out.println(a);
		// System.out.println(a.toTree());
		System.out.println(a.toCharacteristics());
		System.out.println(a.toStatistics());
		// System.out.println(a.genererModele());
		// a.postElaguer(new JeuDonnees("jeux/vote.arff"), 0.005);
		a.postElaguer(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"), 0.05);
		// System.out.println(a.toTree());
		System.out.println(a.toCharacteristics());
		System.out.println(a.toStatistics());
		// System.out.println(a.genererModele());
	}

}
