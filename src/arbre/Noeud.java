package arbre;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.text.DecimalFormat;

import donnees.JeuDonnees;
import donnees.Attribut;
import modele.Regle;

public class Noeud /*extends Thread*/ {

	protected Arbre arbre;
	protected Noeud noeud_pere;
	protected String nom;
	protected JeuDonnees jeu_apprentissage;
	protected JeuDonnees jeu_validation;
	protected ArrayList<Noeud> noeuds_fils;

	/**
	 * Instancie un noeud à partir de l'arbre auquel il appartient, de son noeud père, d'un nom et d'un jeu de données
	 * @param arbre
	 * @param pere
	 * @param nom
	 * @param donnees
	 */
	public Noeud(Arbre arbre, Noeud pere, String nom, JeuDonnees donnees) {
		this.arbre = arbre;
		this.noeud_pere = pere;
		this.nom = nom;
		this.jeu_apprentissage = donnees;
		this.jeu_validation = null;
		this.noeuds_fils = new ArrayList<Noeud>();
	}

	/**
	 * Retourne true si le noeud est pur
	 * Un noeud est pur si son jeu de données ne contient que des exemples appartenant à la même classe (ou aucun exemple)
	 * Un noeud pur est une feuille de l'arbre de décision
	 * @return boolean
	 */
	public boolean estPur() {
		return this.jeu_apprentissage.valeursClasseExemples().size() <= 1;
	}

	/**
	 * Retourne le taux d'erreur du jeu de validation du noeud
	 * Si le jeu de validation n'existe pas, alors retourne le taux d'erreur du jeu d'apprentissage
	 * @return double
	 */
	public double tauxErreurValidation() {
		if (this.jeu_validation == null) {
			return this.jeu_apprentissage.tauxErreur();
		}
		return this.jeu_validation.tauxErreur();
	}

	/**
	 * Retourne la classe majoritaire du jeu de validation du noeud
	 * Si le jeu de validation n'existe pas, alors retourne la classe majoritaire du jeu d'apprentissage
	 * @return String
	 */
	public String classeMajoritaireValidation() {
		if (this.jeu_validation == null) {
			return this.jeu_apprentissage.classeMajoritaire();
		}
		return this.jeu_validation.classeMajoritaire();
	}

	/**
	 * Retourne les classes non majoritaires du jeu de validation du noeud
	 * Si le jeu de validation n'existe pas, alors retourne les classes non majoritaires du jeu d'apprentissage
	 * @return String
	 */
	public ArrayList<String> classesNonMajoritairesValidation() {
		ArrayList<String> res = new ArrayList<String>();

		if (this.jeu_validation == null) {
			res.addAll(this.jeu_apprentissage.valeursClasseExemples().keySet());
			res.remove(this.jeu_apprentissage.classeMajoritaire());
		} else {
			res.addAll(this.jeu_validation.valeursClasseExemples().keySet());
			res.remove(this.jeu_validation.classeMajoritaire());
		}

		return res;
	}

	/**
	 * Retourne la proportion de la classe donnée en paramètre dans le jeu de validation du noeud
	 * Si le jeu de validation n'existe pas, alors retourne la proportion de la classe donnée en paramètre dans le jeu d'apprentissage
	 * @param classe
	 * @return double
	 */
	public double proportionClasseValidation(String classe) {
		if (this.jeu_validation == null) {
			return (double) this.jeu_apprentissage.selectionnerExemplesOu(this.jeu_apprentissage.attributClasse(), classe).size() / (double) this.jeu_apprentissage.nombreExemples();
		}
		return (double) this.jeu_validation.selectionnerExemplesOu(this.jeu_validation.attributClasse(), classe).size() / (double) this.jeu_validation.nombreExemples();
	}

	/**
	 * Retourne le nombre de descendances du noeud
	 * @return int
	 */
	public int nombreDescendances() {
		int max = 0;

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			 if (fils.nombreDescendances() > max) {
				 max = fils.nombreDescendances();
			 }
		}
		
