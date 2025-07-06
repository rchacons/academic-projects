package warcraftTD;

public class Case {
	
	private double x,y;
	double width, height; 
	private String type;    //Type de texture soit grass soit dirt
	private boolean taken;  //Verifie si la Case est deja occupé
	
	//Constructeur
	public Case(double x, int y, double width, double height, String type) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.type=type;
	}
	

	//La methode Draw qui affichera l'image correspondant au type de la Case
	public void draw() { 
	
		switch(type) { 
		case "grass":
			StdDraw.picture((x * width) + (width / 2),1.0-(y * height + height / 2), "images/grass.png");

			break;
		case "dirt":
			StdDraw.picture(x * width + width / 2, 1.0-(y * height + height / 2), "images/dirt1.png");
			break;
			
		case "stone":
			StdDraw.picture(x * width + width / 2, 1.0-(y * height + height / 2), "images/stone.png");
			break;
		}
			

	}
	


	//Getters et Setters
	public double getX() {
		return x;
	}
	
	/*
	 * 
	 * Afin de traduire les valeurs X et Y  du carreau aux positions de la fenetre graphique(de StdDraw):
	 * 
	 * (0.0, 1.0)        (1.0,1.0)
	 * 		----------------
	 * 		|				|
	 * 		|				|
	 * 		|				|
	 * 	    -----------------		
	 * (0.0 , 0.0)		 (1.0, 0.0)
	 * 
	 * Il faut appeller les methodes getXNor et getYNor.
	 */
	public float getXNor() {
		return (float)((x*this.width + (width/2))+0.0067);
	}
	
	public float getYNor() {
		return (float)(0.9000001-y*this.height + height/2);
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getTaken() {
		return taken;
	}
	
	public void setTaken(boolean taken) {
		this.taken=taken;
	}
	
	public Position getPosition() {
		return new Position(this.x,this.y);
	}
}
