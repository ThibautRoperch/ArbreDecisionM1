package donnees;

import java.io.*;
import java.util.*;

public class JeuDonnees {
    
	protected int nb_attributs;
	protected int nb_exemples;
	protected String[][] donnees; // [attributs][exemples]

	public JeuDonnees() {
		//this.donnees = new String()[][];
	}

	public int JeuDonnees(String nom_fichier) {
		
	}

	public void setSize(int attributs, int exemples) {

	}

	public void ajouterAttribut(String attribut) {

	}

	public void ajouterExemple(String exemple) {

	}

	public int NombreDeClasses() {
		List<Integer> myList = new ArrayList<Integer>();
myList.add(5);
myList.add(7);

		String[] exemples_differents = new String()[];
		// Pour chaque exemple (sélection de la dernière colonne)
		for (String[] exemples : this.donnees[nb_attributs-1]) {
			sort(exemples);
		}
	}

}
