package warcraftTD;
import java.awt.Font;


public class TowerCannon extends Tower {
	
	public TowerCannon(double x, double y) {
		super(x,y);
		this.setCost(60);
		this.setUpgradeCost(60);
		this.setSpeed(100);  
		this.setRange(0.2f);
		this.setImg("images/BombTower.png");
		this.setTowerType(TowerType.BOMBER);
		
		
	}
	
	@Override
	public void shoot(Monster target) {
		 
		Projectile bomb = new ProjectileBomb(super.getTarget(),super.getX() ,super.getY(), this.getAngle());
		
		if(this.getImproved())
			bomb.setDamage(bomb.getDamage()+2);
		
		super.getProjectiles().add(bomb);
		
		

	}
	
	@Override
	public void draw() {
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidth(), this.getHeight());
		
		//Affichage du canon à bombes avec rotation
		StdDraw.picture(this.getX()+0.002, this.getY()+0.04, "images/canon1.png", this.getWidth()/2,this.getHeight()/2,this.getAngle());
		
		
		//On dessin un "+" au bas de la tour
		if(this.getImproved()) {
			StdDraw.setPenColor(StdDraw.WHITE);
			Font font = new Font("WHITE", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.text(this.getX(), this.getY()-0.05, "+");
		}
	}

}
