/**
 * Classe Arbre
 *
 * Cette classe permet d'instancier un arbre de décision
 * 
 * Un arbre possède un pointeur vers le premier noed racine de l'arbre, ainsi que la liste des noeuds feuilles qu'il contient
 * Un arbre est construit avec un jeu de données d'apprentissage et peut être élagué avec un jeu de données de Validation,
 * les deux jeux devant être de la même structuer (vérification faite dans le programme instanciant un arbre)
 * 
 * Les caractéristiques et les statistiques de l'arbre sont calculés sur les jeux de validation de ses feuilles s'ils sont existants,
 * sinon sur les jeux d'apprentissage de ses feuilles
 */

package arbre;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	protected Modele modele;

	public static int methode_de_choix;
	public static boolean afficher_noeuds_vides;
	
	/**
	 * Instancie un arbre vide
	 */
	public Arbre() {
		this.jeu_apprentissage = null;
		this.jeu_validation = null;
		this.noeud_racine = null;
		this.feuilles = new ArrayList<Noeud>();
		this.modele = null;
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
			if (!n.estPurValidation()) {
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
		return (this.noeud_racine != null) ? this.noeud_racine.nombreDescendances() - 1 : 0;
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

		// Lancer la construction de l'arbre
		this.noeud_racine = new Noeud(this, null, "Racine", this.jeu_apprentissage);
		this.noeud_racine.start();
	}

	/**
	 * Post-élague l'arbre de décision, en partant de son noeud racine
	 * Un noeud vérifie si le jeu de validation entraine une augmentation du taux de bonnes réponses (>= coeff_v) par rapport au jeu d'apprentissage,
	 * auquel cas le noeud devient une feuille et ses fils sont enlevés de l'arbre
	 * @param donnees_validation
	 * @param coeff_v
	 */
	public void postElaguer(JeuDonnees donnees_validation, double coeff_v) {
		// Enregistrer le jeu de validation
		this.jeu_validation = donnees_validation;

		// Lancer le post-élagage de l'arbre
		// Supprimer les feuilles, car avec le regroupement des fils, des noeuds vont devenir feuille (et s'ajouter à la liste des feuilles)
		// et des feuilles vont disparaître sans pouvoir se supprimer de la liste des feuilles, donc remise à zéro de la liste des feuilles
		this.feuilles.clear();
		this.noeud_racine.regrouperFils(this.jeu_validation, coeff_v);
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

		this.modele = m;

		return m;
	}

	public String toString() {
		String res = jeu_apprentissage.toString();

		res += (this.noeud_racine != null) ? "\n" + this.noeud_racine.toString() : "";
		
		return res;
	}

	public String toTree(boolean afficher_noeuds_vides) {
		// Enregistrer la condition d'affichage des noeuds vides, récupérée par les noeuds de l'arbre
		this.afficher_noeuds_vides = afficher_noeuds_vides;
		
		String res = "";

		res += "Note : S'il n'y a pas eu d'élagage, le jeu de validation est considéré comme non existant\nAinsi, ce sont les informations du jeu d'apprentissage de chaque noeuds qui seront affichées\n";

		res += (this.noeud_racine != null) ? this.noeud_racine.toTree(0) : "";

		return res += "\n";
	}

	public String toCharacteristics() {
		String res = "";

		res += "\nArbre parfait :\t\t" + this.estParfait() + "\n";
		res += "\nNombre de feuilles :\t" + this.feuilles.size() + "\n";
		res += "\nFeuilles non vides :\t" + this.modele.nombreDeRegles() + "\n";
		int surapprentissage = (this.jeu_apprentissage.nombreExemples() > 0) ? this.modele.nombreDeRegles() * 100 / this.jeu_apprentissage.nombreExemples() : 0; // surapprentissage = autant de feuilles non vides que d'individus dans la population
		res += "\nSurapprentissage :\t" + surapprentissage + "%\n";
		res += "\nHauteur de l'arbre :\t" + this.hauteur() + "\n";

		return res;
	}

	public String toStatistics() {
		String res = "";

		res += "Note : S'il n'y a pas eu d'élagage, le jeu de validation est considéré comme non existant\nAinsi, les satistiques seront calculées avec le jeu d'apprentissage (résultats optimistes)\n";

		// Nom des classes, [ nombre d'éléments ayant la valeur classe, nombre d'éléments total ]
		HashMap<String, int[]> taux_erreur_validation = new HashMap<String, int[]>();

		// Pour chaque valeur de l'attribut classe
		for (String classe : this.jeu_apprentissage.attributClasse().valeurs()) {
			// Enregistrement de la classe et [ 0, 0 ] en proportion
			taux_erreur_validation.put(classe, new int[] { 0, 0 });
		}

		// Pour chaque feuille de l'arbre
		for (Noeud n : this.feuilles) {
			ArrayList<String> classes_non_majoritaires = n.classesNonMajoritairesValidation();
			for (String classe : classes_non_majoritaires) {
				// Enregistrement de la proportion de cette classe de ce noeud feuille
				int[] taux_deja_enregistre = taux_erreur_validation.get(classe);
				int[] taux_erreur_classe = { taux_deja_enregistre[0] + n.proportionClasseValidation(classe)[0], taux_deja_enregistre[1] + n.proportionClasseValidation(classe)[1] };
				taux_erreur_validation.put(classe, taux_erreur_classe);
			}
		}

		double taux_erreur_validation_total = 0;

		res += "\nTaux d'erreur des classes :\n\n";

		// Pour chaque couple (classe, proportion[])
		for (Map.Entry<String, int[]> e : taux_erreur_validation.entrySet()) {
			if (e.getValue()[1] > 0) {
				res += e.getKey() + "\t" +  new DecimalFormat("#0.00").format((double) e.getValue()[0] / (double) e.getValue()[1] * 100) + " %\n";
				taux_erreur_validation_total += (double) e.getValue()[0] / (double) e.getValue()[1] * 100;
			} else {
				res += e.getKey() + "\t" +  new DecimalFormat("#0.00").format(0) + " %\n";
				taux_erreur_validation_total += (double) 0;
			}
		}
		
		res += "---------------------------------\n";
		double taux_erreur_validation_moyen = (taux_erreur_validation.size() > 0) ? taux_erreur_validation_total / taux_erreur_validation.size() : (double) 0;
		res += "Moyenne\t" + new DecimalFormat("#0.00").format(taux_erreur_validation_moyen) + " %\n";
		res += "Somme\t" + new DecimalFormat("#0.00").format(taux_erreur_validation_total) + " %\n";

		return res;
	}

	public static void main(String[] args) {
		Arbre a = new Arbre();
		// a.construire(new JeuDonnees("jeux/vote.arff"), Arbre.GAIN_INFORMATION);
		a.construire(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"), Arbre.GAIN_INFORMATION);
		// System.out.println(a);
		System.out.println(a.toTree(true));
		System.out.println(a.toCharacteristics());
		System.out.println(a.genererModele());
		// a.postElaguer(new JeuDonnees("jeux/vote.arff"), 0.005);
		a.postElaguer(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"), 0.005);
		System.out.println(a.toTree(true));
		System.out.println(a.toCharacteristics());
		System.out.println(a.toStatistics());
		// System.out.println(a.genererModele());
	}

}
