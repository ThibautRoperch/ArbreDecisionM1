package arbre;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

import donnees.JeuDonnees;
import donnees.Attribut;
import modele.Regle;

public class Noeud /*extends Thread*/ {

	protected Arbre arbre;
	protected Noeud noeud_pere;
	protected String nom;
	protected JeuDonnees jeu_de_donnees;
	protected ArrayList<Noeud> noeuds_fils;

	/**
	 * Construit un noeud à partir de l'arbre auquel il appartient, de son noeud père et d'un jeu de données
	 * @param donnees
	 */
	public Noeud(Arbre arbre, Noeud pere, String nom, JeuDonnees donnees) {
		this.arbre = arbre;
		this.noeud_pere = pere;
		this.nom = nom;
		this.jeu_de_donnees = donnees;
		this.noeuds_fils = new ArrayList<Noeud>();
	}

	public Regle genererRegle() {
		Regle r = new Regle();

		// Pour chaque attribut du jeu de données
		for (Attribut attribut : this.jeu_de_donnees.attributs()) {
			// Ajouter à la règle l'attribut et sa valeur en tant que condition s'il n'a qu'une valeur possible et si ce n'est pas l'attribut classe
			if (attribut.valeurs().size() == 1 && !attribut.equals(this.jeu_de_donnees.attributClasse())) {
				r.ajouterCondition(attribut.nom(), attribut.valeurs().get(0)); // il n'y a qu'une valeur, en théorie, get(0) est la première et la dernière
			}
		}

		// Ajouter à la règle l'attribut de classe et sa valeur en tant que conclusion
		// Sa valeur est la classe majoritaire parmi les exemples du jeu de données
		r.ajouterConclusion(this.jeu_de_donnees.attributClasse().nom(), this.jeu_de_donnees.classeMajoritaire());
		
		return r;
	}

