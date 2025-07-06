package warcraftTD;

import java.awt.geom.Rectangle2D;

public abstract class Projectile {
	
	public enum projectileType{
		BOMB, ARROW, ICEBALL, POISON 	
		}
	private String img;
	private projectileType type;
	private double x,y;
	private double xVelocity, yVelocity;
	private double distanceFromTarget;
	private double speed;  //Vitesse de deplacement
	private double width, height;  
	private int damage; //Points de degats
	private double angle;
	private Monster target;	  //Le monstre auquel on va attaquer
	private boolean visible;  //Si le projectile est dans le jeu
	private boolean locked;
	private double range;
	private int poisonDamage; 
	


	
	public Projectile(Monster target,double x, double y, double angle) {
			
		this.x=x;
		this.y=y;
		this.width= World.squareWidth/1.5;
		this.height = World.squareHeight/1.5;
		this.angle = angle;
		this.speed=0;
		this.damage= 0;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		this.target = target;
		this.visible = true; 
		this.locked = false;


		
	}
	
	//Pour definir le hitbox de projectiles
	
	public Rectangle2D getBounds() {
		
		Rectangle2D rect = new Rectangle2D.Double(this.x,this.y,this.width/1.5,this.height/1.5);
		
		return rect;
	}
	
	
	//On calcule la direction du projectil en fonction de celle du monstre
	public void calculateDirection() {
		
		if(!locked) {
		double movementAllowed= 1.05;
		
		double xDistanceFromTarget= Math.abs(target.getX()- x );
		double yDistanceFromTarget = Math.abs(target.getY()- y);
		
		distanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;		
		
		double xPercentOfMovement = xDistanceFromTarget / distanceFromTarget;
		
		xVelocity =  xPercentOfMovement ; 
		yVelocity = movementAllowed - xPercentOfMovement;
		
		
		//On definie la direction de l'objectif en fonction de la tour
		if(target.getX() < x)
			xVelocity *= -1;
		if(target.getY() < y)
			yVelocity *= -1;
		
		this.locked=true;
		}
		
	}

	
	//Inflige des degats au monstre et disparaître du jeu
	public void damage(Monster monster) {
		
		//Si c'est un projectile de glace, on ralenti la vitesse du monstre
		if(this.type.equals(projectileType.ICEBALL)) {
			monster.setSpeed(monster.getSpeed()/1.5);
		} 
		
		//Si c'est un projectile de poison, on inflige du poison
		if(this.type.equals(projectileType.POISON)) {
			monster.inflictsPoison(this.poisonDamage);
		}
		monster.damage(damage);
		visible = false;
	}
	
	
	public void move() {
		
		if(visible) {
			calculateDirection();
			
			x +=  xVelocity*speed;
			y +=  yVelocity*speed;
			
			//Le monstre n'est plus vivant ou il a atteint le chateau ou le projectile est sorti du map
			if(!target.isAlive() || target.isReached() || (x <= 0 || x >= 1 || y <= 0 || y >= 1))
				visible = false;
			
		}
		
		draw();
				
	}
	
	
	
	
	public abstract void draw();

	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

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

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}


	public void setHeight(int height) {
		this.height = height;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Monster getTarget() {
		return target;
	}

	public void setTarget(Monster target) {
		this.target = target;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public double getDistanceFromTarget() {
		return distanceFromTarget;
	}

	public void setDistanceFromTarget(double distanceFromTarget) {
		this.distanceFromTarget = distanceFromTarget;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public projectileType getType() {
		return type;
	}

	public void setType(projectileType type) {
		this.type = type;
	}

	public int getPoisonDamage() {
		return poisonDamage;
	}

	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}




}
