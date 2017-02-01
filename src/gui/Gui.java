package gui;

import java.awt.BorderLayout; // https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Gui extends JFrame {

	protected JTextArea arbre; // zone de texte contenant l'arbre de décision construit
	protected JTextArea modele; // zone de texte contenant le modèle (ensemble de règles)
	protected JTextArea caracteristiques; // zone de texte contenant les caractéristiques de l'arbre
	protected JTextArea statistiques; // zone de texte contenant les statistiques de l'arbre
	protected JTextField fichier_jeu_apprentissage; // nom du fichier contenant le jeu d'apprentissage
	protected JTextField fichier_jeu_validation; // nom du fichier contenant le jeu de validation
	protected JComboBox<String> meilleurAttribut; // menu déroulant pour sélectionner la façon dont est choisi du meilleur attribut
	protected Checkbox elaguer; // case à cocher pour élaguer l'arbre de décision
	protected JTextField coefficient_v; // coefficient v pour l'élagage
	protected JLabel etat; // état du moteur

	public Gui() {
		super("Construction et élagage d'un arbre de décision");

		// Variables prêtes à être utilisées
		JPanel panneau = null;
		JScrollPane scrolleur = null;
		GridLayout grille = null;
		JLabel texte = null;
		JButton bouton = null;
		GridBagConstraints contrainte = null; // https://openclassrooms.com/courses/apprenez-a-programmer-en-java/positionner-des-boutons#/id/r-2183149

		// Centre de la fenêtre : des onglets pour l'arbre et le modèle
		JTabbedPane ongletsArbreModele = new JTabbedPane();
		ongletsArbreModele.setPreferredSize(new Dimension(700, 0)); // width height, 0 pour "auto"
		this.add(BorderLayout.CENTER, ongletsArbreModele);

			// Zone d'affichage de l'arbre dans un scrolleur dans un onglet
			this.arbre = new JTextArea();
			this.arbre.setEditable(false);
			this.arbre.setMargin(new Insets(10, 10, 10, 10));
			scrolleur = new JScrollPane(this.arbre);
			ongletsArbreModele.addTab("Arbre", scrolleur);

			// Zone d'affichage du modèle dans un scrolleur dans un onglet
			this.modele = new JTextArea();
			this.modele.setEditable(false);
			this.modele.setMargin(new Insets(10, 10, 10, 10));
			scrolleur = new JScrollPane(this.modele);
			ongletsArbreModele.addTab("Modèle", scrolleur);

		// Droite de la fenêtre : un panneau formaté par une grille de 2 lignes x 1 colonne pour les caractériques et les statistiques
		grille = new GridLayout(2, 1);
		JPanel caracStats = new JPanel(grille);
		// caracStats.setPreferredSize(new Dimension(300, 0)); // width height, 0 pour "auto"
		this.add(BorderLayout.EAST, caracStats);

			// Zone d'affichage des caractéristiques de l'arbre dans un scrolleur dans un panneau dans une case de la grille de droite
			panneau = new JPanel();
			panneau.setBorder(BorderFactory.createTitledBorder("Caractéristiques"));
			this.caracteristiques = new JTextArea();
			this.caracteristiques.setEditable(false);
			this.caracteristiques.setMargin(new Insets(5, 5, 5, 5));
			scrolleur = new JScrollPane(this.caracteristiques);
			scrolleur.setPreferredSize(new Dimension(300, 400));
			panneau.add(scrolleur);
			caracStats.add(panneau);
			
			// Zone d'affichage des statistiques de l'arbre dans un scrolleur dans un panneau dans une case de la grille de droite
			panneau = new JPanel();
			panneau.setBorder(BorderFactory.createTitledBorder("Statistiques"));
			this.statistiques = new JTextArea();
			this.statistiques.setEditable(false);
			this.statistiques.setMargin(new Insets(5, 5, 5, 5));
			scrolleur = new JScrollPane(this.statistiques);
			scrolleur.setPreferredSize(new Dimension(300, 400));
			panneau.add(scrolleur);
			caracStats.add(panneau);

		// Gauche de la fenêtre : un panneau formaté par une grille de x lignes x 1 colonne pour le panneau des options de construction, le panneau des options d'élagage et le bouton de construction de l'arbre de décision
		grille = new GridLayout(0, 1);
		JPanel constructionElagage = new JPanel(grille);
		this.add(BorderLayout.WEST, constructionElagage);

			// Panneau des options de construction dans une case de la grille de gauche
			JPanel optionsConstruction = new JPanel(new GridBagLayout());
			optionsConstruction.setBorder(BorderFactory.createTitledBorder("Construction"));
			constructionElagage.add(optionsConstruction);

			contrainte = new GridBagConstraints();
			contrainte.insets = new Insets(10, 10, 10, 10);
			contrainte.fill = GridBagConstraints.BOTH;

				// Bouton pour sélectionner le jeu d'apprentissage dans les options
				texte = new JLabel("Jeu d'apprentissage");
				contrainte.gridx = 0;
				contrainte.gridy = 0;
				optionsConstruction.add(texte, contrainte);
				bouton = new JButton("Ouvrir");
				bouton.addActionListener(new SelectionnerFichier(this, "apprentissage"));
				contrainte.gridx = 1;
				optionsConstruction.add(bouton, contrainte);
				this.fichier_jeu_apprentissage = new JTextField("Aucun fichier sélectionné");
				this.fichier_jeu_apprentissage.setEditable(false);
				this.fichier_jeu_apprentissage.setMargin(new Insets(5, 5, 5, 5));
				this.fichier_jeu_apprentissage.setHorizontalAlignment(JTextField.CENTER);
				this.fichier_jeu_apprentissage.setPreferredSize(this.fichier_jeu_apprentissage.getPreferredSize());
				contrainte.gridx = 0;
				contrainte.gridy = 1;
				contrainte.gridwidth = 2;
				optionsConstruction.add(this.fichier_jeu_apprentissage, contrainte);

				// Menu déroulant pour sélectionner la façon dont est choisi le meilleur attribut
				texte = new JLabel("Choix du meilleur attribut");
				contrainte.gridx = 0;
				contrainte.gridy = 2;
				optionsConstruction.add(texte, contrainte);
				String[] choixMeilleurAttribut = {"Le premier des candidats", "Au hasard parmi les candidats", "Gain d'information maximum"};
				this.meilleurAttribut = new JComboBox<String>(choixMeilleurAttribut);
				contrainte.gridx = 0;
				contrainte.gridy = 3;
				optionsConstruction.add(this.meilleurAttribut, contrainte);

			// Panneau des options d'élagage dans une case de la grille de droite
			JPanel optionsElagage = new JPanel(new GridBagLayout());
			optionsElagage.setBorder(BorderFactory.createTitledBorder("Élagage"));
			constructionElagage.add(optionsElagage);
			
			contrainte = new GridBagConstraints();
			contrainte.insets = new Insets(10, 10, 10, 10);
			contrainte.fill = GridBagConstraints.BOTH;

				// Case à cocher pour élaguer l'arbre de décision
				contrainte.gridx = 0;
				contrainte.gridy = 0;
				contrainte.gridwidth = 2;
				this.elaguer = new Checkbox(" Élaguer l'arbre de décision            ");
				this.elaguer.setState(true);
				// action event dessus pour changer letat de laffichage de l'input du coeff v
				optionsElagage.add(this.elaguer, contrainte);

				// Bouton pour sélectionner le jeu de validation dans les options
				texte = new JLabel("Jeu de validation");
				contrainte.gridy = 1;
				contrainte.gridwidth = 1;
				optionsElagage.add(texte, contrainte);
				bouton = new JButton("Ouvrir");
				bouton.addActionListener(new SelectionnerFichier(this, "validation"));
				contrainte.gridx = 1;
				optionsElagage.add(bouton, contrainte);
				this.fichier_jeu_validation = new JTextField("Aucun fichier sélectionné");
				this.fichier_jeu_validation.setEditable(false);
				this.fichier_jeu_validation.setMargin(new Insets(5, 5, 5, 5));
				this.fichier_jeu_validation.setHorizontalAlignment(JTextField.CENTER);
				this.fichier_jeu_validation.setPreferredSize(this.fichier_jeu_validation.getPreferredSize());
				contrainte.gridx = 0;
				contrainte.gridy = 2;
				contrainte.gridwidth = 2;
				optionsElagage.add(this.fichier_jeu_validation, contrainte);
				
				// Champ de texte pour le coefficient V pour l'élagage
				texte = new JLabel("Coefficient V");
				contrainte.gridx = 0;
				contrainte.gridy = 3;
				optionsElagage.add(texte, contrainte);
				this.coefficient_v = new JTextField();
				this.coefficient_v.setMargin(new Insets(5, 5, 5, 5));
				this.coefficient_v.setHorizontalAlignment(JTextField.CENTER);
				contrainte.gridx = 1;
				optionsElagage.add(this.coefficient_v, contrainte);
				
			constructionElagage.add(new JLabel());

			// Bouton de construction de l'arbre de décision dans la grille de gauche
			JButton construction = new JButton("Construire l'arbre de décision");
			construction.addActionListener(new ConstruireArbre(this));
			constructionElagage.add(construction);

		// Bas de la fenêtre : état du moteur (prêt, en construction de l'arbre, du modèle, ...)
		this.etat = new JLabel("Prêt");
		this.add(BorderLayout.SOUTH, this.etat);
	}

	public String jeuApprentissage() {
		return this.fichier_jeu_apprentissage.getText();
	}

	public String jeuValidation() {
		return this.fichier_jeu_validation.getText();
	}

	public String choixDuMeilleurAttribut() {
		return (String) this.meilleurAttribut.getSelectedItem();
	}

	public String coefficientV() {
		return this.coefficient_v.getText();
	}

	public boolean elaguer() {
		return this.elaguer.getState();
	}

	/**
	 * Affiche et place la fenêtre dans l'environnement
	 */
	public void afficher() {
		this.pack();
		this.setVisible(true);
		this.setLocation(350, 100);
	}

	public void afficherArbre(String arbre) {
		this.arbre.setText(arbre);
	}

	public void afficherModele(String modele) {
		this.modele.setText(modele);
	}

	public void afficherCaracteristiques(String caracteristiques) {
		this.caracteristiques.setText(caracteristiques);
	}

	public void afficherStatistiques(String statistiques) {
		this.statistiques.setText(statistiques);
	}

	public void afficherFichierJeuApprentissage(String chemin_fichier) {
		this.fichier_jeu_apprentissage.setText(chemin_fichier);
	}

	public void afficherFichierJeuValidation(String chemin_fichier) {
		this.fichier_jeu_validation.setText(chemin_fichier);
	}

	public void afficherEtat(String etat) {
		this.etat.setText(etat);
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.afficher();
	}

}