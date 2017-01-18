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

		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne_lue;
			
			boolean lire_fichier = ((ligne_lue = br.readLine()) != null) ? true : false;

			while (lire_fichier) {
				lignes_fichier += "\n" + ligne_lue;		//Pas utile je crois
				ligne_lue = br.readLine();
				String[] ligne_split;


				if(ligne_lue.contains("@attribute")) {
					ligne_split = ligne_lue.split("\'");
					for (int x=1; x<ligne_split.length; x++) {	
						//Avant d'ajouter, on vérifie que le caractère n'est pas une , ou { ou ]. 
						if(!(ligne_split[x].contains("}") || ligne_split[x].contains(",") || ligne_split[x].contains("{"))) {

							//jeu.ajouter(ligne_split[x]);	
							System.out.println(ligne_split[x]);
						}
					}
				}

				//On détecte quand commence la déclaration de data puis on lit ligne par ligne pour alimenter le jeu de données 
				if(ligne_lue.contains("@data")) {
					while(lire_fichier) {
						//Passage à la ligne suivante pour éviter la ligne @data
						ligne_lue = br.readLine();
						
						//On remplace les ' par "" (rien)
						ligne_lue = ligne_lue.replace("'", "");
						//Puis on split par les virgules ,
						ligne_split = ligne_lue.split(",");
						//On parcourt la ligne pour qui contient une ligne de data
						for(int x=1; x<ligne_split.length; x++) {
							//ALIMENTER LES DATA ICI 
							System.out.println(ligne_split[x]);
						}

						//Si on arrive à la fin du fichier, on stop la lecture
						if (ligne_lue == null) lire_fichier = false;
					}
					
				}				
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