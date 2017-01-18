package arbre;

import donnees.JeuDonnees;
import modele.Modele;

public class Arbre {
	
	protected JeuDonnees jeu_apprentissage;
	protected Noeud noeud_racine;
	
	public Arbre(JeuDonnees donnees) {
		this.jeu_apprentissage = donnees;
		this.noeud_racine = new Noeud(this.jeu_apprentissage);
	}

	public void construire() {
		if (this.jeu_apprentissage.estBienConstruit()) {
			this.noeud_racine.start();
		}
	}

	public void postElaguer(JeuDonnees donnees, int coeff_v) {

	}

	public Modele genererModele() {
		return new Modele();
	}

	public String toString() {
		return jeu_apprentissage + "\n" + noeud_racine;
	}

	public static void main(String[] args) {
		Arbre a = new Arbre(new JeuDonnees("../jeux/vote.arff"));
		a.construire();
		System.out.println(a);
		// a.postElaguer(jeu 2, coeef v)
	}

}
