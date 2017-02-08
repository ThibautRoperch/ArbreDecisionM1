/**
 * Classe Attribut
 *
 * Cette classe permet d'instancier un attribut
 *
 * Un attribut possède un nom et une liste de valeurs
 * 
 * Un attribut est instancié par un jeu de données, structuré avec des attributs
 */

package donnees;

import java.util.ArrayList;

public class Attribut {

	protected String nom;
	protected ArrayList<String> valeurs;

	/**
	 * Construit un attribut vide
	 */
	public Attribut() {
		this.nom = "";
        this.valeurs = new ArrayList<String>();
	}

	/**
	 * Construit un attribut à partir d'un nom et d'une liste de valeurs
	 * @param attribut
	 * @param valeurs
	 */
	public Attribut(String nom, ArrayList<String> valeurs) {
		this.nom = nom;
		this.valeurs = valeurs;
	}

	/**
	 * Construit un attribut à partir d'un nom et d'une seule valeur
	 * @param attribut
	 * @param valeur
	 */
	public Attribut(String nom, String valeur) {
        this();
        this.nom = nom;
        this.valeurs.add(valeur);
	}

    /**
     * Retourne le nom de l'attribut
     * @return String
     */
    public String nom() {
        return this.nom;
    }

    /**
     * Retourne une copie de la liste des valeurs de l'attribut
     * @return ArrayList<String>
     */
    public ArrayList<String> valeurs() {
        ArrayList<String> res = new ArrayList<String>();

        // Pour chaque valeur
        for (String valeur : this.valeurs) {
            // Ajoute la valeur à res
            res.add(valeur);
        }

        return res;
    }

    /**
     * Modifie le nom de l'attribut
     * @param nom
     */
    public void nommer(String nom) {
        this.nom = nom;
    }
    
    /**
     * Ajoute une valeur à l'attribut
     * @param valeur
     */
    public void ajouterValeur(String valeur) {
        this.valeurs.add(valeur);
    }

	/**
	 * Supprime les valeurs de l'attribut, sauf celle donnée en paramètre
	 * @param valeur
	 */
    public void fixerValeur(String valeur) {
		this.valeurs.clear();
		this.valeurs.add(valeur);
    }

    /**
     * Retourne une copie de l'attribut
     * @return Attribut
     */
    public Attribut clone() {
        Attribut res = new Attribut();

        res.nommer(this.nom);

        // Pour chaque valeur
        for (String valeur : this.valeurs) {
            // Ajoute la valeur à res
            res.ajouterValeur(valeur);
        }

        return res;
    }

    public String toString() {
        return this.nom + "=" + this.valeurs;
    }

    public boolean equals(Object o) {
        Attribut attribut = (Attribut) o;
        return this.nom.equals(attribut.nom()) && this.valeurs.equals(attribut.valeurs());
	}

}
