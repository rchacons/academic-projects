# Projet PO - Java Defense

##Group de Binome: 
###TP2 - Binome 10
+ **Roberto Chacon Suarez**
+ **Jason Horth**



##Objectifs du projet
L’objectif de ce projet est de concevoir et implémenter une version simplifiée d’un jeu **Tower defense** en Java à l'aide de la biblioteque [StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html).



##Développement

###Le Plateau du jeu 

D’abord on a défini le plateau du jeu avec les paramètres :
 
 * *Width* = 960       
 * *Height* = 640     
 * *NbSquareX*=15   
 * *NbSquareY*= 10

Ces paramètres correspondent à une case de 64 pixels ( *Width / NbSquareX = 64*, *Height / NbSquareY = 64*), de manière qu’on puisse avoir des images de meilleur qualité tant qu’elles soient de cette taille. 
Ces paramètres sont définies dans la **class Boot**.  
Puis, on a créé la **class Case** et **CaseGrid** afin que chaque case du plateau soit modifiable et qu’il soit facile de reconnaitre son type de texture (utile pour dessiner le chemin des monstres ainsi que le placement des tours).
Le plateau du jeu est facilement modifiable grâce au Double Array :


	int [][] map = {
	
			{0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,2 ,0 ,0 ,0 ,0 },
			{0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 },
			{0 ,1 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,0 ,1 ,1 ,1 ,1 ,0 },
			{0 ,1 ,0 ,0 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,1 ,0 ,0 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,1 ,1 ,1 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,0 },
			
	};

Où les 0 correspondent à la *prairie*, le 1 a la *terre* et le 2 au *sol du château*.

Ce double Array  *map* est pris comme argument dans le constructeur de la **class CaseGrid**, qui inverse les lignes et colonnes du plateau pour pouvoir l’afficher de manière correcte dans la fenêtre graphique de StdDraw.


	public CaseGrid(int [][] newMap){    //Il prend le map definie dans la classe World(10x15)  
		
		//Pour pouvoir afficher correctement le map[][] dans la fenetre graphique, on doit inverser ses valeurs et creer un nouvel map
			this.NbSquareX = newMap[0].length; //15
			this.NbSquareY = newMap.length;	   //10
			this.squareW = (double)1/NbSquareX;
			this.squareH = (double)1/NbSquareY;
			
			map = new Case[NbSquareX][NbSquareY];  //On cree le map de Cases (15x10) 
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
	
					if(newMap[j][i]==0)  //Si les valeurs dans le map[][] sont 0/1 on definira le type de la Case comme grass/dirt 
						
					map[i][j] = new Case(i,j,squareW, squareH, "grass");
				else if (newMap[j][i]==1)
				map[i][j] = new Case(i, j, squareW, squareH, "dirt");
				else
					map[i][j] = new Case(i,j,squareW, squareH, "stone");
				}	
			}
	}

Lorsqu’on manipule les positions des entités dans le jeu, on obtient des coordonnés normalisées de la fenêtre graphique de StdDraw, c’est pour cela qu’il nous faut une méthode (très importante pour la suite) qui transforme 
ces coordonnes et renvoi la case du plateau correspondante à cette position:

public Case getCaseMap(double normalizedX, double normalizedY) {
		
		int x = (int)((normalizedX - this.squareW/2)/this.squareW);
		int y = (int)((normalizedY - this.squareH/2)/this.squareH);
		if(x < NbSquareX && y < NbSquareY && x > -1 && y > -1)
			return map[x][9-y];
		else
			return new Case(0,0,0,0, "Null");
}



###Les Monstres

Pour l’implémentation des Monstres on s’est servi de la **classe abstract Monstre** qui nous a été donne. Elle compte avec plusieurs variables (privées pour suivre les principes d’encapsulation) parmi lesquelles on souligne *health*,
*speed, damageCastle, recompense, img, type*. Ces variables seront modifiées par las différentes classes de Monstres qui vont l’hériter en fonction des caractéristiques de chaque monstre.

De même, dans la classe on trouve les méthodes *move(), damage(), checkpointReached(), findNextD(), update(), draw()* et les différentes getters / setters. 
Pour gérer le déplacement des monstres :

1.	Dans la méthode *move()* on mesure la distance à laquelle le monstre a pu se déplacer, et ensuite on appelle la méthode *findNextD()*, qui prend comme argument une appelle au methode *getCaseMap* de la classe **CaseGrid** avec la position 
du monstre, avec l’objectif d’obtenir la case dans laquelle il se trouve dans cet instant :

	findNextD(World.grid.getCaseMap(p.x, p.y))

