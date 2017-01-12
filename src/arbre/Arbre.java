package arbre;

import java.io.*;

class Arbre {
    protected Noeud noeud_racine; //faire les tests pour déterminer si le noeud est une feuille pure
    protected Arbre fils1;
    protected Arbre fils2;

    Arbre() {
        //calculer le meilleur attribut à mettre en noeud racine, puis remplir les fils. Puis recommencer sur les fils.

    } 

    //Calcule le gain pour choisir le meilleur attribut.
    //Prendre le jeu d'apprentissage en paramètre maybe ? 
    int gain(int positif, int negatif) {
        //return positif*(log(positif/positif+negatif)-log(P/P+N));

        //Appeler la fonction pour chaque attribut, et mettre à jour le
    }
}
