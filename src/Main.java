import java.io.*;
import java.util.*;

import arbre.Arbre;
import donnees.JeuDonnees;
import modele.Modele;

public class Main {

	public static void main(String[] args) {
		
		// 1. Récupération des arguments

		// Jeu d'apprentissage
		String fichier_jeu_app = (args.length >= 1) ? args[0] : "";
		// Jeu de test
		String fichier_jeu_test = (args.length >= 2) ? args[1] : "";
		// k
		int k = (args.length >= 3) ? Integer.parseInt(args[2]) : 0;

		// 2. Lecture les fichiers, création de deux instances de type JeuDonnees avec le contenu des fichiers

		JeuDonnees jeu_app = new JeuDonnees(fichier_jeu_app);
		JeuDonnees jeu_test = new JeuDonnees(fichier_jeu_test);

		// 3. Construction d'un arbre de décision (instance de la classe Arbre) avec le jeu d'apprentissage

		Arbre arbre_decision = new Arbre(jeu_app);
		arbre_decision.construire(k); // c'est là qu'il faut donner le coeff k ?
		arbre_decision.postElaguer(jeu_test); // c'est là qu'il faut post-élaguer ?

		// 4. Construction du modèle (instance de la classe Modele) associé à l'arbre de décision

		Modele modele = arbre_decision.genererModele();

		// 5. Test du modèle avec le jeu de test				Un modèle est un ensemble de règles ou d'hypothèses
		modele.tester(jeu_test); // String resultat_test = modele.tester(jeu_test); ?
	}

}