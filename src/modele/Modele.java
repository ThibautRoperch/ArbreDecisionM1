package modele;

import java.io.*;
import java.util.*;

import donnees.JeuDonnees;

public class Modele {

	protected HashMap<String, String> conditions;
	protected HashMap<String, String> conclusion;

	/**
	 * Construit un modèle vide
	 */
	public Modele() {
		this.conditions = new HashMap<String, String>();
		this.conclusion = new HashMap<String, String>();
	}

	/**
	 * Construit un modèle à partir de conditions et d'une conclusion
	 * @param conditions
	 * @param conclusion
	 */
	public Modele(HashMap<String, String> conditions, HashMap<String, String> conclusion) {
		this.conditions = conditions;
		this.conclusion = conclusion;
	}

	/**
	 * Ajoute aux conditions un attribut et sa valeur
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterCondition(String attribut, String valeur) {
		this.conditions.put(attribut, valeur);
	}

	/**
	 * Enregistre la conclusion
	 * Supprime l'ancienne s'il y en avait une (ce qui n'est pas censé arriver)
	 * @param attribut
	 * @param valeur
	 */
	public void ajouterConclusion(String attribut, String valeur) {
		this.conclusion.clear();
		this.conclusion.put(attribut, valeur);
	}
	
	public void tester(JeuDonnees donnees) {
		
	}

	/**
	 * Retourne une copie du modèle
	 * @return Modele
	 */
	public Modele clone() {
		HashMap<String, String> cond = new HashMap<String, String>();
		HashMap<String, String> conc = new HashMap<String, String>();
		cond.putAll(this.conditions);
		conc.putAll(this.conclusion);
		return new Modele(cond, conc);
	}

}
