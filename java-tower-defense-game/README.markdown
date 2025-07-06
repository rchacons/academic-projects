# Projet PO - Java Defense

##Group de Binome: 
###TP2 - Binome 10
+ **Roberto Chacon Suarez**
+ **Jason Horth**



##Objectifs du projet
L�objectif de ce projet est de concevoir et impl�menter une version simplifi�e d�un jeu **Tower defense** en Java � l'aide de la biblioteque [StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html).



##D�veloppement

###Le Plateau du jeu 

D�abord on a d�fini le plateau du jeu avec les param�tres :
 
 * *Width* = 960       
 * *Height* = 640     
 * *NbSquareX*=15   
 * *NbSquareY*= 10

Ces param�tres correspondent � une case de 64 pixels ( *Width / NbSquareX = 64*, *Height / NbSquareY = 64*), de mani�re qu�on puisse avoir des images de meilleur qualit� tant qu�elles soient de cette taille. 
Ces param�tres sont d�finies dans la **class Boot**.  
Puis, on a cr�� la **class Case** et **CaseGrid** afin que chaque case du plateau soit modifiable et qu�il soit facile de reconnaitre son type de texture (utile pour dessiner le chemin des monstres ainsi que le placement des tours).
Le plateau du jeu est facilement modifiable gr�ce au Double Array :


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

O� les 0 correspondent � la *prairie*, le 1 a la *terre* et le 2 au *sol du ch�teau*.

Ce double Array  *map* est pris comme argument dans le constructeur de la **class CaseGrid**, qui inverse les lignes et colonnes du plateau pour pouvoir l�afficher de mani�re correcte dans la fen�tre graphique de StdDraw.


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

Lorsqu�on manipule les positions des entit�s dans le jeu, on obtient des coordonn�s normalis�es de la fen�tre graphique de StdDraw, c�est pour cela qu�il nous faut une m�thode (tr�s importante pour la suite) qui transforme 
ces coordonnes et renvoi la case du plateau correspondante � cette position:

public Case getCaseMap(double normalizedX, double normalizedY) {
		
		int x = (int)((normalizedX - this.squareW/2)/this.squareW);
		int y = (int)((normalizedY - this.squareH/2)/this.squareH);
		if(x < NbSquareX && y < NbSquareY && x > -1 && y > -1)
			return map[x][9-y];
		else
			return new Case(0,0,0,0, "Null");
}



###Les Monstres

Pour l�impl�mentation des Monstres on s�est servi de la **classe abstract Monstre** qui nous a �t� donne. Elle compte avec plusieurs variables (priv�es pour suivre les principes d�encapsulation) parmi lesquelles on souligne *health*,
*speed, damageCastle, recompense, img, type*. Ces variables seront modifi�es par las diff�rentes classes de Monstres qui vont l�h�riter en fonction des caract�ristiques de chaque monstre.

De m�me, dans la classe on trouve les m�thodes *move(), damage(), checkpointReached(), findNextD(), update(), draw()* et les diff�rentes getters / setters. 
Pour g�rer le d�placement des monstres :

1.	Dans la m�thode *move()* on mesure la distance � laquelle le monstre a pu se d�placer, et ensuite on appelle la m�thode *findNextD()*, qui prend comme argument une appelle au methode *getCaseMap* de la classe **CaseGrid** avec la position 
du monstre, avec l�objectif d�obtenir la case dans laquelle il se trouve dans cet instant :

	findNextD(World.grid.getCaseMap(p.x, p.y))

2.	Puis, dans la methode *findNextD(Case actual)*, on se sert de la position de la Case actual pour obtenir les cases qui se trouvent autour d�elle:

		//Case superieur
		Case u = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()+0.1);
		
		//Case inferieur
		Case d = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()-0.1);
		
		//Case droit
		Case r = World.grid.getCaseMap(actual.getXNor()+0.0666667,actual.getYNor()); 
		
		//Case Gauche
		Case l = World.grid.getCaseMap(actual.getXNor()-0.066, actual.getYNor());  
		

3. 	Vu que la position de la case corresponde � son centre, on a calcul� la diff�rence entre la position du monstre et celle de la case, et quand elle soit minimale, cela nous dira que le monstre se trouve dans la position centrale de la case. 

