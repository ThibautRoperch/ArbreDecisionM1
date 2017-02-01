import java.io.*;
import java.util.*;

import donnees.JeuDonnees;
import arbre.Arbre;
import modele.Modele;

public class Main {

	public static void main(String[] args) {
		
		// 1. Récupération des arguments

		// Jeu d'apprentissage
		String fichier_jeu_app = (args.length >= 1) ? args[0] : "";
		// Jeu de test
		String fichier_jeu_test = (args.length >= 2) ? args[1] : "";
		// Coefficient V
		double coeff_v = (args.length >= 3) ? Double.parseDouble(args[2]) : 0;

		// 2. Lecture les fichiers, création des jeux de données avec le contenu des fichiers

		System.out.println("\n> Création des jeux de données avec le contenu des fichiers\n");
		JeuDonnees jeu_app = new JeuDonnees(fichier_jeu_app);
		JeuDonnees jeu_test = new JeuDonnees(fichier_jeu_test);

		// 3. Construction de l'arbre de décision avec le jeu d'apprentissage

		System.out.println("\n> Construction de l'arbre de décision avec le jeu d'apprentissage\n");
		Arbre arbre_decision = new Arbre();
		arbre_decision.construire(jeu_app, Arbre.GAIN_INFORMATION);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("\n# Affichage de l'arbre\n" + arbre_decision.toTree());
		System.out.println("\n\n# Caractéristiques de l'arbre\n" + arbre_decision.toCharacteristics());
		
		// 4. Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V
		
		System.out.println("\n> Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V\n");
		arbre_decision.postElaguer(jeu_test, coeff_v);
		// System.out.println("\n# Affichage de l'arbre\n" + arbre_decision.toTree());
		System.out.println("\n# Caractéristiques de l'arbre\n" + arbre_decision.toCharacteristics());
		System.out.println("\n# Statistiques de l'arbre\n\n" + arbre_decision.toStatistics());

		// 5. Génération du modèle associé à l'arbre de décision

		System.out.println("\n> Génération du modèle associé à l'arbre de décision\n");
		Modele modele = arbre_decision.genererModele();
		System.out.println("\n# Affichage du modèle\n\n" + modele);
		
	}

}