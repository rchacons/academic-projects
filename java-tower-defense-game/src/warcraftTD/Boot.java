package warcraftTD;


public class Boot {
	
	public int width = 960, height = 640;
	public int NbSquareX=15;
	public int NbSquareY=10;
	public int StartX = 1;
	public int StartY = 9;
	public double squareWidth = 1/NbSquareX;
	public double squareHeight = 1/NbSquareY;

	
	int [][] map = {
			{0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,2 ,0 ,0 ,0 ,0 },
			{0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 },
			{0 ,1 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,0 ,1 ,1 ,1 ,1 ,0 },
			{0 ,1 ,0 ,0 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,1 ,0 ,0 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,1 ,1 ,1 ,1 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,1 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 ,0 },
			{0 ,0 ,0 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,0 },
			
	};
	
	
	public Boot() {
		
		World w = new World(width, height, NbSquareX, NbSquareY, StartX, StartY, map);
		w.run();
		
	}


	public static void main(String[] args) {
		
		new Boot();

	}
}
	
