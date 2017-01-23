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
* Un pointeur vers chaque noeud feuille (liste)

### Représenter un noeud d'arbre

Classe Noeud qui contient :
* Un pointeur vers son noeud père
* Un jeu de données
* Un pointeur vers chaque noeud enfants (liste)
* Une calcul du meilleur attribut (gain d'entropie pour chaque attribut)

### Représenter un jeu de données

Classe JeuDonnees qui contient :
* Une map ayant pour paire un attribut et une liste des valeurs de l'attribut
* Une liste d'exemples, un exemple étant une liste de chaines de caractères représentant ses valeurs pour chaque attribut

## Contributeurs

[<img alt="Pierre Granier--Richard" src="https://avatars1.githubusercontent.com/u/11854882" width="50">](https://github.com/PierreGranier) | [<img alt="Thibaut Roperch" src="https://avatars3.githubusercontent.com/u/18574394" width="50">](https://github.com/ThibautRoperch) |
------------------------|---------------------------|----------------------|-----------------------------------|
[@PierreGranier](https://github.com/PierreGranier) | [@ThibautRoperch](https://github.com/ThibautRoperch)

[<img alt="Pierre Granier--Richard" src="https://avatars1.githubusercontent.com/u/11854882" width="50">](https://github.com/PierreGranier) | [@PierreGranier](https://github.com/PierreGranier) |
------------------------|---------------------------|----------------------|-----------------------------------|
[<img alt="Thibaut Roperch" src="https://avatars3.githubusercontent.com/u/18574394" width="50">](https://github.com/ThibautRoperch) | [@ThibautRoperch](https://github.com/ThibautRoperch)

