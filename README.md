# ArbreDecisionM1
Construire et post-élaguer un arbre de décision (projet d'option IA M1 info 2017)

## Règles

### Termes employés

Attribut

> Un critère, une colonne dans un jeu de données)

Attribut discriminant

> Un critère qui engendre au moins une feuille pure (attribut efficace)

Exemple

> Un cas concret ayant une valeur pour chaque attribut, une ligne dans un jeu de données

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

### Représenter les exemples

Une matrice de chaines de caractères :
* Autant de colonnes qu'il y a de attribut
* Autant de lignes qu'il y a d'exemples

### Représenter un arbre

Classe Arbre qui contient :
* 

### Représenter un noeud d'arbre

Classe Noeud qui contient :
* Des exemples
* Une méthode pour trouver le meilleur attribut