	/**
	 * Créé des noeuds fils pour chaque valeur de l'attribut choisi
	 * Un noeud fils a un son propre jeu de données et son propre modèle, tous deux créés à partir de ceux de this
	 * Si le noeud est pur ou s'il n'a pas d'attributs candidats dans son jeu de données, alors c'est une feuille
	 */
	public void start() {
		if (!this.jeu_de_donnees.estBienConstruit()) {
			System.out.println("Erreur : Jeu de données du noeud vide d'attributs");
		}

		// Si ce noeud n'est pas pur et s'il y a des attributs à évaluer, créer des noeuds fils
		if (!this.estPur() && this.jeu_de_donnees.attributsCandidats().size() > 0) {
			// Choisir un attribut en fonction du gain d'information de chacune de ses valeurs
			Attribut attribut_choisi = meilleurAttribut();
			// Récupérer les valeurs possibles pour l'attribut choisi
			ArrayList<String> valeurs_possibles = attribut_choisi.valeurs();
			// Créer autant de noeuds fils qu'il y a de valeurs pour l'attribut choisi
			for (String valeur_possible : valeurs_possibles) {
				// Créer un nouveau jeu de données pour le fils, pour cela :
				// Partir du jeu de données du noeud actuel et enlever les exemples où attribut_choisi != valeur
				// Indiquer au jeu de données l'attribut et la valeur utilisés par le noeud
				JeuDonnees donnees_fils = new JeuDonnees(	this.jeu_de_donnees.attributs(),
															this.jeu_de_donnees.selectionnerExemplesOu(attribut_choisi, valeur_possible)	);
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
			Attribut attribut_classe = this.jeu_de_donnees.attributClasse();
			String valeur_classe = this.jeu_de_donnees.classeMajoritaire();
			this.jeu_de_donnees.choisirAttributValeur(attribut_classe, valeur_classe);
			this.arbre.ajouterFeuille(this);
		}
	}

	/**
	 * Retourne true si le noeud est pur
	 * Un noeud est pur si son jeu de données ne contient que des exemples appartenant à la même classe (ou aucun exemple)
	 * Un noeud pur est une feuille de l'arbre de décision
	 * @return boolean
	 */
	public boolean estPur() {
		return this.jeu_de_donnees.valeursClasseExemples().size() <= 1;
	}



	/*
	//calculer le meilleur attribut à mettre en noeud racine, puis remplir les fils. Puis recommencer sur les fils.
	//Calcule le gain pour choisir le meilleur attribut.
	//Prendre le jeu d'apprentissage en paramètre maybe ? 
	private int gain(int positif, int negatif) {
		//return positif*(log(positif/positif+negatif)-log(P/P+N));

		//Appeler la fonction pour chaque attribut, et mettre à jour le
		return 0;
	}
	*/

	//Dis moi si tu galères à comprendre mais normalement ça devrait aller :p 
	//Mais j'pense que c'est quand même nul :trololo:

	/**
	 * Choisit le meilleur attribut en fonction de celui qui génère le gain d'information le plus élevé
	 * @return Attribut
	 */
	private Attribut meilleurAttribut() {
		ArrayList<Attribut> attributs_candidats = this.jeu_de_donnees.attributsCandidats();
		double max_gain = 0;
		
		// Pour chaque attribut
		for (Attribut attribut_candidat : attributs_candidats) {
			// Si le nombre d'exemples est strictement supérieur au maximum lu, le remplacer
			// Sinon, supprimer l'attribut (en local)
			if (this.gain(attribut_candidat) > max_gain) {
				max_gain = this.gain(attribut_candidat);
			} else {
				attributs_candidats.remove(attributs_candidats);
			}
		}
		
		return attributs_candidats.get(0);

		/*int plus = 0, moins = 0;
		String meilleur_attribut;
		int max;
		Iterator<String> it = attributs_candidats.iterator();
		while(it.hasNext()) {
			String s = it.toString();
			if(s == valeur_attribut) {
				plus++;
			} else moins++;

			if(gain(s) > max) {
				meilleur_attribut = s;
				max = gain(attributs_candidats, s);
			}
		}*/
		

		// ou alors au pif !

		// return attributs_candidats.get( (int) Math.random() * attributs_candidats.size() );

		// ou alors le first ! // static Noeud.HASARD Noeud.PREMIER

		// return attributs_candidats.get(0);
	}

	/**
	 * Calcule le gain d'information généré par un attribut donné en paramètre
	 * Le gain se calcul avec les valeurs possibles de l'attribut donné en paramètre
	 * L'entropie de chaque jeu de données fils (jeu de données généré par chaque valeur de l'attribut) est soustraite à celle du jeu de données du père
	 * @return double
	 */
	private double gain(Attribut attribut) {
		double gain = this.jeu_de_donnees.entropie();

		int nombre_exemples_this = this.jeu_de_donnees.nombreExemples();
		ArrayList<String> valeurs_possibles = attribut.valeurs();
		
		// Pour chaque valeur de l'attribut donné en paramètre (donc pour chaque potentiels noeuds fils)
		for (String valeur_possible : valeurs_possibles) {
			// Créer un jeu de données sélectionnant les exemples où l'attribut vaut cette valeur
			JeuDonnees donnees_fils = new JeuDonnees(this.jeu_de_donnees.selectionnerExemplesOu(attribut, valeur_possible));
			// Soustraire au gain du jeu de données père le gain de ce jeu de données fils
			gain -= (double) donnees_fils.nombreExemples() / (double) nombre_exemples_this * donnees_fils.entropie();
		}

		return gain;
	}

	public String toString() {
		String res = "\n----- NOEUD -----\n";

		res += this.jeu_de_donnees + "\n";

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			res += fils + "\n";
		}
		
		return res;
	}

	public String toTree(int level) {
		String res = "";
		String margin_top = "";
		String margin_left = "";
		int niveau = level;

		while (niveau > 0) {
			margin_top += " |  ";
			if (niveau > 1) {
				margin_left += " |  ";
			} else {
				margin_left += " |__";
			}
			--niveau;
		}

		String margin = margin_top + "\n" + margin_left;
		
		res += margin + "[" + this.nom + "] " + this.jeu_de_donnees.valeursClasseExemples();

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			// Affiche le noeud en augmentant le niveau
			res += "\n" + fils.toTree(level + 1);			
		}

		return res;
	}

}