2.	Puis, dans la methode *findNextD(Case actual)*, on se sert de la position de la Case actual pour obtenir les cases qui se trouvent autour d’elle:

		//Case superieur
		Case u = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()+0.1);
		
		//Case inferieur
		Case d = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()-0.1);
		
		//Case droit
		Case r = World.grid.getCaseMap(actual.getXNor()+0.0666667,actual.getYNor()); 
		
		//Case Gauche
		Case l = World.grid.getCaseMap(actual.getXNor()-0.066, actual.getYNor());  
		

3. 	Vu que la position de la case corresponde à son centre, on a calculé la différence entre la position du monstre et celle de la case, et quand elle soit minimale, cela nous dira que le monstre se trouve dans la position centrale de la case. 

4.	Ensuite, lorsqu’on vérifie cette condition, on définira la direction à laquelle le monstre va se diriger en fonction des cases *u, d ,r , l* , un integer appelé *lastdir* et un switch :

	//PseudoCode
	
	Switch(valeur de lastdir)
	
	Case 1 :  //Le monstre se dirige vers le bas
		On vérifie les 3 cases qui sont autour (sauf celle de haut puisqu’il vient de ce direction)
		Et il se dirigera à celle qui est de texture « dirt »
		
	Case 2 : //Le monstre se dirige vers le haut
		On vérifie les mêmes conditions que ci-dessus seulement cette fois on veut continuer vers le haut (et donc on ignore la case inferieur)
		
	Case 3 : //Le monstre se dirige vers la droite
		On vérifie les mêmes conditions que ci-dessus seulement cette fois on veut continuer vers la droite(et donc on ignore la case gauche)
		
	Case 4 : // Le monstre se dirige vers la gauche 
		On vérifie les mêmes conditions que ci-dessus seulement cette fois on veut continuer vers la gauche(et donc on ignore la case droite)

5.	Finalement, dans la méthode *update()* on lance l’appel à la méthode *move()* et *draw()*.De manière qu’on gère avec succès le déplacement des monstres en suivant le chemin. 


###Les Vagues

On a construit deux classes, **Wave** et **WaveManager** pour créer et manipuler facilement les vagues. Dans la première on peut souligner variables telles que deux *LinkedLists de monstres*(l’une à laquelle on ajoute les monstres et qu’on utilise pour 
iterer, et l’autre pour gérer la suppression de monstres qui sera à la fin renvoyée pour vérifier le final de la vague), ici on a décidé d’utiliser des LinkedLists car le program fera plein d’ajouts/suppressions et elles sont plus adaptées pour ça. 
On a aussi variables telles que le *temps de spawn de monstres, la position de spawn, le nombre de monstres par vague, le nombre de monstres apparus, etc.* 

Fonctionnement:

1.	Le spawn de monstres : on vérifie d’abord le nombre de monstres par Vague, et si le monstres apparus n’est pas encore égal, on crée une valeur aléatoire et en fonction de cette valeur on décidera si créer de monstres de base ou aériens, et on les ajoute 
aux listes.  Une fois tous les monstres par vague sont apparus, on fait apparaitre un « boss » . 

2.	La méthode *update()* : On assume que tous les mortes sont morts par un boolean, jusqu’à ce que le program montre le contraire. Ici on trouve de conditions qui comparent le nombre de monstres apparus avec le nombre de monstres par vague, ainsi que le temps 
d’apparition entre chaque monstre. Et pour chaque monstre dans la liste, on vérifie s’il est vivant ou pas. Une fois tous les monstres soient apparus et morts, on déclare la vague comme terminée.

La classe **WaveManager** s’occupe de créer le délai du début du jeu (à l’aide d’une classe **Timer** qui sert à chronométrer depuis un Instant début jusqu’à une durée souhaitée) ainsi que le délai entre chaque vague. Ensuite on a les variables comme le bonus par vague 
et le nombre de vagues du jeu. La fonction principale de la classe c’est de créer les vagues et par chaque vague, en fonction de son niveau, modifier les caractéristiques des monstres pour les rendre plus difficiles. 



###Les Tours
On a construit une **class abstract Tower**, qui sera hérité par les différentes types de tours dans le jeu.

On a décidé de créer 4 types de tours :

1.	*Une tour de bombes*.
2.	*Une tour d’archers*
3.	*Une tour freeze*, qui ralenti la vitesse de déplacement de l’ennemi
4.	*Une tour poison*, qui diminue la vie du monstre a mesure que le jeu progresse.


Dans cette classe, on peut trouver des variables comme *les coordonnées de la tour, sa largueur et hauteur, sa portée,*
*le type de tour, la vitesse de rechargement, le cout de l’amélioration, une liste de monstres, un monstre appelé target, une List de projectiles, etc.*
 
Et des méthodes comme *targetEnemy(), isInRange(), calculateAngle, updateProjectiles() , updateMonsterList et update().*

