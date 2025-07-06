package org.tp5.element;

public class Train extends Thread{

    private Quai quai;

    private int vitesse;

    private  int capacite;

    private int placeLibre;

    private int nbrVoyageur;

    private int arret;

    public Train(Quai quai, int vitesse, int capacite, int arret) {
        this.quai = quai;
        this.vitesse = vitesse;
        this.capacite = capacite;
        this.placeLibre = capacite;
        this.arret = arret;
        this.setDaemon(true);
    }

    public void setPlaceLibre(int placeLibre) {
        this.placeLibre = placeLibre;
    }

    public void setNbrVoyageur(int nbrVoyageur) {
        this.nbrVoyageur = nbrVoyageur;
    }

    public int getPlaceLibre() {
        return placeLibre;
    }

    public int getNbrVoyageur() {
        return nbrVoyageur;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getPlaceSold() {
        return  this.getCapacite() - this.getPlaceLibre();
    }

    public void run() {

        while (true) {

            this.quai.iniTrain(this);

            try {
                Thread.sleep(10000 / this.vitesse);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            quai.arriverTrain(this);
            try {
                Thread.sleep(this.arret);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            this.quai.trainPartir(this);
        }
    }

}
