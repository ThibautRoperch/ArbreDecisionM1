package arbre;

import java.util.ArrayList;

import donnees.JeuDonnees;
import modele.Modele;
import modele.Regle;

public class Arbre {
	
	protected JeuDonnees jeu_apprentissage;
	protected Noeud noeud_racine;
	protected ArrayList<Noeud> feuilles;
	
	public Arbre(JeuDonnees donnees) {
		this.jeu_apprentissage = donnees;
		this.noeud_racine = new Noeud(this, null, this.jeu_apprentissage);
		this.feuilles = new ArrayList<Noeud>();
	}

	public void ajouterFeuille(Noeud noeud) {
		this.feuilles.add(noeud); // this pour que les noeuds puissent appeller ajouterFeuille
	}

	public void construire() {
		this.noeud_racine.start();
	}

	public void postElaguer(JeuDonnees donnees, int coeff_v) {
		// remplir l'arbre avec un jeu de test
		// parcourir de la racine vers les noeuds fils en profondeur d'abord
		// si le noeud satisfait le coeff v, reunir avec les fils ?

		// enlever la liste des feuilles et l'ajout d'une feuille dans la liste (méthode start de Noeud)
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
		return this.noeud_racine.toTree(0);
	}

	public static void main(String[] args) {
		// Arbre a = new Arbre(new JeuDonnees("jeux/vote.arff"));
		Arbre a = new Arbre(new JeuDonnees("jeux/Jeuxsimples/weather.nominal.arff"));
		a.construire();
		System.out.println("\n== Modèle de l'arbre ==\n");
		System.out.println(a.genererModele());
		System.out.println("\n== Noeuds de l'arbre ==\n");
		// System.out.println(a);
		System.out.println(a.toTree());
		// a.postElaguer(jeu 2, coeef v)
	}

}
