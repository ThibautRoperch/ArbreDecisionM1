package arbre;

import java.io.*;
import java.util.*;

import donnees.JeuDonnees;

public class Noeud extends Thread {

	protected JeuDonnees jeu_de_donnees;
	protected ArrayList<Noeud> enfants;

	public Noeud(JeuDonnees donnees) {
		this.jeu_de_donnees = donnees;
		this.enfants = new ArrayList<Noeud>();
	}

	public void run() {
		if (!this.estPure()) {
			String attribut_choisi = meilleurAttribut();
		}
	}

	public boolean estPure() {
		return this.jeu_de_donnees.nombreDeClassesExemples() == 1;
	}

	private String meilleurAttribut() {
		return new String();
	}

	//calculer le meilleur attribut à mettre en noeud racine, puis remplir les fils. Puis recommencer sur les fils.
	//Calcule le gain pour choisir le meilleur attribut.
	//Prendre le jeu d'apprentissage en paramètre maybe ? 
	private int gain(int positif, int negatif) {
		//return positif*(log(positif/positif+negatif)-log(P/P+N));

		//Appeler la fonction pour chaque attribut, et mettre à jour le
	}

}
