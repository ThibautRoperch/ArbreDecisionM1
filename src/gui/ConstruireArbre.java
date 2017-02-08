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

		this.interface_graphique.nettoyer();
		
		// 1. Récupération des fichiers contenant les jeux de données
		
		// Jeu d'apprentissage
		String fichier_jeu_app = interface_graphique.jeuApprentissage();
		String fichier_jeu_test = "";
		// Jeu de test
		if (this.interface_graphique.elaguer()) {
			fichier_jeu_test = interface_graphique.jeuValidation();
		}
		
		// 2. Lecture les fichiers, création des jeux de données avec le contenu des fichiers

		this.interface_graphique.afficherEtat("Création des jeux de données avec le contenu des fichiers");
		JeuDonnees jeu_app = new JeuDonnees(fichier_jeu_app);
		JeuDonnees jeu_test = new JeuDonnees();
		if (this.interface_graphique.elaguer()) {
			jeu_test = new JeuDonnees(fichier_jeu_test);
		}

		// 3. Construction d'un arbre de décision avec le jeu d'apprentissage

		this.interface_graphique.afficherEtat("Construction de l'arbre de décision avec le jeu d'apprentissage");
		Arbre arbre_decision = new Arbre();
		if (jeu_app.estBienConstruit()) {
			int choix_meilleur_attribut = 0;
			switch (this.interface_graphique.choixDuMeilleurAttribut()) {
				case "Le premier des candidats":
					choix_meilleur_attribut = Arbre.PREMIER;
					break;
				case "Au hasard parmi les candidats":
					choix_meilleur_attribut = Arbre.HASARD;
					break;
				case "Gain d'information maximum":
					choix_meilleur_attribut = Arbre.GAIN_INFORMATION;
					break;
			}
			arbre_decision.construire(jeu_app, choix_meilleur_attribut);
		} else {
			this.interface_graphique.afficherErreur("Le jeu de d'apprentissage n'a pas d'attribut, impossible de construire l'arbre");
		}
		
		// 4. Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V

		if (this.interface_graphique.elaguer()) {
			this.interface_graphique.afficherEtat("Post-élagage de l'arbre de décision avec le jeu de test et le coefficient V");
			if (jeu_test.estBienConstruit() && jeu_test.estConstruitComme(jeu_app)) {
				if (!this.interface_graphique.coefficientV().equals("")) {
					arbre_decision.postElaguer(jeu_test, Double.parseDouble(this.interface_graphique.coefficientV()));
				} else {
					this.interface_graphique.afficherErreur("Le coefficient V doit être un nombre non null, impossible de post-élaguer l'arbre");
				}
			} else {
				this.interface_graphique.afficherErreur("Le jeu de validation n'a pas d'attribut ou n'a pas les mêmes attributs que le jeu d'apprentissage, impossible de post-élaguer l'arbre");
			}
		}

		// 5. Génération et affichage du modèle associé à l'arbre de décision

		this.interface_graphique.afficherEtat("Génération du modèle associé à l'arbre de décision");
		Modele modele = arbre_decision.genererModele();
		this.interface_graphique.afficherModele(modele.toString());

		// 6. Affichage de l'arbre de décision, ses caractéristiques et ses statistiques

		this.interface_graphique.afficherArbre(arbre_decision.toTree(this.interface_graphique.afficherNoeudsVides()));
		this.interface_graphique.afficherCaracteristiques(arbre_decision.toCharacteristics());
		this.interface_graphique.afficherStatistiques(arbre_decision.toStatistics());

		this.interface_graphique.afficherEtat("Prêt");

	}

}
