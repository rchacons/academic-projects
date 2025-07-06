package warcraftTD;

public class ProjectileIceball extends Projectile {
	
	
	public ProjectileIceball(Monster target, double x, double y, double angle) {
		super(target,x,y, angle);
		this.setSpeed(0.02);  ;
		this.setDamage(1);
		this.setImg("images/freezing.png");
		this.setType(projectileType.ICEBALL);
		
	}
	
	@Override
	public void draw() {
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht()/1.5,this.getHeight()/1.5, this.getAngle()-135);
	}

}
