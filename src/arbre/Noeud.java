package arbre;

import java.io.*;
import java.util.*;

import donnees.JeuDonnees;

public class Noeud {

	protected JeuDonnees jeu_de_donnees;

	public Noeud(JeuDonnees donnees) {
		this.jeu_de_donnees = donnees;
	}

	public boolean estPure() {
		return this.jeu_de_donnees.NombreDeClasses() == 1;
	}

	public void run() {
		if (!this.estPure()) {

		}
	}

	public static void main(String[] args) {
		Noeud n = new Noeud();
	}

}