Fonctionnement : 

1.	Chaque tour cible un monstre qui est dans sa portée, pour cela, on attribue au monstre target la methode *targetEnemy()*. Cette méthode parcourt 
chaque monstre présent dans le jeu et en appelant la méthode *isInRange(Monster m)*, elle décide de cibler le monstre ou pas. 
Si le montre est hors de portée, elle cherche un autre monstre a cibler. 

2.	Une fois la tour a une monstre ciblé, elle tire des projectiles et s’assure de remplir les conditionnes de la tour (par exemple si c’est un tour de bombes 
elle ne peut pas attaquer les monstres aériens) . Si le monstre est mort, elle cherche un autre monstre à cibler. 

3.	Les méthodes abstraites qui seront modifies par les classes filles sont *shoot(Monster Target)* et *draw()*. Où la méthode *shoot* crée des projectiles 
en fonction du type de tour, et l’ajout a sa liste de projectiles. Et la méthode *draw* gère l’affichage de la tour, son canon qui suit le monstre(avec l’angle), 
et dans le cas où elle a été améliorée, elle dessin un signe « + » au bas de la tour.


###Les Projectiles

De la même manière qu’avec les tours, on a créé une **class abstract Projectile** qui sera hérité par les différentes types de projectiles dans le jeu. 
Il existe des projectiles pour chaque type de tour, ceux-ci étant :

1.	*Des flèches*
2.	*Des bombes*
3.	*Des flocons de neige*
4.	*Des syringes de poison*

Dans la *classe Projectile* on trouve des variables comme *les coordonnes x,y initial du projectile, le x,y correspondant à son déplacement, la vitesse de déplacement,*
*le longueur et hauteur, le points de dégâts, le monstre ciblé, l’angle etc.*

Les méthodes principales de cette classe sont *getBounds(),  calculeDirection(), damage(), move(), draw().*

 Fonctionnement 
 
1.	Tout d’abord, on calcule la direction a laquelle le projectile va se diriger, pour cela on appelle la méthode *calculateDirection* qui calculera la distance entre le 
monstre ciblé et sa position initiale, ainsi que le pourcentage de mouvement et définira les X et Y correspondantes à son déplacement, qui au même temps seront multiplies par la vitesse de déplacement. 

2.	Si le monstre est mort, ou il a atteint le château, ou le projectile sort du plateau, on l’élimine du jeu. 

3.	Les méthodes *getBounds()* (Pour créer le « hitbox » autour du projectile) et *damage()* (Pour infliger de dégâts au monstre), vont être utilisées depuis la **classe principal du jeu : World**.
 
###Le Jeu Principal

Dans la boucle principale du jeu, on trouve *le décors du plateau du jeu, l’ensemble de tours, l’initialisation des vagues, la vie du joueur, la quantité d’or, et les booléens qui définissent si la partie a été gagnée ou pas.*

Les changements remarquables à cette classe sont :

1.	La création des trois méthodes : *checkIfBuildable* (elle vérifie si on peut construire une tour, en suivant les conditionnes telles qu’avoir assez d’or, qu’il n’existe pas une tour dans cette position 
ou si on essaie de la placer dans le chemin), *checkTower* (s’il y a une tour dans la case sélectionné, elle sera renvoyée),  *checkCase*(Boolean qui vérifie s’il y a une tour dans la case sélectionnée). 
Tous ces méthodes sont implémentées dans la méthode *mouseClick* afin de pouvoir ajouter ou améliorer une tour existante avec la souris.

2.	La méthode *checkCollisions()* parcourt la liste de projectiles de la tour, ainsi que la liste de monstres présents dans le jeu, et construit un rectangle autour des deux, et si ces deux rectangles entrent en collision, 
on élimine le projectile du jeu et on inflige de points de dégâts au monstre.   

3.	Les méthodes *checkWin()* et *checkDefeat()* servent à vérifier lorsque la partie a été gagnée (tous les vagues battus ) ou perdue (la vie du joueur est 0 )

4.	Et finalement dans la méthode *update()* on gèrera tous les déplacements et actions des entités.  


###Division du travail:

1.	*Le Plateau du Jeu :  réalisé par **Roberto***
2.	*Les Monstres : réalisés par **Roberto***
3.	*Les Vagues : réalisées par **Roberto***
4.	*Les tours :  réalisées par **Jason***
5.	*Les projectiles : réalisés par **les deux***
6.	*Le jeu principal (World) : réalisé par **les deux***



***Les images des monstres, projectiles et textures du plateau on été telechargés du site Flaticon.com lequel donne une license gratuite de ses images sous mention. Les tours et canons on été dessinés par nous meme.***
