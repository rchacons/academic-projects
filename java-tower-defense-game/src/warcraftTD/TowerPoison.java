package warcraftTD;

import java.awt.Font;

public class TowerPoison extends Tower {
	
	public TowerPoison(double x, double y) {
		super(x,y);
		this.setCost(100);
		this.setUpgradeCost(100);
		this.setSpeed(70);  //Set to 100 wich is like 7 secs
		this.setRange(0.2f);
		this.setImg("images/PoisonTower.png");
		this.setTowerType(TowerType.POISON);
		
		
		
	}
	
	@Override
	public void shoot(Monster target) {
		 
		
		Projectile vaccine = new ProjectilePoison(super.getTarget(),super.getX() ,super.getY(), super.getAngle());
		
		if(this.getImproved())
			vaccine.setDamage(vaccine.getDamage()+2);
			vaccine.setPoisonDamage(2);
		
		super.getProjectiles().add(vaccine);
		
		

	}
	
	@Override
	public void draw() {
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidth(),this.getHeight());
		
		//Affichage de l'arbalete avec rotation
		StdDraw.picture(this.getX()+0.003, this.getY()+0.03, "images/PoisonGun.png", this.getWidth()/1.5,this.getHeight()/1.5,this.getAngle());
		
		
		//On dessin un "+" au bas de la tour
		if(this.getImproved()) {
			StdDraw.setPenColor(StdDraw.WHITE);
			Font font = new Font("WHITE", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.text(this.getX(), this.getY()-0.05, "+");
		}
	}
	
}
