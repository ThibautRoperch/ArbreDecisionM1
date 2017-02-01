package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import donnees.JeuDonnees;
import arbre.Arbre;
import modele.Modele;

public class ConstruireArbre implements ActionListener {

	protected Gui interface_graphique;

	public ConstruireArbre(Gui interface_graphique) {
		this.interface_graphique = interface_graphique;
	}

	public void actionPerformed(ActionEvent event) {
		
		// 1. Récupération des fichiers contenant les jeux de données
		
		// Jeu d'apprentissage
		String fichier_jeu_app = interface_graphique.jeuApprentissage();
		// Jeu de test
		String fichier_jeu_test = interface_graphique.jeuValidation();
		
		// 2. Lecture les fichiers, création des jeux de données avec le contenu des fichiers

		this.interface_graphique.afficherEtat("Création des jeux de données avec le contenu des fichiers");
		System.out.println("\n> Création des jeux de données avec le contenu des fichiers\n");
		JeuDonnees jeu_app = new JeuDonnees(fichier_jeu_app);
		JeuDonnees jeu_test = new JeuDonnees(fichier_jeu_test);

		// 3. Construction d'un arbre de décision avec le jeu d'apprentissage

		this.interface_graphique.afficherEtat("Construction de l'arbre de décision avec le jeu d'apprentissage");
		System.out.println("\n> Construction de l'arbre de décision avec le jeu d'apprentissage\n");
		Arbre arbre_decision = new Arbre();
		int choix_meilleur_attribut = 0;
		switch (interface_graphique.choixDuMeilleurAttribut()) {
			case "Le premier":
				choix_meilleur_attribut = Arbre.PREMIER;
				break;
			case "Au hasard":
				choix_meilleur_attribut = Arbre.HASARD;
				break;
			case "Gain d'information":
				choix_meilleur_attribut = Arbre.GAIN_INFORMATION;
				break;
		}
		arbre_decision.construire(jeu_app, choix_meilleur_attribut);
		
		// 4. Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V

		if (interface_graphique.elaguer()) {
			this.interface_graphique.afficherEtat("Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V");
			System.out.println("\n> Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V\n");
			if (!interface_graphique.coefficientV().equals("")){
				arbre_decision.postElaguer(jeu_test, Double.parseDouble(interface_graphique.coefficientV()));
			}
		}

		// 5. Affichage de l'arbre de décision, ses caractéristiques et ses statistiques

		interface_graphique.afficherArbre(arbre_decision.toTree());
		interface_graphique.afficherCaracteristiques(arbre_decision.toCharacteristics());
		interface_graphique.afficherStatistiques(arbre_decision.toStatistics());

		// 6. Génération et affichage du modèle associé à l'arbre de décision

		this.interface_graphique.afficherEtat("Génération du modèle associé à l'arbre de décision");
		System.out.println("\n> Génération du modèle associé à l'arbre de décision\n");
		Modele modele = arbre_decision.genererModele();
		interface_graphique.afficherModele(modele.toString());

		this.interface_graphique.afficherEtat("Prêt");
	}

}
