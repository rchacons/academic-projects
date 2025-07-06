/*Images from  Danc's Miraculously Flexible Game Prototyping Tiles 
 *Images telecharges du site Flaticon.com, license gratuite sous attribution
 */

package warcraftTD;


import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.*;

import warcraftTD.Monster.monsterType;
import warcraftTD.Tower.TowerType;


public class World {

	//L'ensemble de tours 
	List<Tower> towerlist = new ArrayList<Tower>();
	
	
	public static WaveManager VagueManager;
	
	
	//Pour savoir si la partie est gagnee ou pas
	private static boolean gameWon;   
	private static boolean gameLost;
	
	// Position par laquelle les monstres vont venir
	public static Position spawn;
	
	// Information sur la taille du plateau de jeu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	public static double squareWidth;
	public static double squareHeight;
	
	// Nombre de points de vie du joueur
	public static int life;
	
	// Quantite d'or du depart
	public static int gold;
	
	// Commande sur laquelle le joueur appuie (sur le clavier)
	char key=' ';
	
	// Condition pour terminer la partie
	boolean end = false;
	
	
	//Definit le decors du plateau de jeu.
	public static CaseGrid grid;

	

	
	
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases donnees
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	public World(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY,int [][] map) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		spawn = new Position(startSquareX * squareWidth + squareWidth / 2, startSquareY * squareHeight + squareHeight / 2);
		grid = new CaseGrid(map); //On attribue les valeurs du map a la grid des Cases
		World.life = 20;
		World.gold = 100;
		VagueManager = new WaveManager(spawn, 60, 3);
		gameWon = false;
		gameLost = false;
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();

	}
	
	 
	 /**
	  * Affiche certaines informations sur l'ecran telles que les points de vie du joueur ou son or
	  */
	 public void drawInfos() {
			
		Font font = new Font("SansSerif",Font.BOLD, 20);
		StdDraw.setFont(font);
		 
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.picture(spawn.x,spawn.y, "images/portal.png", squareWidth, squareHeight);
		 
		 StdDraw.picture(0.92, 0.95, "images/heart.png",squareWidth/1.5,squareHeight/1.5);
		 StdDraw.text(0.97, 0.95, World.life + "");
		 
		 StdDraw.picture(0.92, 0.88, "images/coin.png",squareWidth/1.5,squareHeight/1.5);
		 StdDraw.text(0.97, 0.88, World.gold+"");
		 
		 StdDraw.picture(0.7, 0.95, "images/castle.png");
	 }
	 
	
	 /**
	  * Fonction qui recupere le positionnement de la souris et permet d'afficher une image de tour en temps reel
	  *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	  */
	 public void drawMouse() {
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;
		switch (key) {
		case 'a' : 
			StdDraw.picture(normalizedX,normalizedY, "images/archer.png", squareWidth, squareHeight);			 
			 break;
		case 'b' :
			StdDraw.picture(normalizedX,normalizedY, "images/bomb.png", squareWidth, squareHeight);
			 break;
		
		case 'f' : 
			StdDraw.picture(normalizedX,normalizedY, "images/freezing.png", squareWidth, squareHeight);
			break;
		case 'p' :
			StdDraw.picture(normalizedX, normalizedY, "images/poison.png", squareWidth, squareHeight);
			break;
		}
		
	 }
	 
	 
	 /*  
	  * Gestion des tours
	  * 
	  */

	 
	 //Elle Verifie s'il y a un tour dans la case
	 public boolean checkCase(double normalizedX ,double normalizedY){
		 for(Tower t : towerlist) {
			 if(t.getX() == normalizedX && t.getY() == normalizedY) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 //Elle renvoie la tour dans la case
	 public Tower checkTower(double normalizedX,double normalizedY) {

		 for(Tower t: towerlist) {
			 if(t.getX() == normalizedX && t.getY() == normalizedY) {
				 return t;
			 }
		 }
		 return null;
	 }
	 
	 //Elle verifie si on peut construire une tour 
	 public void checkIfBuildable(Tower tour) {
		 if(gold <= 0 || World.gold - tour.getCost() < 0){
			 System.out.println("Vous n'avez pas assez d'or ! ");
		 }
		else {
			towerlist.add(tour);
			World.gold = World.gold - tour.getCost();
	 }
	 }

		/**
		 * Verifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
		 * 		- Ajouter une tour a† la position indiquee par la souris.
		 * 		- Ameliorer une tour existante.
		 * Puis l'ajouter a† la liste des tours
		 * @param x
		 * @param y
		 */
		public void mouseClick(double x, double y) {
			
			double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
			double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		
			//Si on n'a pas encore appuye une cle sur le clavier 
			 if(Character.isSpaceChar(key)) {    
				 if(towerlist.isEmpty())
					 System.out.println("\nVous n'avez pas encore construit une tour !");
				 else 
					 System.out.println("\nAucune touche appuyÈe...");
			 }
			 else {
				 if(grid.getCaseMap(normalizedX, normalizedY).getType().equals("dirt") || grid.getCaseMap(normalizedX, normalizedY).getType().equals("stone") )
					 System.out.println("Vous ne pouvez pas la placer sur le chemin !");
			 
				 else if(checkCase(normalizedX,normalizedY) && key != 'e') {
					 System.out.println("Il y a deja une tour sur cette case !");
				 }
				 else {
					 
					 switch(key) {
					 case 'a': 
						Tower tour1 = new TowerArcher(normalizedX,normalizedY);
						 checkIfBuildable(tour1);
						 break;
					 case 'b':
						Tower tour2 = new TowerCannon(normalizedX,normalizedY);
						 checkIfBuildable(tour2);
						 break;
					
					 case 'f':
						 Tower tour3 = new TowerFreeze(normalizedX,normalizedY);
						 checkIfBuildable(tour3);
						 break;
					 
					 case 'p' :
						 Tower tour4 = new TowerPoison(normalizedX,normalizedY);
						 checkIfBuildable(tour4);
						 break;
					
					//Amelioration de tour
					 case 'e':
						 
						//D'abord on verifie s'il existe une tour dans la case
						 if(checkCase(normalizedX,normalizedY)) {
							 
							 //S'il y existe, on verifie si on a assez d'or
							 if(World.gold <= 0 || World.gold - checkTower(normalizedX,normalizedY).getUpgradeCost() < 0) {
								 System.out.println("Vous n'avez pas assez d'or ! ");
							 }
							//Puis on verifie s'il a pas encore ete ameliore
							 else if(!checkTower(normalizedX,normalizedY).getImproved()) {
								 
								 //On modifie ses parametres
								 double newSpeed =  checkTower(normalizedX,normalizedY).getSpeed() - 20;
								 double newRange = checkTower(normalizedX,normalizedY).getRange() + 0.25;

								 
							 
								 checkTower(normalizedX,normalizedY).setSpeed(newSpeed);
								 checkTower(normalizedX,normalizedY).setRange(newRange);
								 
								 
								 for (Projectile p : checkTower(normalizedX,normalizedY).getProjectiles())
									 p.setDamage(p.getDamage()+2);
							 
								 System.out.println("\nTour ameliorÈe !");
							 
								 checkTower(normalizedX,normalizedY).setImproved(true);
								 World.gold -= checkTower(normalizedX,normalizedY).getUpgradeCost();
								
							 }
							 else
								 System.out.println("\nTour dÈj‡ ameliorÈe");
						 }
						  else 
							 System.out.println("\nAucune tour selectionnÈe");
						 
						 
						 keyPress(' ');
	
						 break;
					 }
				}
			}	
		}
		
		
		 public void updateTowers() {
			 
			 for (Tower t : towerlist) {
				 t.update();
				 t.updateMonsterList(VagueManager.getCurrentWave().getMonsterListRemove());
				 checkCollisions(t);
				 
				 
			 }
			 
		 }
		 
		 //Collision des Monstres
		 public void checkCollisions(Tower t) {
				 
				 for( Projectile p : t.getProjectiles()) {
					 
					 Rectangle2D r1 = p.getBounds();
					 
					for(Monster m : VagueManager.getCurrentWave().getMonsterListRemove()) {
						
						Rectangle2D r4 = new Rectangle2D.Double(m.getX()-0.01,m.getY(),m.getWidht(),m.getHeight());
						
						if(p.getX() <= t.getX() - t.getRange() || p.getX() >= t.getX() + t.getRange() 
								|| p.getY() <= t.getY() - t.getRange() || p.getY() >= t.getY() + t.getRange())
							p.setVisible(false);
						
						
						//Si le montre est aerien et la tour de bombes il n'existe pas de collision
						
						if(!(m.getType().equals(monsterType.AERIAL) && t.getTowerType().equals(TowerType.BOMBER))){
							
							if(r1.intersects(r4)) {
								StdDraw.picture(m.getX(), m.getY(), "images/explosion.png");
								p.setVisible(false);
								p.damage(m);
							
						}	
						}
					}
						
				}
			}
		
		
		 

		 
	 
	/**
	 * Recupere la touche appuyee par l'utilisateur et affiche les informations pour la touche selectionnee
	 * @param key la touche utiliseee par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		switch (key) {
		case ' ' :
			System.out.println("\nVeuillez appuyer une touche:"
					+ "\nA pour Arrow Tower (cost 50g)" + 
					"\nB pour Cannon Tower (cost 60g)" + 
					"\nF pour Freeze Tower (cost 100g)" + 
					"\nP pour Poison Tower (cost 100g)"+
					"\nE pour amelioration");
			break;
		case 'a':
			System.out.println("\nArrow Tower selected (50g).");
			break;
		case 'b':
			System.out.println("\nBomb Tower selected (60g).");
			break;
		case 'f':
			System.out.println("\nFreeze Tower selected (100g)");
			break;
		case 'p':
			System.out.println("\nPoison Tower selected (100g)");
			break;
		case 'e':
			if(towerlist.size() ==0) {
				 System.out.println("\nVous n'avez pas encore construit une tour !");
				 keyPress(' ');
			}
			else
				System.out.println("\nEvolution selected (40g).");
			break;

		}
	}
	
	/**
	 * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les diff√©rentes possibilit√©s 
	 * offertes au joueur pour int√©ragir avec le clavier
	 */
	public void printCommands() {
		System.out.println("Press A to select Arrow Tower (cost 50g).");
		System.out.println("Press B to select Cannon Tower (cost 60g).");
		System.out.println("Press F to select Freeze Tower (cost 100g).");
		System.out.println("Press P to select Poison Tower (cost 100g).");
		System.out.println("Press E to update a tower (cost varies!).");
		
	}

	
	 
	 public boolean CheckDefeat() {
		 if(life == 0) {
			 VagueManager.getCurrentWave().deleteAll();
			 gameLost= true;
			 
		 }else {
			 gameLost= false;
		 }
		 return gameLost;
	 }
	 
	 
	public void drawDefeat() {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledRectangle(0.5, 0.5, 0.2, 0.2);
			StdDraw.setPenColor(StdDraw.WHITE);
			Font font = new Font("Arial", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.text(0.5, 0.5, "GAME OVER");
			StdDraw.show();
		}
	 
	 
	 //Le jeu s'arrete lorsque le jouer a vaincu les N vagues
	 public boolean CheckVictory() {
		 if(VagueManager.getVagueN() > VagueManager.getNumberWaves() && VagueManager.getCurrentWave().isCompleted()) {
			 gameWon = true;
		 }
		 else {
			 gameWon = false;
		 }
	 return gameWon;
	 }
	 
	 
	public void drawVictory() {
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledRectangle(0.5, 0.5, 0.2, 0.2);
		StdDraw.setPenColor(StdDraw.WHITE);
		Font font = new Font("Arial", Font.BOLD, 20);
		StdDraw.setFont(font);
		StdDraw.text(0.5, 0.5, "YOU WIN!");
		StdDraw.show();
		}
	 
	
	 /**
	  * Met √† jour toutes les informations du plateau de jeu ainsi que les d√©placements des monstres et les attaques des tours.
	  * @return les points de vie restants du joueur
	  */
	 public void update() {
		
		 
		CheckDefeat();
		CheckVictory();
		grid.draw();
		updateTowers();
		drawInfos();
		VagueManager.update();
		drawMouse();

		
	 }
	 
	
	/**
	 * Recupere la touche entree au clavier ainsi que la position de la souris et met a† jour le plateau en fonction de ces interractions
	 */
	public void run() {
		printCommands();
		while(!end) {
			
			StdDraw.clear();
			if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}
			
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(50);
			}
			
			update();
			
			
			if(gameLost){
				drawDefeat();
				end = true;
			}
			else if(gameWon) {
				drawVictory();
				end=true;
			}
			StdDraw.show();
			StdDraw.pause(20);			
		}
	}
}
