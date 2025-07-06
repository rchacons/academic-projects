package warcraftTD;

import java.util.*;

import warcraftTD.Monster.monsterType;

public abstract class Tower {
	
	public enum TowerType{
		BOMBER, ARCHER, FREEZE, POISON 
	}
	private double x,y;
	private double reloadTime;	 //Vitesse de rechargement 
	private double range;     //Portee de la tour
	private double width;
	private double height;
	private boolean improved; //Pour savoir si elle a été ameliorée ou pas
	private int cost, upgradeCost;
	private String img;  //Path de l'image
	private TowerType type;
	private Monster target;  //L'objectif de la tour
	private boolean targeted;
	private float timeSinceShot;
	private List<Monster> monsters = new LinkedList<Monster>();
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	private double angle;

	
	
	
	public Tower(double x, double y) {
		this.x=x;
		this.y=y;
		this.width = World.squareWidth;
		this.height= World.squareHeight;
		this.range=0;
		this.cost=0;
		this.upgradeCost=0;
		this.reloadTime =0;
		this.improved = false;
		this.timeSinceShot = 0f;
		this.targeted = false;
		this.angle = 0;
		this.target = null;
		
	}
	
	
	//Si le monstre est à portée, elle le vise
	public Monster targetEnemy() {
		
		
		monsters = World.VagueManager.getCurrentWave().getMonsterListRemove();
		
	
		if(target == null || !(isInRange(target)) || !this.target.isAlive()) {
		
		//Parcourt chaque monstre and renvoi celui qui est le plus proche (tant qu'il soit vivant)
		 for(Monster m: monsters) {
			 
			//Si le montre est aerien et la tour de bombes , elle ne le vise pas
			if(!(m.getType().equals(monsterType.AERIAL) && this.getTowerType().equals(TowerType.BOMBER))) {
				
				if(isInRange(m)) {
					target = m;
					targeted = true;
				}	

			}
		 }
		 
		}
		else 
			targeted = true;
		
		return target;
		
	}
	
	//Verifie que la distance du monstre ne depasse pas la portee de la tour
	public boolean isInRange(Monster e) {
		double xDistance = Math.abs(e.getX() - x);
		double yDistance = Math.abs(e.getY() -y);

		
		if(xDistance < range && yDistance < range) {
			return true;
		}

		return false;
	}
	
	
	//Calcule l'angle des monstres par rapport a la tour, utile pour savoir la rotation de l'image du projectile
	public double calculateAngle() {
		double angleTemp = Math.atan2(World.spawn.y - y, World.spawn.x - x);
		
		if(!(this.target==null) ) {
			angleTemp = Math.atan2(this.target.getY() - y, this.target.getX() - x);
		}
	

		return  Math.toDegrees(angleTemp)-90;
	}
	


	public void updateMonsterList (List<Monster> newList) {
		this.monsters = newList;
	}
	
	//Mettre a jour les projectiles
	public void updateProjectiles() {

		for(int i = 0 ; i < projectiles.size(); i++) {
			
			Projectile p = projectiles.get(i);
			
			if(p.isVisible()) {
				p.move();
			} else {
				projectiles.remove(i);
			}
		}
	}
	
	public void update() {
		
		this.target =targetEnemy();
		
		if(target != null && (timeSinceShot > reloadTime) && this.target.isAlive()) {
					
					shoot(this.target);
					timeSinceShot = 0;
				

		} else if(this.target == null || this.target.isAlive() == false) {
			targeted =false;
			this.target = targetEnemy();
		
		}

		timeSinceShot += 1;
		updateProjectiles();
		this.angle=calculateAngle();
		draw();
	}
	

	
	// Fonction abstraite qui sera instanciee dans les classes filles 
	public abstract void shoot(Monster target);
	
	// Fonction abstraite qui sera instanciee dans les classes filles pour afficher le monstre sur le plateau de jeu.
	public abstract void draw();

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}


	public double getSpeed() {
		return reloadTime;
	}

	public void setSpeed(double speed) {
		this.reloadTime = speed;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}


	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean getImproved() {
		return improved;
	}

	public void setImproved(boolean improved) {
		this.improved = improved;
	}

	public Monster getTarget() {
		return target;
	}

	public void setTarget(Monster target) {
		this.target = target;
	}

	public boolean isTargeted() {
		return targeted;
	}

	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}

	public float getTimeSinceShot() {
		return timeSinceShot;
	}

	public void setTimeSinceShot(float timeSinceShot) {
		this.timeSinceShot = timeSinceShot;
	}


	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public TowerType getTowerType() {
		return type;
	}

	public void setTowerType(TowerType newtowerType) {
		type = newtowerType;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getUpgradeCost() {
		return upgradeCost;
	}

	public void setUpgradeCost(int upgradeCost) {
		this.upgradeCost = upgradeCost;
	}

}
