# ArbreDecisionM1
Construire et post-élaguer un arbre de décision (projet d'option IA M1 info 2017)

## Termes employés

Attribut

> Un critère, une colonne dans un jeu de données)

Attribut discriminant

> Un critère qui engendre au moins une feuille pure (attribut efficace)

Exemple

> Un cas concret ayant une valeur pour chaque attribut, une ligne dans un jeu de données

## Code

### Nom des méthodes

camelCase (nomMethode)

### Commentaire en début de classe

    /**
     * NomClasse
     *
     * Cette classe permet d'instancier un ... 
     * Un objet de type ... instancie cette classe dans la methode ...
     */

## Structures utilisées

### Représenter un arbre

Classe Arbre qui contient :
* Un pointeur vers le noeud racine de l'arbre

### Représenter un noeud d'arbre

Classe Noeud qui contient :
* Un jeu de données
* Un pointeur vers chaque noeud enfants (liste)
* Une calcul du meilleur attribut (gain d'entropie pour chaque attribut)

### Représenter un jeu de données

Classe JeuDonnees qui contient :
* Une matrice de chaines de caractères (autant de colonnes qu'il y a d'attributs et autant de lignes qu'il y a d'exemples)
* Les attributs et leur valeur précédemment choisis (liste)
