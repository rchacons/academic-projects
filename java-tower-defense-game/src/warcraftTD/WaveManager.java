package warcraftTD;




public class WaveManager {
	
	private double timeBetweenMonsters; //Le temps de spawn entre chaque monstre
	Position spawn;
	private Timer delayStartWave;		//Delai de debut du jeu
	private Timer delayVague;			//Delai entre chaque Vague
	private int bonusWaveOver = 100;	//L'or de recompense pour avoir battu la vague
	private Wave currentWave;
	boolean timercreated;
	private int actualWave,NbMonst, NHealth, numberWaves;
	
	
	public WaveManager(Position spawn, double timeBetweenMonsters, int NbMonst) {
		this.spawn=spawn;
		this.timeBetweenMonsters = timeBetweenMonsters;
		this.NbMonst = NbMonst;
		this.actualWave = 1;
		this.currentWave = null;
		this.delayStartWave = new Timer(20_000);		//Milliseconde
		this.timercreated = false;
		this.numberWaves = 8;
		newWave();
	}
	



	
	private void newWave() {
		
		//On verifie le delai du debut 
		if(!delayStartWave.hasFinished()) {
			
			//S'il reste 3 secondes pour que la vague comence
			if(delayStartWave.printMessage()) {
				System.out.println("\nLES MONSTRES ARRIVENT !!!");
				
			}
			//Tant qu'il reste de secondes , on cree une fausse vague
			currentWave = new Wave(spawn, 0,0,0);
			
		}//Si c'est la premier vague on la cree normalement
		else if(actualWave == 1) {
				System.out.println("Vague: "+actualWave+"\tMonstres à vaincre: "+ NbMonst+ "");
				currentWave = new Wave(spawn, timeBetweenMonsters, NbMonst, NHealth);
				actualWave++;
	
			}
		
		//Vague 2 et plus
		else{
			
			
			//On cree un delai pour chaque vague different a celui du debut
			if(!timercreated) {
				delayVague = new Timer(7_000);
				timercreated = true;
				System.out.println("\nVague "+(actualWave-1)+" terminée");
				
				if(actualWave > numberWaves) 
					System.out.println("Felicitations, vous avez gagné la partie!");
		
				else {
					System.out.println("Bonus d'Or = "+bonusWaveOver+"g");
					System.out.println("\n\nPréparez-vous pour la nouvelle vague!!\n");
					
				}
					
				
				
				World.gold += bonusWaveOver;
				
			}
			
			//Une fois le delai fini, on cree les nouvelles vagues
			else if (delayVague.hasFinished()) {
				
				timeBetweenMonsters = timeBetweenMonsters - 5;
				
				NbMonst = NbMonst +3; 
				
				System.out.println("Vague: "+actualWave+"\tMonstres à vaincre: "+ NbMonst+ "");
				
				NHealth = (actualWave - 1)*3;   //Il augmente les points de vie des monstres par 3 chaque vague
				
				currentWave = new Wave(spawn, timeBetweenMonsters, NbMonst, NHealth);
				actualWave++;
				
				timercreated =false;
		
				
			}
			//En attendant on fait une vague null
			else {
				currentWave = new Wave(spawn, 0,0,0);
			}
		}
		
	}
	
	public void update() {
		if(!currentWave.isCompleted()) {
			currentWave.update();
		}
		else
			newWave();
		
			
	}

	
	public Wave getCurrentWave() {
		return currentWave;
	}
	
	public int getVagueN() {
		return actualWave;
	}





	public int getNumberWaves() {
		return numberWaves;
	}





	public void setNumberWaves(int numberWaves) {
		this.numberWaves = numberWaves;
	}

	
}
