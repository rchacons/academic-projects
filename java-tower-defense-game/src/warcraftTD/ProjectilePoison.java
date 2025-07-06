package warcraftTD;



public class ProjectilePoison extends Projectile {
		
		
		public ProjectilePoison(Monster target, double x, double y, double angle) {
			
			super(target,x,y, angle);
			this.setSpeed(0.01);  ;
			this.setDamage(1);
			this.setImg("images/syringe.png");
			this.setType(projectileType.POISON);
			this.setPoisonDamage(1);
		

		}
		
		@Override
		public void draw() {
			StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht()/1.5,this.getHeight()/1.5, this.getAngle()+45);
		}


}
