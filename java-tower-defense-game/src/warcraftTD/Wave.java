package warcraftTD;

import java.util.*;


public class Wave {
	
	
	private double spawnTime;
	private double timeSinceSpawn; //Temps passé depuis le dernier Spawn
	private List<Monster> monsterList = new LinkedList<Monster>();
	private List<Monster> monsterListRemove = new LinkedList<Monster>();   //Liste de monstres a retirer , utile pour pouvoir modifier l'autre a meme temps
	Position spawn;
	private int NbMonst; //Nb de monstres par Vague
	private int NbMSpawned;  //Nb de monstres apparus
	private boolean VagueTerminee;
	private int NHealth;   //Il augmente la vie de Monstres chaque vague
	

	public Wave(Position spawn, double spawnTime, int NbMonst, int newHealth) {
		this.spawn= spawn;
		this.spawnTime=spawnTime;
		this.NbMonst=NbMonst; 
		this.NHealth = newHealth;
		this.timeSinceSpawn=0;
		this.NbMSpawned=0;
		this.monsterList = new LinkedList<Monster>();
		this.VagueTerminee = false;
		spawn();
	}
	
	//Gere l'affichage des monstres de maniere aleatoire
	private void spawn() {
		
		if(!(this.NbMonst == 0))  {
			
			//Si tous les monstres sont apparu, on fait apparaitre le monstre final de la vague
			if(NbMSpawned == NbMonst) {
				System.out.println("\nUn boss est apparu !");
				Monster boss = new MonsterBoss(spawn.x,spawn.y);
				boss.setHealth(boss.getHealth() + NHealth*4);
				monsterList.add(boss);
				monsterListRemove.add(boss);
			}
			
			else {
			int enemyChosen = 0;
			Random random = new Random();
			enemyChosen= random.nextInt(2);
		
			if(enemyChosen == 1) {
				
				Monster mb = new MonsterB(spawn.x,spawn.y);
				mb.setHealth(mb.getHealth() + NHealth); //Modificateur de vie en fonction de nombre de vague
				monsterList.add(mb);
				monsterListRemove.add(mb);
			
			}
			else {
				Monster ma = new MonsterA(spawn.x,spawn.y);
				ma.setHealth(ma.getHealth() + NHealth);
				monsterList.add(ma);
				monsterListRemove.add(ma);
			}
			
			}
			
			NbMSpawned++;
		}
		
	}
	
	
	
	public void update () {
		
		//On assume que tous les monstres sont morts, jusqu'a ce que le program montre le contraire
		boolean tousMMorts = true;
			
		if(NbMSpawned<=NbMonst) {
			this.timeSinceSpawn ++;
			if(this.timeSinceSpawn > spawnTime) {
				spawn();
				this.timeSinceSpawn = 0;
			}
		}
		
		for (Monster m: monsterList) {
			if(m.isAlive()) {
				tousMMorts = false;
				m.update();
			}else 
				monsterListRemove.remove(m);
		
		}
		

		if(tousMMorts && NbMSpawned >= NbMonst ) {
			VagueTerminee = true;
		}
	
	}
	
	
	public boolean isCompleted() {
		return VagueTerminee;
	}
	
	public void setMonsterList(List<Monster> monsterList) {
		this.monsterList = monsterList;
	}
	
	public List<Monster> getMonsterList(){
		return monsterList;
	}
	
	public void deleteAll() {
		monsterList.clear();
		
	}

	public List<Monster> getMonsterListRemove() {
		return monsterListRemove;
	}

	public void setMonsterListRemove(List<Monster> monsterListRemove) {
		this.monsterListRemove = monsterListRemove;
	}




}
