package arbre;

import java.io.*;

import donnees.JeuDonnees;
import modele.Modele;

public class Arbre {
	
	protected JeuDonnees jeu_apprentissage;
	protected Noeud noeud_racine;
	
	public Arbre(JeuDonnees donnees) {
		this.jeu_apprentissage = donnees;
		this.noeud_racine = new Noeud(this.jeu_apprentissage);
	}

	public void construire(int k) {
		this.noeud_racine.start();
	}

	public void postElaguer(JeuDonnees donnees) {

	}

	public Modele genererModele() {
		return new Modele();
	}

	public static void main(String[] args) {
		//Arbre a = new Arbre(new JeuDonnees("../jeux/vote.arff"));
		//a.genererArbre();
	}

}