		return 1 + max;
	}

	/**
	 * Retourne la règle de décision associée au noeud
	 * Les conditions de la règle sont les attributs précédemment choisis et la conclusion est la classe majoritaire du jeu de données du noeud
	 * @return Regle
	 */
	public Regle genererRegle() {
		Regle r = new Regle();

		// Pour chaque attribut du jeu de données
		for (Attribut attribut : this.jeu_apprentissage.attributs()) {
			// Ajouter à la règle l'attribut et sa valeur en tant que condition s'il n'a qu'une valeur possible (def d'un attribut choisi) et si ce n'est pas l'attribut classe
			if (attribut.valeurs().size() == 1 && !attribut.equals(this.jeu_apprentissage.attributClasse())) {
				r.ajouterCondition(attribut);
			}
		}

		// Ajouter à la règle l'attribut de classe et sa valeur en tant que conclusion
		// Sa valeur est la classe majoritaire parmi les exemples du jeu de données
		r.ajouterConclusion(this.jeu_apprentissage.attributClasse());
		
		return r;
	}

	/**
	 * Créé des noeuds fils pour chaque valeur de l'attribut choisi
	 * Un noeud fils a un son propre jeu de données et son propre modèle, tous deux créés à partir de ceux de this
	 * Si le noeud est pur ou s'il n'a pas d'attributs candidats dans son jeu de données, alors c'est une feuille
	 */
	public void start() {
		// Si ce noeud n'est pas pur et s'il y a des attributs à évaluer, créer des noeuds fils
		if (!this.estPur() && this.jeu_apprentissage.attributsCandidats().size() > 0) {
			// Choisir un attribut en fonction du gain d'information de chacune de ses valeurs
			Attribut attribut_choisi = meilleurAttribut(this.arbre.methode_de_choix);
			// Récupérer les valeurs possibles pour l'attribut choisi
			ArrayList<String> valeurs_possibles = attribut_choisi.valeurs();
			// Créer autant de noeuds fils qu'il y a de valeurs pour l'attribut choisi
			for (String valeur_possible : valeurs_possibles) {
				// Créer un nouveau jeu de données pour le fils, pour cela :
				// Partir du jeu de données du noeud actuel et enlever les exemples où attribut_choisi != valeur
				// Indiquer au jeu de données l'attribut et la valeur utilisés par le noeud
				JeuDonnees donnees_fils = new JeuDonnees(	this.jeu_apprentissage.attributs(),
															this.jeu_apprentissage.selectionnerExemplesOu(attribut_choisi, valeur_possible)	);
				donnees_fils.choisirAttributValeur(attribut_choisi, valeur_possible);
				// Créer à partir des données, enregistrer et lancer le noeud fils
				Noeud noeud_fils = new Noeud(this.arbre, this, attribut_choisi.nom() + " = " + valeur_possible, donnees_fils); // peut contenir 0 exemples dans son jeu de données, la règle ainsi générée sera "ALORS class = "
				this.noeuds_fils.add(noeud_fils);
				noeud_fils.start();
			}
		}
		// Sinon, ce noeud est une feuille, indiquer au jeu de données la valeur de la classe majoritaire des exemples
		// Ajouter ce noeud à la liste des feuilles de l'abre auquel ce noeud appartient
		else {
			Attribut attribut_classe = this.jeu_apprentissage.attributClasse();
			String valeur_classe = this.jeu_apprentissage.classeMajoritaire();
			this.jeu_apprentissage.choisirAttributValeur(attribut_classe, valeur_classe);
			this.arbre.ajouterFeuille(this);
			System.out.println(this.genererRegle());
		}
	}

	/**
	 * Choisit le meilleur attribut en fonction de celui qui génère le gain d'information le plus élevé
	 * @return Attribut
	 */
	private Attribut meilleurAttribut(int methode_de_choix) {
		ArrayList<Attribut> attributs_candidats = this.jeu_apprentissage.attributsCandidats();
		Attribut res = null;

		switch (methode_de_choix) {
			case 1: // premier attribut
				res = attributs_candidats.get(0);
				break;
			case 2: // attribut au hasard
				res = attributs_candidats.get( (int) (Math.random() * attributs_candidats.size()) );
				break;
			case 3: // gain d'information le plus élevé
				double max_gain = 0;
				Attribut max_gain_attribut = null;

				// Pour chaque attribut
				for (Attribut attribut_candidat : attributs_candidats) {
					// Si le nombre d'exemples est strictement supérieur au maximum lu, le remplacer
					// Sinon, supprimer l'attribut (en local)
					double gain_attribut = this.gain(attribut_candidat);
					if (gain_attribut > max_gain) {
						max_gain = gain_attribut;
						max_gain_attribut = attribut_candidat;
					}
				}
				
				res = max_gain_attribut;
				break;
			default: // le premier par défaut
				res = attributs_candidats.get(0);
				break;
		}
		
		return res;
	}

	/**
	 * Calcule le gain d'information généré par un attribut donné en paramètre
	 * Le gain se calcul avec les valeurs possibles de l'attribut donné en paramètre
	 * L'entropie de chaque jeu de données fils (jeu de données généré par chaque valeur de l'attribut) est soustraite à celle du jeu de données du père
	 * @param attribut
	 * @return double
	 */
	private double gain(Attribut attribut) {
		double gain = this.jeu_apprentissage.entropie();

		int nombre_exemples_this = this.jeu_apprentissage.nombreExemples();
		ArrayList<String> valeurs_possibles = attribut.valeurs();
		
		// Pour chaque valeur de l'attribut donné en paramètre (donc pour chaque potentiels noeuds fils)
		for (String valeur_possible : valeurs_possibles) {
			// Créer un jeu de données sélectionnant les exemples où l'attribut vaut cette valeur
			JeuDonnees donnees_fils = new JeuDonnees(this.jeu_apprentissage.selectionnerExemplesOu(attribut, valeur_possible));
			// Soustraire au gain du jeu de données père le gain de ce jeu de données fils
			gain -= (double) donnees_fils.nombreExemples() / (double) nombre_exemples_this * donnees_fils.entropie();
		}

		return gain;
	}

	/**
	 * Tente de regrouper les noeuds fils avec ce noeud si le jeu de validation entraine une augmentation du taux de bonnes réponses (+ coeff_v)
	 * par rapport au jeu d'apprentissage
	 * S'il y a plus de taux de bonnes réponses dans le jeu de validation que dans le jeu d'apprentissage (+ coeff_v) :
	 * - Les fils sont supprimés et leurs jeux de données (du jeu d'apprentissage, donc) sont fusionnés avec celui de ce noeud
	 * - Ce noeud devient une feuille et s'ajoute à la liste des feuilles de l'arbre auquel il appartient
	 * Le regroupement des fils se fait dans le cadre d'un élagage de l'arbre
	 * @param donnees_validation
	 * @param coeff_v
	 */
	public void regrouperFils(JeuDonnees donnees_validation, double coeff_v) {
		ArrayList<Attribut> attributs_this = this.jeu_apprentissage.attributs();
		this.jeu_validation = new JeuDonnees(attributs_this, donnees_validation.exemples()); // exemples pouvant être placés à ce noeud

		// Sélection des exemples du jeu de validation satisfaisant les attributs choisis de ce noeud
		// Pour chaque attribut du jeu de données de ce noeud
		for (Attribut attribut : attributs_this) {
			// Sélection des exemples du jeu de validation où l'attribut = cet attribut s'il n'a qu'une valeur possible pour cet attribut (def d'un attribut choisi) et si ce n'est pas l'attribut classe
			if (attribut.valeurs().size() == 1 && !attribut.equals(this.jeu_apprentissage.attributClasse())) {
				this.jeu_validation = new JeuDonnees(attributs_this, this.jeu_validation.selectionnerExemplesOu(attribut, attribut.valeurs().get(0))); // il n'y a qu'une valeur, en théorie, get(0) est la première et la dernière
			}
		}
		
		// Si le taux de bonnes réponses du jeu de validation est supérieur ou égal au taux de bonnes réponses du jeu d'apprentissage + le coefficient v donné en paramètre,
		// regrouper les noeud fils avec ce noeud et ajouter ce noeud à la liste des feuilles de l'arbre
		// Si ce noeud est déjà une feuille, re-ajouter ce noeud à la liste des feuilles de l'arbre
		// Taux de réussite de l'arbre élagué augmente d'une valeur coeff_v par rapport à l'arbre non élagué => regroupement
		if (1 - this.jeu_validation.tauxErreur() >= (1 - this.jeu_apprentissage.tauxErreur()) + coeff_v || this.noeuds_fils.size() == 0) {
			this.noeuds_fils.clear();
			this.arbre.ajouterFeuille(this);
		}
		// Sinon, parcours en profondeur, tester si les fils peuvent être regroupés en commençant par le premier
		else {
			// Pour chaque noeud fils de ce noeud
			for (Noeud fils : this.noeuds_fils) {
				fils.regrouperFils(this.jeu_validation, coeff_v);
			}
		}
	}

	public String toString() {
		String res = "\n----- NOEUD -----\n";

		res += this.jeu_apprentissage + "\n";

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			res += fils + "\n";
		}
		
		return res;
	}

	public String toTree(int level) {
		// Ne rien afficher si le jeu d'apprentissage ne contient pas d'exemple dans le cas d'un jeu de validation null,
		// ou si le jeu de validation ne contient pas d'exemple dans le cas d'un jeu de validation non null
		if (this.jeu_validation == null && this.jeu_apprentissage.nombreExemples() == 0
		 || this.jeu_validation != null && this.jeu_validation.nombreExemples() == 0) {
			return "";
		}

		String res = "";
		String margin_top = "";
		String margin_left = "";
		int niveau = level;

		while (niveau > 0) {
			margin_top += " |   ";
			if (niveau > 1) {
				margin_left += " |   ";
			} else {
				margin_left += " |____";
			}
			--niveau;
		}

		String margin = margin_top + "\n" + margin_left;
		
		res += "\n" + margin + "[" + this.nom + "] " + this.jeu_apprentissage.valeursClasseExemples();

		if (this.jeu_validation != null && this.noeuds_fils.size() == 0) {
			res += " (" + new DecimalFormat("#0.00").format(this.jeu_apprentissage.tauxErreur() * 100) + " % de taux d'erreur)";
		}

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			// Affiche le noeud en augmentant le niveau
			res += fils.toTree(level + 1);			
		}

		return res;
	}

}
