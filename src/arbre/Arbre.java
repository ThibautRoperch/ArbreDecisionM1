package arbre;

import java.util.ArrayList;

import donnees.JeuDonnees;
import modele.Modele;
import modele.Regle;

public class Arbre {
	
	protected JeuDonnees jeu_apprentissage;
	protected Noeud noeud_racine;
	protected ArrayList<Noeud> feuilles;
	
	public Arbre(JeuDonnees donnees_apprentissage) {
		this.jeu_apprentissage = donnees_apprentissage;
		this.noeud_racine = new Noeud(this, null, "Racine", this.jeu_apprentissage);
		this.feuilles = new ArrayList<Noeud>();
	}

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

	public int hauteur() {
		return this.noeud_racine.nombreDescendances() - 1;
	}

	public void ajouterFeuille(Noeud noeud) {
		this.feuilles.add(noeud); // this pour que les noeuds puissent appeller ajouterFeuille
	}

	public void construire() {
		this.noeud_racine.start();
	}

	public void postElaguer(JeuDonnees donnees_test, int coeff_v) {
		this.noeud_racine.regrouperFils(donnees_test, coeff_v);
	}

	public Modele genererModele() {
		Modele m = new Modele();

		// Pour chaque feuille (noeud pur) de l'arbre
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

		res += "\n\n\n# Caractéristiques de l'arbre\n";

		res += "\nArbre parfait :\t\t" + this.estParfait() + "\n";
		res += "\nNombre de feuilles :\t" + this.feuilles.size() + "\n";
		res += "\nNombre d'exemples :\t" + this.jeu_apprentissage.nombreExemples() + "\n";
		res += "\nHauteur de l'arbre :\t" + this.hauteur() + "\n";
		res += "\nNombre d'attributs :\t" + this.jeu_apprentissage.nombreAttributs() + "\n";
		int surrapprentissage = this.hauteur() * 100 / this.jeu_apprentissage.nombreAttributs();
		res += "\nSurrapprentissage :\t" + surrapprentissage + "%\n";
		int surrapprentissage2 = this.feuilles.size() * 100 / this.jeu_apprentissage.nombreExemples();
		res += "\nSurrapprentissage2 :\t" + surrapprentissage2 + "%\n";


		return res;
	}

	public static void main(String[] args) {
		// Arbre a = new Arbre(new JeuDonnees("jeux/vote.arff"));
		Arbre a = new Arbre(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"));
		a.construire();
		// System.out.println(a);
		// System.out.println(a.toTree());
		// System.out.println(a.genererModele());
		a.postElaguer(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"), 5);
	}

}
