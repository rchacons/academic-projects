package warcraftTD;

public class MonsterA extends Monster {
	
	
	public MonsterA(double x, double y) {
		super(x,y);
		this.setHealth(3);
		this.setSpeed(0.01);  //Attention elle doit pas depasser les 0.02
		this.setDamageCastle(1);
		this.setRecompense(10);
		this.setImg("images/dragon.png"); 
		this.setType(monsterType.AERIAL);
	
		
	}
		

	@Override
	public void draw(){
		
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht(), this.getHeight());
		
		if(this.isPoisoned()) {
			StdDraw.picture(this.getX(), this.getY()-0.04, "images/poison.png", this.getWidht()/3, this.getHeight()/3);

		}
		
	}
}