4.	Ensuite, lorsqu�on v�rifie cette condition, on d�finira la direction � laquelle le monstre va se diriger en fonction des cases *u, d ,r , l* , un integer appel� *lastdir* et un switch :

	//PseudoCode
	
	Switch(valeur de lastdir)
	
	Case 1 :  //Le monstre se dirige vers le bas
		On v�rifie les 3 cases qui sont autour (sauf celle de haut puisqu�il vient de ce direction)
		Et il se dirigera � celle qui est de texture � dirt �
		
	Case 2 : //Le monstre se dirige vers le haut
		On v�rifie les m�mes conditions que ci-dessus seulement cette fois on veut continuer vers le haut (et donc on ignore la case inferieur)
		
	Case 3 : //Le monstre se dirige vers la droite
		On v�rifie les m�mes conditions que ci-dessus seulement cette fois on veut continuer vers la droite(et donc on ignore la case gauche)
		
	Case 4 : // Le monstre se dirige vers la gauche 
		On v�rifie les m�mes conditions que ci-dessus seulement cette fois on veut continuer vers la gauche(et donc on ignore la case droite)

5.	Finalement, dans la m�thode *update()* on lance l�appel � la m�thode *move()* et *draw()*.De mani�re qu�on g�re avec succ�s le d�placement des monstres en suivant le chemin. 


###Les Vagues

On a construit deux classes, **Wave** et **WaveManager** pour cr�er et manipuler facilement les vagues. Dans la premi�re on peut souligner variables telles que deux *LinkedLists de monstres*(l�une � laquelle on ajoute les monstres et qu�on utilise pour 
iterer, et l�autre pour g�rer la suppression de monstres qui sera � la fin renvoy�e pour v�rifier le final de la vague), ici on a d�cid� d�utiliser des LinkedLists car le program fera plein d�ajouts/suppressions et elles sont plus adapt�es pour �a. 
On a aussi variables telles que le *temps de spawn de monstres, la position de spawn, le nombre de monstres par vague, le nombre de monstres apparus, etc.* 

Fonctionnement:

1.	Le spawn de monstres : on v�rifie d�abord le nombre de monstres par Vague, et si le monstres apparus n�est pas encore �gal, on cr�e une valeur al�atoire et en fonction de cette valeur on d�cidera si cr�er de monstres de base ou a�riens, et on les ajoute 
aux listes.  Une fois tous les monstres par vague sont apparus, on fait apparaitre un � boss � . 

2.	La m�thode *update()* : On assume que tous les mortes sont morts par un boolean, jusqu�� ce que le program montre le contraire. Ici on trouve de conditions qui comparent le nombre de monstres apparus avec le nombre de monstres par vague, ainsi que le temps 
d�apparition entre chaque monstre. Et pour chaque monstre dans la liste, on v�rifie s�il est vivant ou pas. Une fois tous les monstres soient apparus et morts, on d�clare la vague comme termin�e.

La classe **WaveManager** s�occupe de cr�er le d�lai du d�but du jeu (� l�aide d�une classe **Timer** qui sert � chronom�trer depuis un Instant d�but jusqu�� une dur�e souhait�e) ainsi que le d�lai entre chaque vague. Ensuite on a les variables comme le bonus par vague 
et le nombre de vagues du jeu. La fonction principale de la classe c�est de cr�er les vagues et par chaque vague, en fonction de son niveau, modifier les caract�ristiques des monstres pour les rendre plus difficiles. 



###Les Tours
On a construit une **class abstract Tower**, qui sera h�rit� par les diff�rentes types de tours dans le jeu.

On a d�cid� de cr�er 4 types de tours :

1.	*Une tour de bombes*.
2.	*Une tour d�archers*
3.	*Une tour freeze*, qui ralenti la vitesse de d�placement de l�ennemi
4.	*Une tour poison*, qui diminue la vie du monstre a mesure que le jeu progresse.


Dans cette classe, on peut trouver des variables comme *les coordonn�es de la tour, sa largueur et hauteur, sa port�e,*
*le type de tour, la vitesse de rechargement, le cout de l�am�lioration, une liste de monstres, un monstre appel� target, une List de projectiles, etc.*
 
Et des m�thodes comme *targetEnemy(), isInRange(), calculateAngle, updateProjectiles() , updateMonsterList et update().*

Fonctionnement : 

1.	Chaque tour cible un monstre qui est dans sa port�e, pour cela, on attribue au monstre target la methode *targetEnemy()*. Cette m�thode parcourt 
chaque monstre pr�sent dans le jeu et en appelant la m�thode *isInRange(Monster m)*, elle d�cide de cibler le monstre ou pas. 
Si le montre est hors de port�e, elle cherche un autre monstre a cibler. 

