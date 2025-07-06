package warcraftTD;


public abstract class Monster {
	
	public enum monsterType{
		BASE, AERIAL, BOSS;
	}
	private int health;		// Vie du monstre
	private double speed;	// Vitesse du monstre
	private double width,height;  //Taille du monstre
	Position p;		// Position du monstre a l'instant t
	Position nextP;	// Position du monstre a l'instant t+1
	Case actual;
	private boolean reached;	// Boolean pour savoir si le monstre a atteint le chateau du joueur
	private String img; 	//Path de l'image
	private monsterType type;
	private int lastdir;   	//Directions de deplacement 1 = Down, 2= Up, 3= Right , 4=Left
	private int recompense;
	private int damageCastle;
	private boolean alive;
	
	//Dans le cas ou le monstre est infecté par la Tour Poison
	private boolean poisoned;
	private int poisonDamage;
	private int count;
	

	
	
	public Monster(double x, double y) {
		this.p= new Position(x,y);
		this.nextP = new Position(x,0);
		this.speed= 0;
		this.health= 0;
		this.damageCastle=0;
		this.recompense=0;
		this.alive=true;
		this.width = World.squareWidth;
		this.height= World.squareHeight;
		this.lastdir=1;
		this.poisoned = false;
		this.count =0;
		

	}
	
	
	/**
	 * Deplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	
	public void move() {
		

		//Si le monstre a atteint le chateau
		if(checkpointReached()) {
			World.life -= damageCastle;  //On reduit la vie du joeur
			this.alive = false; //On retire le monstre 
			
		}
		else {
			double dx = nextP.x - p.x ;
			double dy = nextP.y - p.y;
			
			if (dy + dx != 0){
				// Mesure la distance a laquelle le monstre a pu se deplacer.
				double ratioX = dx/(Math.abs(dx) + Math.abs(dy));
				double ratioY = dy/(Math.abs(dx) + Math.abs(dy));
				p.x += ratioX * speed;
				p.y += ratioY * speed;
				
				//On lance la method pour chercher la direction de deplacement avec le carreau actuel (utilisant la position du monstre)
				findNextD(World.grid.getCaseMap(p.x, p.y));
				
			}
		}
		
		
	
	
	}
	
	//Points de degats des projectiles
	public void damage(int amount) {
		health -= amount;
		if(health <= 0) {
			alive = false;
			poisoned = false;
			count = 0;
			World.gold += this.getRecompense();
		}
	}
	
	

	
	public boolean checkpointReached() {
		boolean reached = false;
		Position p1 = new Position(this.p);
		
		Position p2 = new Position(0.7,0.95); //Position de notre chateau 
		
		

		int difX = (int)((p1.x*100) - (p2.x*100));
		int difY = (int)((p1.y*100) - (p2.y*100));
		
		if(difX <= 1 && difX >= 0 && difY ==0) {
			reached = true;
		}
		return reached;
	}
	
	

	//Trouver la prochaine direction
	private void findNextD(Case actual) {
		int check=lastdir;
		
		
		
		//Case superieur
		Case u = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()+0.1);
		
		//Case inferieur
		Case d = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()-0.1);
		
		//Case droit
		Case r = World.grid.getCaseMap(actual.getXNor()+0.0666667,actual.getYNor()); 
		
		//Case Gauche
		Case l = World.grid.getCaseMap(actual.getXNor()-0.066, actual.getYNor());  
		
		
		
		/*
		 * Afin d'obtenir la position quand le monstre est au centre du carreau, on calcule la difference entre la position actual du monstre 
		 * et la position de la case dans laquelle il se deplace, et on s'assure qu'elle soit minimale.
		 * 
		 */
		
		int difX = (int)((this.p.x*1000)-(actual.getXNor()*1000));
		int difY = (int)((this.p.y*1000) -(actual.getYNor()*1000));
		
