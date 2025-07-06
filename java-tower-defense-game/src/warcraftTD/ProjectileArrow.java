package warcraftTD;


public class ProjectileArrow extends Projectile{
	
	public ProjectileArrow(Monster target, double x, double y, double angle) {
		super(target,x,y, angle);
		this.setSpeed(0.04);  ;
		this.setDamage(2);
		this.setImg("images/arrow.png");
		this.setType(projectileType.ARROW);
	}
	
	@Override
	public void draw() {
		
	StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht()/1.5,this.getHeight()/1.5, this.getAngle());
	}

}
	
	
