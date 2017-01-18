package donnees;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class LectureFichier {

//ajouter JeuDonnees jeu, en argument (c'est pour les tests qu'il a été enlevé)
    public LectureFichier(String nom_fichier) { // throws IOException 
		String fichier = nom_fichier;
		String lignes_fichier = new String();

		// TEST 
		/* 
		String[] test; 
		String[] res;
		String ligne_test = "bonjour 'bla' 'bla' 'bli' 'blo' 'blu' 'bla' 'bli' \n on continue ici 'lol' 'mdr' bob";
		test = ligne_test.split("\'");
		for(int x=0; x<=ligne_test.length(); x++) {
			System.out.println(test[x]);
			//res[x]=test[x];
		}


		// FIN TEST 
		*/



		
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne_lue;
			
			boolean lire_fichier = ((ligne_lue = br.readLine()) != null) ? true : false;
			//String declaration_section = new String();

			while (lire_fichier) {
				lignes_fichier += "\n" + ligne_lue;
				ligne_lue = br.readLine();
				String[] ligne_split;

				//JE SAIS PAS POURQUOI CA BOUCLE PAS MAIS G PALTEMP
				if(ligne_lue.contains("@attribute")) {
					ligne_split = ligne_lue.split("\'");
					for (int x=1; x<ligne_lue.length(); x++) {
						
						if(!(ligne_split[x].contains("}") || ligne_split[x].contains(",") || ligne_split[x].contains("{"))) {
							//Avant d'ajouter, on vérifie que le caractère n'est pas une , ou { ou ]. 

							//jeu.ajouter(ligne_split[x]);	
							System.out.println(ligne_split[x]);
						} 
					}
				}
				
				
				if (ligne_lue == null) lire_fichier = false;
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	} 

    public static void main (String[] args) {
        LectureFichier l = new LectureFichier("jeux/vote.arff");
    }


	public static void remplirDepuisFichier(JeuDonnees donnees, String nom_fichier) { // static permet d'appeller la fonction sans avoir besoin d'instancier la classe dans une variable
		// Lecture du fichier et remplissage du jeu donné en paramètre
		// donnees.ajouterAttribut(String attribut, ArrayList<String> valeurs)
		// donnees.ajouterExemple(ArrayList<String> exemple)
	}

}