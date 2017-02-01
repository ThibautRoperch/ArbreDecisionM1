package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class SelectionnerFichier implements ActionListener {

	protected Gui interface_graphique;
	protected String type_du_jeu;

	public SelectionnerFichier(Gui interface_graphique, String type_du_jeu) {
		this.interface_graphique = interface_graphique;
		this.type_du_jeu = type_du_jeu;
	}

	public void actionPerformed(ActionEvent event) {
		File repertoireCourant = null;

		try {
			// Obtention d'un objet File qui désigne le répertoire courant
			// "getCanonicalFile" pas indispensable, juste pour éviter les /Truc/./Chose/etc...
			repertoireCourant = new File(".").getCanonicalFile();
		} catch(IOException e) {
			e.getMessage();
		}
		 
		// Création de la boîte de dialogue dans ce répertoire courant (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel cas repertoireCourant vaut null)
		JFileChooser dialogue = new JFileChooser(repertoireCourant);
		 
		// Affichage de la boîte de dialogue
		dialogue.showOpenDialog(null);
		 
		// Récupération du fichier sélectionné
		String fichierChoisi = (dialogue.getSelectedFile() != null) ? dialogue.getSelectedFile().toString() : "";

		// Envoi du chemin du fichier dans l'interface graphique
		if (this.type_du_jeu.equals("apprentissage")) {
			this.interface_graphique.afficherFichierJeuApprentissage(fichierChoisi);
		}
		else if (this.type_du_jeu.equals("validation")) {
			this.interface_graphique.afficherFichierJeuValidation(fichierChoisi);
		}
	}

}