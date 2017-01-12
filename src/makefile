#makefile

# all

all:
	@echo "# Compilation des classes"
	find -name "*.java" | xargs javac -d bin -Xlint:unchecked

# arbre

arbre:
	@echo "# Création d'un arbre de test"
	java -cp bin arbre.Arbre
	
noeud:
	@echo "# Création d'un noeud de test"
	java -cp bin arbre.Noeud
	
# nettoyage

clean:
	@echo "# Nettoyage du dossier bin"
	rm -rf bin/*
