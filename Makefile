# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire build
#     La hierarchie des sources (par package) est conservee.
#     L'archive build/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture buildScenarios buildStrategies

.PHONY: clean exeInvader exeLecture scenario0 scenario1 strategieElem strategieAvancee strategieAmelioree \
	testInvader testLecture buildScenarios buildStrategies

testInvader:
	javac -d build -classpath build/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d build -cp build/gui.jar -sourcepath src src/TestLecteurDonnees.java

#Plusieurs dépendances dans le même target
#Si vous regardez en dessous, chaque scenario/strategie ne demande que son .class et pas tout le target.
#Si on ne fait pas ça, on rebuild à chaque fois (ce qui est long)
buildStrategies build/StrategieElementaire.class build/StrategieAvancee.class build/StrategieMieux.class: src/Strategies.java
	javac -d build -cp build/gui.jar -sourcepath src src/Strategies.java

buildScenarios build/Scenario0.class build/Scenario1.class: src/Scenarios.java
	javac -d build -cp build/gui.jar -sourcepath src src/Scenarios.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath build:build/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: testInvader
	java -classpath build:build/gui.jar TestInvader

exeLecture: testLecture
	java -classpath build:build/gui.jar TestLecteurDonnees cartes/carteSujet.map

scenario0: build/Scenario0.class
	java -cp build:build/gui.jar Scenario0 $(carte)

scenario1: build/Scenario1.class
	java -cp build:build/gui.jar Scenario1 $(carte)

strategieElem: build/StrategieElementaire.class 
	java -cp build:build/gui.jar StrategieElementaire $(carte)

strategieAvancee: build/StrategieAvancee.class
	java -cp build:build/gui.jar StrategieAvancee $(carte)

strategieAmelioree: build/StrategieMieux.class
	java -cp build:build/gui.jar StrategieMieux $(carte)

clean:
	rm -rf build/*.class
