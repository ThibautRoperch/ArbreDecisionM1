package donnees;

import java.io.*;
import java.util.*;

public class LectureFichier {

    public void LectureFichier() throws IOException {
		String ligne = "";
		String fichier = "";
        
		BufferedReader ficTexte;
		try {
			ficTexte = new BufferedReader(new FileReader(new File(fichier)));
			if (ficTexte == null) {
				throw new FileNotFoundException("Fichier non trouvé: "
						+ fichier);
			}
			do {
				ligne = ficTexte.readLine();
				if (ligne != null) {
					System.out.println(ligne);
				}
			} while (ficTexte != null);
			ficTexte.close();
			System.out.println("\n");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}


	public static void remplirDepuisFichier(JeuDonnees donnees, String nom_fichier) { // static permet d'appeller la fonction sans avoir besoin d'instancier la classe dans une variable
		// Lecture du fichier et remplissage du jeu donné en paramètre
		// donnees.ajouterAttribut(String attribut, ArrayList<String> valeurs)
		// donnees.ajouterExemple(ArrayList<String> exemple)
	}
}