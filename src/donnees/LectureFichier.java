package donnees;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class LectureFichier {

	public static void lectureFichier(JeuDonnees donnees, String nom_fichier) { // throws IOException 
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
				lignes_fichier += "\n" + ligne_lue;
				ligne_lue = br.readLine();
				String[] ligne_split;


				if(ligne_lue.contains("@attribute")) {
					// ligne_split = ligne_lue.split("\'");
					//On remplace les ' et " " par ,
					ligne_lue = ligne_lue.replace("'", "");
					ligne_lue = ligne_lue.replace(" ", ",");
					//On remplace les { par , et } par "" (rien)
					ligne_lue = ligne_lue.replace("{", ",");
					ligne_lue = ligne_lue.replace("}", "");
					//On remplace les doubles ou + virgules ainsi générées par une simple virgule
					while(ligne_lue.contains(",,")) ligne_lue = ligne_lue.replace(",,", ",");
					//Puis on split par les virgules
					ligne_split = ligne_lue.split(",");
					ArrayList<String> valeurs = new ArrayList<String>();	//ArrayList servant à contenir les valeurs possibles de l'attribut
					for (int x=2; x<ligne_split.length; x++) {		//2 = TEST
						//Avant d'ajouter, on vérifie que le caractère n'est pas une , ou { ou ]. 
						// if(!(ligne_split[x].contains("}") || ligne_split[x].contains(",") || ligne_split[x].contains("{"))) {							
							valeurs.add(ligne_split[x]);
						// }
					}
					//ligne_split[1] = le nom de l'attribut
					// System.out.println("> " + ligne_split[1] + " = " + valeurs);
					donnees.ajouterAttribut(ligne_split[1], valeurs);	
				}

				//On détecte quand commence la déclaration de data puis on lit ligne par ligne pour alimenter le jeu de données 
				if(ligne_lue.contains("@data")) {
					//Passage à la ligne suivante pour éviter la ligne @data
					ligne_lue = br.readLine();
					while(lire_fichier) {
						if(!ligne_lue.contains("%")) {
							//On remplace les ' par "" (rien)
							ligne_lue = ligne_lue.replace("'", "");
							//Puis on split par les virgules ,
							ligne_split = ligne_lue.split(",");
							//On parcourt la ligne pour qui contient une ligne de data
							ArrayList<String> donnee_ligne = new ArrayList<String>();
							for(int x=0; x<ligne_split.length; x++) {
								//ALIMENTER LES DATA ICI 
								donnee_ligne.add(ligne_split[x]);
							}
							
							// System.out.println(donnee_ligne);
							donnees.ajouterExemple(donnee_ligne);
						}
						ligne_lue = br.readLine();
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
		LectureFichier.lectureFichier(new JeuDonnees(), "jeux/vote.arff");
		// LectureFichier.lectureFichier(new JeuDonnees(), "jeux/Jeuxsimples/weather.nominal.arff");
	}

}
