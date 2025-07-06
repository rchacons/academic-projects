package fr.istic.csr.gare.v1.elements;

import fr.istic.csr.gare.v1.elements.EspaceQuaiV1;
import fr.istic.csr.gare.v2.internals.Train;

public class TrainV1 extends Thread {

    private EspaceQuaiV1 espaceQuai;

    private int vitesse;

    private int capacite;

    private int placeLibre;

    private int nbrVoyageur;

    private int arret;

    public enum StatusTrain {
        A("A - en route vers la gare"),
        B("B - en attente d’une voie libre"),
        C("C - en gare"),
        D("D - parti");


        private String status;

        StatusTrain(String status){
            this.status = status;
        }

        public String status(){
            return status;
        }
    }

    public TrainV1(EspaceQuaiV1 espaceQuai, int vitesse, int capacite, int arret) {
        this.espaceQuai = espaceQuai;
        this.vitesse = vitesse;
        this.capacite = capacite;
        this.placeLibre = capacite;
        this.arret = arret;
        this.setDaemon(true);
    }

    public void run() {


        this.nbrVoyageur = 0;
        System.out.println("L'état du train : "+Thread.currentThread().getName()+" est :"+ Train.StatusTrain.A.status());


        try {
            Thread.sleep(10000 / this.vitesse);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        this.espaceQuai.arriverTrain(this);
        System.out.println("L'état du train : "+Thread.currentThread().getName()+" est :"+ Train.StatusTrain.C.status());
        try {
            Thread.sleep(this.arret);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        this.espaceQuai.trainPartir(this);

    }

    public Boolean isFull() {
        return this.nbrVoyageur == this.capacite;
    }

    public void addVoyageur() {
        this.nbrVoyageur++;
        this.placeLibre--;
    }

    public void setPlaceLibre(int placeLibre) {
        this.placeLibre = placeLibre;
    }


    public int getPlaceLibre() {
        return placeLibre;
    }


    public int getCapacite() {
        return capacite;
    }

}