2.	Une fois la tour a une monstre cibl�, elle tire des projectiles et s�assure de remplir les conditionnes de la tour (par exemple si c�est un tour de bombes 
elle ne peut pas attaquer les monstres a�riens) . Si le monstre est mort, elle cherche un autre monstre � cibler. 

3.	Les m�thodes abstraites qui seront modifies par les classes filles sont *shoot(Monster Target)* et *draw()*. O� la m�thode *shoot* cr�e des projectiles 
en fonction du type de tour, et l�ajout a sa liste de projectiles. Et la m�thode *draw* g�re l�affichage de la tour, son canon qui suit le monstre(avec l�angle), 
et dans le cas o� elle a �t� am�lior�e, elle dessin un signe � + � au bas de la tour.


###Les Projectiles

De la m�me mani�re qu�avec les tours, on a cr�� une **class abstract Projectile** qui sera h�rit� par les diff�rentes types de projectiles dans le jeu. 
Il existe des projectiles pour chaque type de tour, ceux-ci �tant :

1.	*Des fl�ches*
2.	*Des bombes*
3.	*Des flocons de neige*
4.	*Des syringes de poison*

Dans la *classe Projectile* on trouve des variables comme *les coordonnes x,y initial du projectile, le x,y correspondant � son d�placement, la vitesse de d�placement,*
*le longueur et hauteur, le points de d�g�ts, le monstre cibl�, l�angle etc.*

Les m�thodes principales de cette classe sont *getBounds(),  calculeDirection(), damage(), move(), draw().*

 Fonctionnement 
 
1.	Tout d�abord, on calcule la direction a laquelle le projectile va se diriger, pour cela on appelle la m�thode *calculateDirection* qui calculera la distance entre le 
monstre cibl� et sa position initiale, ainsi que le pourcentage de mouvement et d�finira les X et Y correspondantes � son d�placement, qui au m�me temps seront multiplies par la vitesse de d�placement. 

2.	Si le monstre est mort, ou il a atteint le ch�teau, ou le projectile sort du plateau, on l��limine du jeu. 

3.	Les m�thodes *getBounds()* (Pour cr�er le � hitbox � autour du projectile) et *damage()* (Pour infliger de d�g�ts au monstre), vont �tre utilis�es depuis la **classe principal du jeu : World**.
 
###Le Jeu Principal

Dans la boucle principale du jeu, on trouve *le d�cors du plateau du jeu, l�ensemble de tours, l�initialisation des vagues, la vie du joueur, la quantit� d�or, et les bool�ens qui d�finissent si la partie a �t� gagn�e ou pas.*

Les changements remarquables � cette classe sont :

1.	La cr�ation des trois m�thodes : *checkIfBuildable* (elle v�rifie si on peut construire une tour, en suivant les conditionnes telles qu�avoir assez d�or, qu�il n�existe pas une tour dans cette position 
ou si on essaie de la placer dans le chemin), *checkTower* (s�il y a une tour dans la case s�lectionn�, elle sera renvoy�e),  *checkCase*(Boolean qui v�rifie s�il y a une tour dans la case s�lectionn�e). 
Tous ces m�thodes sont impl�ment�es dans la m�thode *mouseClick* afin de pouvoir ajouter ou am�liorer une tour existante avec la souris.

2.	La m�thode *checkCollisions()* parcourt la liste de projectiles de la tour, ainsi que la liste de monstres pr�sents dans le jeu, et construit un rectangle autour des deux, et si ces deux rectangles entrent en collision, 
on �limine le projectile du jeu et on inflige de points de d�g�ts au monstre.   

3.	Les m�thodes *checkWin()* et *checkDefeat()* servent � v�rifier lorsque la partie a �t� gagn�e (tous les vagues battus ) ou perdue (la vie du joueur est 0 )

4.	Et finalement dans la m�thode *update()* on g�rera tous les d�placements et actions des entit�s.  


###Division du travail:

1.	*Le Plateau du Jeu :  r�alis� par **Roberto***
2.	*Les Monstres : r�alis�s par **Roberto***
3.	*Les Vagues : r�alis�es par **Roberto***
4.	*Les tours :  r�alis�es par **Jason***
5.	*Les projectiles : r�alis�s par **les deux***
6.	*Le jeu principal (World) : r�alis� par **les deux***



***Les images des monstres, projectiles et textures du plateau on �t� telecharg�s du site Flaticon.com lequel donne une license gratuite de ses images sous mention. Les tours et canons on �t� dessin�s par nous meme.***
