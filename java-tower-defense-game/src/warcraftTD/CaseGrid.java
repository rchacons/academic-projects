package warcraftTD;

//Classe qui sert a creer un Grid 2d des Cases et qui nous aidera a manipuler chacune
public class CaseGrid {
	
	private Case [][] map;		//Double array des Cases
	private int NbSquareX, NbSquareY;
	double squareW,squareH;


	
	public CaseGrid(int [][] newMap){    //Il prend le map definie dans la classe World(10x15)  
		
		//Pour pouvoir afficher correctement le map[][] dans la fenetre graphique, on doit inverser ses valeurs et creer un nouvel map
			this.NbSquareX = newMap[0].length; //15
			this.NbSquareY = newMap.length;	   //10
			this.squareW = (double)1/NbSquareX;
			this.squareH = (double)1/NbSquareY;
			
			map = new Case[NbSquareX][NbSquareY];  //On cree le map de Cases (15x10) 
			
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
	
					if(newMap[j][i]==0)  //Si les valeurs dans le map[][] sont 0/1 on definira le type de la Case comme grass/dirt 
						
						map[i][j] = new Case(i,j,squareW, squareH, "grass");
					else if (newMap[j][i]==1)
						map[i][j] = new Case(i, j, squareW, squareH, "dirt");
					else
						map[i][j] = new Case(i,j,squareW, squareH, "stone");
				}	
			}
	}
	
	
	public void setCase(int xCoord, int yCoord,String type) {
		map[xCoord][yCoord] = new Case(xCoord,yCoord,1/NbSquareX,1/NbSquareY,type);
		
	}
	
	
	public Case getCaseMap(double normalizedX, double normalizedY) {
		
		/*
		 * Les X,Y sont positions normalises aux positions de la fenetre graphique. Alors pour connaitre la Case correspondant a 
		 * cette position on doit les transformer.
		 */
		
		int x = (int)((normalizedX - this.squareW/2)/this.squareW);
		int y = (int)((normalizedY - this.squareH/2)/this.squareH);
		if(x < NbSquareX && y < NbSquareY && x > -1 && y > -1)
			return map[x][9-y];
		else
			return new Case(0,0,0,0, "Null");
		
	}
	
	
	public void draw() {
	
		for (int i=0; i < map.length;i++) {
			for(int j=0; j<map[i].length;j++) {
					map[i][j].draw(); //On appelle le methode draw de la classe Case
					
			}
		}
		
	}

	public int getNbSquareX() {
		return NbSquareX;
	}

	public void setNbSquareX(int nbSquareX) {
		NbSquareX = nbSquareX;
	}

	public int getNbSquareY() {
		return NbSquareY;
	}

	public void setNbSquareY(int nbSquareY) {
		NbSquareY = nbSquareY;
	}


	
	
	
}
