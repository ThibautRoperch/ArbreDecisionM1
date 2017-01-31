package gui;

import java.awt.BorderLayout; // https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
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
	protected JLabel fichier_jeu_apprentissage; // nom du fichier contenant le jeu d'apprentissage
	protected JLabel fichier_jeu_validation; // nom du fichier contenant le jeu de validation

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
		caracStats.setPreferredSize(new Dimension(300, 0)); // width height, 0 pour "auto"
		this.add(BorderLayout.EAST, caracStats);

		// Zone d'affichage des caractéristiques dans une case de la grille de droite
		this.caracteristiques = new JTextArea();
		this.caracteristiques.setEditable(false);
		this.caracteristiques.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
		caracStats.add(this.caracteristiques);

		// Zone d'affichage des statistiques dans une case de la grille de droite
		this.statistiques = new JTextArea();
		this.statistiques.setEditable(false);
		this.statistiques.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
		caracStats.add(this.statistiques);

		// Gauche de la fenêtre : un panneau formaté par une grille de 2 lignes x 1 colonne pour le panneau des options et le bouton de construction de l'arbre de décision
		grille = new GridLayout(2, 1);
		JPanel optionsConstruction = new JPanel(grille);
		this.add(BorderLayout.WEST, optionsConstruction);

		// Panneau des options formaté par une grille de x lignes x 2 colonnes pour les options dans la grille de gauche
		grille = new GridLayout(0, 2);
		JPanel options = new JPanel(grille);
		options.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), BorderFactory.createEmptyBorder(10,10,10,10)));
		optionsConstruction.add(options);

		// Bouton de construction de l'arbre de décision dans la grille de gauche
		JButton construction = new JButton("Construire l'arbre");
		optionsConstruction.add(construction);

		// Bouton pour sélectionner le jeu d'apprentissage dans les options
		texte = new JLabel("Jeu d'apprentissage");
		options.add(texte);
		panneau = new JPanel();
        panneau.setLayout(new GridBagLayout()); // alignement vertical des futurs éléments added à panneau
		bouton = new JButton("Choisir");
		panneau.add(bouton);
		this.fichier_jeu_apprentissage = new JLabel("Aucun fichier");
		panneau.add(fichier_jeu_apprentissage);
		options.add(panneau);

		// Bouton pour sélectionner le jeu de validation dans les options
		texte = new JLabel("Jeu de validation");
		options.add(texte);
		panneau = new JPanel();
        panneau.setLayout(new GridBagLayout()); // alignement vertical des futurs éléments added à panneau
		bouton = new JButton("Choisir");
		panneau.add(bouton);
		this.fichier_jeu_validation = new JLabel("Aucun fichier");
		panneau.add(fichier_jeu_validation);
		options.add(panneau);

		
		// menu déroulant pour le choix du meileur attribut
		// coche pour elaguer ou pas elaguer
	}

	/**
	 * Affiche et place la fenêtre dans l'environnement
	 */
	public void display() {
		this.pack();
		this.setVisible(true);
		this.setLocation(350, 100);
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.display();


		File repertoireCourant = null;
        try {
            // obtention d'un objet File qui désigne le répertoire courant. Le
            // "getCanonicalFile" n'est pas absolument nécessaire mais permet
            // d'éviter les /Truc/./Chose/ ...
            repertoireCourant = new File(".").getCanonicalFile();
            System.out.println("Répertoire courant : " + repertoireCourant);
        } catch(IOException e) {
			e.getMessage();
		}
         
        // création de la boîte de dialogue dans ce répertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
         
        // affichage
        dialogue.showOpenDialog(null);
         
        // récupération du fichier sélectionné
        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
	}

}