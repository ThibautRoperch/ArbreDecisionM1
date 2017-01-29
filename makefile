#makefile

# all

all:
	@echo "# Compilation des classes"
	find -name "*.java" | xargs javac -d bin -Xlint:unchecked

# arbre

main:
	@echo "# Lancement du programme principal\n# Usage : app=<jeu d'apprentissage> test=<jeu de test> k=<coefficient k>"
	java -cp bin Main ${app} ${test} ${k}

arbre:
	@echo "# Création d'un arbre de test"
	java -cp bin arbre.Arbre

jeudonnees:
	@echo "# Création d'un jeu de données de test"
	java -cp bin donnees.JeuDonnees
	
lecturefichier:
	@echo "# Lecture d'un fichier de test"
	java -cp bin donnees.LectureFichier
	
# nettoyage

clean:
	@echo "# Nettoyage du dossier bin"
	rm -rf bin/*
