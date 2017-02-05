# ArbreDecisionM1

Construire et post-élaguer un arbre de décision (projet d'option IA M1 info 2017)

## Compilation et exécution

Compiler les classes :

	make

Exécuter le programme principal en donnant les jeux d'apprentissage et de validation ainsi qu'un coefficient V :

	make main app=<jeu d'apprentissage> test=<jeu de validation> v=<coefficient V>

Exemple de ligne de compilation et d'exécution du programme principal :

	clear ;  make ; make main app=jeux/Mushroom/mushroom_train.arff test=jeux/Mushroom/mushroom_valid.arff v=0.005

Ouvrir l'interface graphique :

	make interface


## Termes employés

Attribut

> Un critère, une colonne dans un jeu de données

Attribut discriminant

> Un critère qui engendre au moins une feuille pure (attribut efficace)

Exemple

> Un cas concret ayant une valeur pour chaque attribut, une ligne dans un jeu de données

## Structures utilisées

### Représenter un arbre

Classe Arbre qui contient :
* Un pointeur vers le noeud racine de l'arbre
* Un pointeur vers chaque noeud feuille (liste de noeuds)

### Représenter un noeud d'arbre

Classe Noeud qui contient :
* Un pointeur vers son noeud père
* Un jeu de données d'apprentissage
* Un jeu de données de validation
* Un pointeur vers chaque noeud enfants (liste de noeuds)

### Représenter un jeu de données

Classe JeuDonnees qui contient :
* Une liste d'attributs, un attribut étant représenté par son nom et ses valeurs possibles
* Une liste d'exemples, un exemple étant représenté par une liste de valeurs à raison d'une valeur pour chaque attribut
* Des méthodes de calcul sur les caractéristiques du jeu de données comme le nombre d’exemples, la liste des attributs candidats (soit les attributs à au moins deux valeurs possibles), ou encore la classe majoritaire.


## Contributeurs

<table>
<tr>
	<td><a href="https://github.com/PierreGranier"><img alt="Pierre Granier--Richard" src="https://avatars1.githubusercontent.com/u/11854882" width="25"></a></td>
	<td><a href="https://github.com/PierreGranier">PierreGranier</a></td>
</tr>
<tr>
	<td><a href="https://github.com/ThibautRoperch"><img alt="Thibaut Roperch" src="https://avatars3.githubusercontent.com/u/18574394" width="25"></a></td>
	<td><a href="https://github.com/ThibautRoperch">@ThibautRoperch</a></td>
</tr>
</table>
