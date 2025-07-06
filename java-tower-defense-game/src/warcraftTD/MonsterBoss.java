package warcraftTD;


public class MonsterBoss extends Monster {
	
	public MonsterBoss(double x, double y) {
		super(x,y);
		this.setHealth(10);
		this.setSpeed(0.007);  //Attention elle doit pas depasser les 0.02
		this.setDamageCastle(3);
		this.setRecompense(15);
		this.setImg("images/golem.png"); 
		this.setType(monsterType.BOSS);
		
	}
		

	@Override
	public void draw(){
		
		StdDraw.picture(this.getX(),this.getY(), this.getImg(), this.getWidht(), this.getHeight());
		
		if(this.isPoisoned()) {
			StdDraw.picture(this.getX(), this.getY()-0.04, "images/poison.png", this.getWidht()/3, this.getHeight()/3);

		}
		
	}

}
