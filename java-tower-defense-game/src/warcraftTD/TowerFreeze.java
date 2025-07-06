package warcraftTD;

import java.awt.Font;


public class TowerFreeze extends Tower {
	
	public TowerFreeze(double x, double y) {
		super(x,y);
		this.setCost(100);
		this.setUpgradeCost(100);
		this.setSpeed(70);
		this.setRange(0.3f);
		this.setImg("images/FreezeTower.png");
		this.setTowerType(TowerType.FREEZE);
	
		
	}
	
	@Override
	public void shoot(Monster target) {

		Projectile ice = new ProjectileIceball(super.getTarget(),super.getX() ,super.getY(), super.getAngle());
	
		if(this.getImproved())
			ice.setDamage(ice.getDamage()+2);
	
		super.getProjectiles().add(ice);
	
	}
	
	@Override
	public void draw() {
		
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidth(),this.getHeight());
		
		
		//Affichage du canon à glace avec rotation
		StdDraw.picture(this.getX()+0.002, this.getY()+0.04, "images/IceCanon.png", this.getWidth()/2,this.getHeight()/2,this.getAngle());
		
		
		//On dessin un "+" au bas de la tour
		if(this.getImproved()) {
			StdDraw.setPenColor(StdDraw.WHITE);
			Font font = new Font("WHITE", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.text(this.getX(), this.getY()-0.05, "+");
		}
	}

}
