package arbre;

import java.util.ArrayList;
import java.util.Iterator;

import donnees.JeuDonnees;
import jdk.nashorn.internal.codegen.ClassEmitter;
import donnees.Attribut;
import modele.Regle;

public class Noeud /*extends Thread*/ {

	protected Arbre arbre;
	protected Noeud noeud_pere;
	protected JeuDonnees jeu_de_donnees;
	protected ArrayList<Noeud> noeuds_fils;

	/**
	 * Construit un noeud à partir de l'arbre auquel il appartient, de son noeud père et d'un jeu de données
	 * @param donnees
	 */
	public Noeud(Arbre arbre, Noeud pere, JeuDonnees donnees) {
		this.arbre = arbre;
		this.noeud_pere = pere;
		this.jeu_de_donnees = donnees;
		this.noeuds_fils = new ArrayList<Noeud>();
	}

	public Regle genererRegle() {
		Regle r = new Regle();

		// Pour chaque attribut du jeu de données
		for (Attribut attribut : this.jeu_de_donnees.attributs()) {
			// Ajouter à la règle l'attribut et sa valeur en tant que condition s'il n'a qu'une valeur possible et si ce n'est pas l'attribut classe
			if (attribut.valeurs().size() == 1 && !attribut.equals(this.jeu_de_donnees.attributClasse())) {
				r.ajouterCondition(attribut.nom(), attribut.valeurs().get(0));
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
				Noeud noeud_fils = new Noeud(this.arbre, this, donnees_fils); // peut contenir 0 exemples dans son jeu de données, la règle ainsi générée sera "ALORS class = "
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

	//Dis moi si tu galères à comprendre mais normalement ça devrait aller :p 
	//Mais j'pense que c'est quand même nul :trololo:
	private Attribut meilleurAttribut() {
		ArrayList<Attribut> attributs_candidats = this.jeu_de_donnees.attributsCandidats();
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
		
		// appeller la méthode de gain pr chaque attribut, mettre le meilleur resultat dans une variable
		// retourner la variable

		// ou alors au pif !

		// return attributs_candidats.get( (int) Math.random() * attributs_candidats.size() );

		// ou alors le first ! // static Noeud.HASARD Noeud.PREMIER

		return attributs_candidats.get(0);
	}

	private int gain(ArrayList<String> attributs_candidats, String attribut) {

		/*	
		-∑ fs log2 fs;
		 s
		*/

		// float gain_potentiel = entropie(appeller la méthode pour chaque attribut candidat);

		return 0;
	}

	/**
	 * Calcule l'entropie du noeud pour un attribut donné en paramètre
	 * L'entropie d'un noeud est égale à l'entropie du noeud + celle de ses potentiels fils générés à partir d'un attribut
	 * @param attribut
	 */
	private float entropie(Attribut attribut) {
		/*float proportion de chaque classe
		-(9/14) * log2(9/14) - (5/14) * log2(5/14)*/

		/*
		ent = entropie de ce noeud
		Pour chaque valeur de l'attribut donné en paramètre (donc des potentiels noeuds fils)
			entropie = 0
			Pour chaque valeur s de l'attribut de classe
				Calculer fs, la proportion d'exemples qui ont pour classe s
				Multiplier fs par log2(fs)
				Soustraire le résultat à la variable entropie
			ent -= prop * entropie
			http://moodle.univ-angers.fr/pluginfile.php/579146/mod_resource/content/1/ArbreDecision.pdf
		*/

		return 0;
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
		
		res += margin + "NŒUD";

		// Pour chaque noeud fils de ce noeud
		for (Noeud fils : this.noeuds_fils) {
			// Affiche le noeud en augmentant le niveau
			res += "\n" + fils.toTree(level + 1);			
		}

		return res;
	}

}
