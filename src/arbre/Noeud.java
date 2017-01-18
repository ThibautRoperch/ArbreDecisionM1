package arbre;

import java.util.ArrayList;

import donnees.JeuDonnees;

public class Noeud extends Thread {

	protected JeuDonnees jeu_de_donnees;
	protected ArrayList<Noeud> noeuds_fils;

	/**
	 * Construit un noeud à partir d'un jeu de données
	 * @param donnees
	 */
	public Noeud(JeuDonnees donnees) {
		this.jeu_de_donnees = donnees;
		this.noeuds_fils = new ArrayList<Noeud>();
	}

	/**
	 * Créé des noeuds fils pour chaque valeur de l'attribut choisi
	 * Un noeud fils a un son propre jeu de données et son propre modèle, tous deux créés à partir de ceux de this
	 * Si le noeud est pure ou s'il n'a pas d'attributs candidats dans son jeu de données, alors c'est une feuille
	 */
	public void run() {
		// Si le noeud n'est pas pure et s'il y a des attributs à évaluer, créer des noeuds fils
		if (!this.estPure() && this.jeu_de_donnees.attributsCandidats().size() > 0) {
			// Choisir un attribut en fonction du gain d'information de chacune de ses valeurs
			String attribut_choisi = meilleurAttribut();
			// Récupérer auprès du jeu de données les valeurs possibles pour l'attribut choisi
			ArrayList<String> valeurs_possibles = jeu_de_donnees.valeursPossibles(attribut_choisi);
			System.out.println(valeurs_possibles);
			// Créer autant de noeuds fils qu'il y a de valeurs pour l'attribut choisi
			for (String valeur_possible : valeurs_possibles) {
				// Créer un nouveau jeu de données pour le fils, pour cela :
				// Partir du jeu de données du noeud actuel et enlever les exemples où attribut_choisi != valeur
				// Mettre dans le modèle du jeu de données l'attribut et la valeur utilisés par le noeud
				JeuDonnees donnees_fils = new JeuDonnees(	this.jeu_de_donnees.attributs(),
															this.jeu_de_donnees.selectionnerExemplesOu(attribut_choisi, valeur_possible)	);
				donnees_fils.enregistrerAttribut(attribut_choisi, valeur_possible);
				// Créer à partir des données, enregistrer et lancer le noeud fils
				Noeud noeud_fils = new Noeud(donnees_fils);
				noeuds_fils.add(noeud_fils);
				noeud_fils.start();
			}
		}
		// Sinon, le noeud est une feuille, enregistrer la classe dominante des exemples en tant que conclusion dans le modèle du jeu de données
		else {
			String attribut_classe = this.jeu_de_donnees.attributClasse();
			String valeur_classe = this.jeu_de_donnees.classeDominante();
			this.jeu_de_donnees.enregistrerAttribut(attribut_classe, valeur_classe);
		}
	}

	/**
	 * Retourne true si la feuille est pure
	 * Une feuille est pure si son jeu de données ne contient que des exemples de la même classe
	 * @return boolean
	 */
	public boolean estPure() {
		return this.jeu_de_donnees.valeursClasseExemples().size() == 1;
	}

	private String meilleurAttribut() {
		ArrayList<String> attributs_candidats = this.jeu_de_donnees.attributsCandidats();

		// faire d truc
		// compter les + et les -
		// appeller la méthode de gain pr chaque attribut, mettre le meilleur resultat dans une variable
		// retourner la variable

		return "crime";
	}

	private int gain(String attribut) {
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
		String res = "\n----- NOEUD -----\n" + this.jeu_de_donnees + "\n";
		for (Noeud fils : this.noeuds_fils) {
			res += fils;
		}
		return res;
	}

}
