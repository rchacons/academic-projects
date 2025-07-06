package warcraftTD;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
 
//Classe utile pour avoir une mesure du temps

public class Timer {

	private Instant debut;
	private long duree; // duree du timer en millisecondes
	
	public Timer(long duree) {
		this.duree = duree;
		restart();
	}
	
	public void restart() {
		debut = Instant.now();
	}
	
	public boolean hasFinished() {
		boolean b = (debut.compareTo((Instant.now().minus(duree, ChronoUnit.MILLIS))) < 0);
		return b;
	}
	
	//Renvoie true quand il reste 3 secondes pour la nouvelle vague
	public boolean printMessage() {

		Instant t = Instant.now().minusMillis(duree);
		long diff = (debut.toEpochMilli()/100-t.toEpochMilli()/100);
		if(diff == 30)
			return true;
		
		return false;
	}

	public void getTimer() {
		System.out.println("Duree = " +this.duree);
		System.out.println("Debut = "+ this.debut);
	}

}
