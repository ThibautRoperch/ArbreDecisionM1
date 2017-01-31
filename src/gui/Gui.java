package gui;

import java.awt.BorderLayout; // https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
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
	protected JButton fichier_jeu_apprentissage; // nom du fichier contenant le jeu d'apprentissage
	protected JLabel fichier_jeu_validation; // nom du fichier contenant le jeu de validation
	protected JComboBox<String> meilleurAttribut; // menu déroulant pour sélectionner la façon dont est choisi du meilleur attribut
	protected Checkbox elaguer; // case à cocher pour élaguer l'arbre de décision
	protected JTextField coefficient_v; // coefficient v pour l'élagage

	public Gui() {
		super("Construction et élagage d'un arbre de décision");

		// Variables prêtes à être utilisées
		JPanel panneau = null;
		JScrollPane scrolleur = null;
		GridLayout grille = null;
		JLabel texte = null;
		JButton bouton = null;

		// Centre de la fenêtre : des onglets pour l'arbre et le modèle
		JTabbedPane ongletsArbreModele = new JTabbedPane();
		ongletsArbreModele.setPreferredSize(new Dimension(700, 800)); // width height
		this.add(BorderLayout.CENTER, ongletsArbreModele);

			// Zone d'affichage de l'arbre dans un scrolleur dans un onglet
			this.arbre = new JTextArea();
			this.arbre.setEditable(false);
			this.arbre.setMargin(new Insets(10, 10, 10, 10));
			scrolleur = new JScrollPane(this.arbre);
			ongletsArbreModele.addTab("Arbre", this.arbre);

			// Zone d'affichage du modèle dans un scrolleur dans un onglet
			this.modele = new JTextArea();
			this.modele.setEditable(false);
			this.modele.setMargin(new Insets(10, 10, 10, 10));
			scrolleur = new JScrollPane(this.modele);
			ongletsArbreModele.addTab("Modèle", this.modele);

		// Droite de la fenêtre : un panneau formaté par une grille de 2 lignes x 1 colonne pour les caractériques et les statistiques
		grille = new GridLayout(2, 1);
		JPanel caracStats = new JPanel(grille);
		caracStats.setPreferredSize(new Dimension(150, 0)); // width height, 0 pour "auto"
		this.add(BorderLayout.EAST, caracStats);

			// Zone d'affichage des caractéristiques de l'arbre dans une case de la grille de droite
			this.caracteristiques = new JTextArea();
			this.caracteristiques.setEditable(false);
			this.caracteristiques.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
			caracStats.add(this.caracteristiques);

			// Zone d'affichage des statistiques de l'arbre dans une case de la grille de droite
			this.statistiques = new JTextArea();
			this.statistiques.setEditable(false);
			this.statistiques.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
			caracStats.add(this.statistiques);

		// Gauche de la fenêtre : un panneau formaté par une grille de 2 lignes x 1 colonne pour le panneau des options et le bouton de construction de l'arbre de décision
		grille = new GridLayout(3, 1);
		JPanel optionsConstruction = new JPanel(grille);
		optionsConstruction.setPreferredSize(new Dimension(500, 0)); // width height, 0 pour "auto"
		this.add(BorderLayout.WEST, optionsConstruction);

			// Panneau des options formaté par une grille de x lignes x 2 colonnes pour les options dans la grille de gauche
			grille = new GridLayout(0, 2);
			JPanel options = new JPanel(grille);
			options.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
			optionsConstruction.add(options);

				// Bouton pour sélectionner le jeu d'apprentissage dans les options
				texte = new JLabel("Jeu d'apprentissage");
				options.add(texte);
				this.fichier_jeu_apprentissage = new JButton("Aucun fichier sélectionné");
				this.fichier_jeu_apprentissage.addActionListener(new SelectionnerFichier(this, "apprentissage"));
				options.add(fichier_jeu_apprentissage);

				// Bouton pour sélectionner le jeu de validation dans les options
				texte = new JLabel("Jeu de validation");
				options.add(texte);
				panneau = new JPanel();
				panneau.setLayout(new GridBagLayout()); // alignement vertical des futurs éléments added à panneau
				bouton = new JButton("Choisir");
				bouton.addActionListener(new SelectionnerFichier(this, "validation"));
				panneau.add(bouton);
				this.fichier_jeu_validation = new JLabel("Aucun fichier");
				panneau.add(fichier_jeu_validation);
				options.add(panneau);

				// Menu déroulant pour sélectionner la façon dont est choisi le meilleur attribut
				texte = new JLabel("Choix du meilleur attribut");
				options.add(texte);
				String[] choixMeilleurAttribut = {"Le premier", "Au hasard", "Meilleur gain d'information"};
				this.meilleurAttribut = new JComboBox<String>(choixMeilleurAttribut);
				options.add(this.meilleurAttribut);
				
				// Case à cocher pour élaguer l'arbre de décision
				texte = new JLabel("Elaguer l'arbre de décision");
				options.add(texte);
				this.elaguer = new Checkbox("Clique ici pour agrandir ton penis");
				// action event dessus pour changer letat de laffichage de l'input du coeff v
				options.add(this.elaguer);

				// Champ de texte pour le coefficient V pour l'élagage
				texte = new JLabel("Coefficient V");
				options.add(texte);
				this.coefficient_v = new JTextField();
				options.add(this.coefficient_v);

			// Elément vide pour séparer le panneau des options du bouton de construction
			panneau = new JPanel();
			panneau.setBorder(BorderFactory.createEmptyBorder(500,500,500,500));
			optionsConstruction.add(panneau);

			// Bouton de construction de l'arbre de décision dans la grille de gauche
			JButton construction = new JButton("Construire l'arbre");
			construction.setPreferredSize(new Dimension(0, 50)); // width height, 0 pour "auto"
			construction.addActionListener(new ConstruireArbre(this));
			optionsConstruction.add(construction);
	}

	public String jeuApprentissage() {
		return this.fichier_jeu_apprentissage.getText(); // rturn bien "" si pas de fichier ?
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

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.afficher();
	}

}