package warcraftTD;

import java.awt.Font;


public class TowerArcher extends Tower {
	
	public TowerArcher(double x, double y) {
		super(x,y);
		this.setCost(50);
		this.setUpgradeCost(50);
		this.setSpeed(70);
		this.setRange(0.3f);
		this.setImg("images/ArcherTower.png");
		this.setTowerType(TowerType.ARCHER);

		
	}
	
	@Override
	
	public void shoot(Monster target) {
	
		Projectile arrow = new ProjectileArrow(super.getTarget(),super.getX() ,super.getY(), this.getAngle());
	
		if(this.getImproved())
			arrow.setDamage(arrow.getDamage()+2);
	
		super.getProjectiles().add(arrow);
	
	}
	
	@Override
	public void draw() {
		//Affichage de la tour
		StdDraw.picture(this.getX(),this.getY(), this.getImg(),this.getWidth(),this.getHeight());
		
		//Affichage de l'arbalete avec rotation
		StdDraw.picture(this.getX()+0.002, this.getY()+0.04, "images/crossbow1.png", this.getWidth()/2.5,this.getHeight()/2.5,this.getAngle()+45);
		
		//On dessin un "+" au bas de la tour
		if(this.getImproved()) {
			StdDraw.setPenColor(StdDraw.WHITE);
			Font font = new Font("WHITE", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.text(this.getX(), this.getY()-0.05, "+");
		}
		
	}


}
