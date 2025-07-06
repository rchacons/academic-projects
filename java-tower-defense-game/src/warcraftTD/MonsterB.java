package warcraftTD;


public class MonsterB extends Monster {


	public MonsterB(double x, double y) {
		super(x,y);
		this.setHealth(5);
		this.setSpeed(0.005);  //Attention elle ne doit pas depasser les 0.02
		this.setDamageCastle(1);
		this.setRecompense(10);
		this.setImg("images/monster1.png"); 
		this.setType(monsterType.BASE);
	}
	
	

	@Override
	public void draw() {
		
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht(), this.getHeight());

		if(this.isPoisoned()) {
			StdDraw.picture(this.getX(), this.getY()-0.04, "images/poison.png", this.getWidht()/3, this.getHeight()/3);

		}
	}
}
