package warcraftTD;


public class ProjectileBomb extends Projectile {
	
	
	public ProjectileBomb(Monster target, double x, double y, double angle) {
		super(target,x,y, angle);
		this.setSpeed(0.02);  ;
		this.setDamage(8);
		this.setImg("images/bomb (4).png");
		this.setType(projectileType.BOMB);
	}
	
	@Override
	public void draw() {
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht()/1.5,this.getHeight()/1.5);
	}


}