		//Le monstre est-il au centre du carreau?
		if( difY <= 19 && difY >= -10 && difX >= -6 && difX <=6) {
			
			/*Pour visualiser les types de textures autour de la position actual du monstre
			
			System.out.println("The type of upper tile is: "+ u.getType());
			System.out.println("Thy type of lower tile is: "+ d.getType());
			System.out.println("The type of right tile is: "+ r.getType());
			System.out.println("The type of left tile is: "+ l.getType());
			
			*/
			
			//Condition qui sert a fixer le rang de la case inferieur de la fenetre(Pour que le monstre ne la depasse pas)
			if((int)(actual.getYNor()*100) == 5) {
				d = World.grid.getCaseMap(actual.getXNor(), actual.getYNor()-0.12);
			}
			

			//On verifie la direction dans laquelle le monstre se dirige
			switch(check) {				
	
			//Le monstre se dirige vers le bas
			case 1: {
	
				if(actual.getType() == d.getType()) {   //Si la texture du carreau inferieur est egale a celle du carreau actual
					nextP = new Position (this.p.x+0.0001,0); //On continue vers le bas
					this.lastdir =1;
				}
				else if (actual.getType() == r.getType()) { //Si la texture du carreau droit est egale a celle du carreau actual
					nextP = new Position (1,this.p.y);   //On change la direction a droite
					this.lastdir = 3;
				}
				else if (actual.getType() == l.getType()) {  //Si la texture du carreau gauche est egale a celle du carreau actual
					nextP = new Position (0,this.p.y);		//On change la direction a gauche
					this.lastdir =4;
				}
				break;
			}
			//Le monstre se dirige vers le haut
			case 2: {
				
				//Les memes conditions que ci-dessus seulement cette fois  on veut continuer vers le haut
				if(actual.getType() == u.getType()) {
					nextP = new Position (this.p.x,1);
					this.lastdir =2;
				}
				else if (actual.getType() == r.getType()) {
					nextP = new Position (1,this.p.y);
					this.lastdir = 3;
				}
				else if (actual.getType() == l.getType()) {
					nextP = new Position (0,this.p.y);
					this.lastdir =4;
				}
				break;
			}
				//Le monstre se dirige vers la droite
				case 3 : {
					
					//Les memes conditions que ci-dessus seulement cette fois  on veut continuer vers la droite
					if(actual.getType().equals(r.getType())) {
						nextP = new Position(1,this.p.y);
						this.lastdir= 3;
					}
					else if(actual.getType().equals(u.getType())) {
						nextP = new Position(this.p.x,1);
						this.lastdir = 2;
						
					}
					else if(actual.getType().equals(d.getType())) {
						nextP = new Position(this.p.x,0.05);
						this.lastdir = 1;
					}
					break;
					
				}
				
				//Le monstre se dirige vers la gauche
				case 4 : {
					
					//Les memes conditions que ci-dessus seulement cette fois  on veut continuer vers la gauche
					if(actual.getType() == l.getType()) {
						nextP = new Position (0,this.p.y);
					}
					else if (actual.getType() == u.getType()) {
						nextP = new Position (this.p.x,1);
						this.lastdir = 2;
					}
					else if (actual.getType() == d.getType()) {
						nextP = new Position (this.p.x,0);
						this.lastdir =1;
					}
					
					break;
					
				}
			}
		}
	}
	
	//Si le monstre est infecté, il sera affecté par le poison chaque 60 cycles 
	public void checkifPoisoned() {
		
		if(this.isAlive() && this.isPoisoned())
			if(count >= 60) {
				damage(poisonDamage);
				count = 0;
			}
			else
				count ++;
		
	}

	


	public void update() {
		
		checkifPoisoned();	
		move();
		draw();
	}
	
	
	
	
	/**
	 * Fonction abstraite qui sera instanciee dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public abstract void draw();
	
	//Il renvoie le type de carreau dans la position p
	public String traduceMap(Position p) {
		
		return World.grid.getCaseMap(p.x,p.y).getType();
		
	}
	
	public boolean isAlive() {
		return alive;
	}

	public int getHealth() {
		return health;
	}
	
	public void reduceHealth(int amount) {
		health -= amount;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void inflictsPoison(int amount) {
		this.poisonDamage = amount;
		this.setPoisoned(true);
	}

	public Position getPosition() {
		return p;
	}

	public void setPosition(Position position) {
		this.p = position;
	}
	
	public double getX() {
		return this.p.x;
	}
	
	public double getY() {
		return this.p.y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if(speed <= 0.02)
			this.speed = speed;
		else
			System.out.println("\n***Vitesse du monstre très élevée***\n");
	}

	public Position getNextP() {
		return nextP;
	}

	public void setNextP(Position nextP) {
		this.nextP = nextP;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	

	public int getRecompense() {
		return recompense;
	}

	public void setRecompense(int recompense) {
		this.recompense = recompense;
	}


	public double getWidht() {
		return width;
	}


	public void setWidht(double widht) {
		this.width = widht;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public monsterType getType() {
		return type;
	}



	public void setType(monsterType type) {
		this.type = type;
	}


	public boolean isPoisoned() {
		return poisoned;
	}


	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}


	public int getDamageCastle() {
		return damageCastle;
	}


	public void setDamageCastle(int damageCastle) {
		this.damageCastle = damageCastle;
	}
	
}
	
	
	