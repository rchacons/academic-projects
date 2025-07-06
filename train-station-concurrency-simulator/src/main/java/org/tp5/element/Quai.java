package org.tp5.element;

public class Quai {

    private int nbrVoies;

    private int nbrVoiesLibre;

    private Billeterie billeterie;

    private Train [] trains;


    public Quai(int nbrVoies, Billeterie billeterie) {
        this.nbrVoies = nbrVoies;
        this.nbrVoiesLibre = nbrVoies;
        this.billeterie = billeterie;
        this.trains = new Train[nbrVoies];
    }

    synchronized public void monterTrain(int voie) {
        System.out.println("Voyageur: " + Thread.currentThread().getName() +
                "monte dans le train sur la voie" + voie);
        this.trains[voie].setNbrVoyageur(this.trains[voie].getNbrVoyageur()+1);
        notifyAll();
    }

    synchronized  public void iniTrain(Train train) {
        train.setNbrVoyageur(train.getCapacite());
        train.setNbrVoyageur(0);
    }
    synchronized public void arriverTrain(Train train) {
        System.out.println("Le Train:" + Thread.currentThread().getName()
                + " arrive");
        while (nbrVoiesLibre < 1) {
            try {
                System.out.println("Le Train:" + Thread.currentThread().getName()
                        + " en attente d’une voie libre");
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        nbrVoiesLibre--;

        int i = 0;
        while (this.trains[i] != null && i < nbrVoies) {

            if (i == nbrVoies - 1) {
                    i = 0;
            } else {
                    i++;
            }
        }
        this.trains[i] = train;
        this.billeterie.updateBilletDispo(train, i, true);
            System.out.println("TRAIN : " +
                    Thread.currentThread().getName()
                    + " est en voie " + i);
    }

    synchronized public void trainPartir (Train train) {

        System.out.println("Train :" + Thread.currentThread().getName()  +
                "va partir de la gare");

        int i = 0;
        while (this.trains[i] != train && i < this.trains.length) {
            if(i == this.trains.length -1){
                i = 0;
            } else {
                i++;
            }
        }
        this.billeterie.updateBilletDispo(train, i , false);

            while (this.trains[i].getNbrVoyageur() < this.trains[i].getPlaceSold()){
                try{
                    wait();
                } catch (InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }
            }
            this.trains[i] = null;
            nbrVoiesLibre ++;
            System.out.println("Train" + Thread.currentThread().getName()
            +" est parti de la gare");
            notifyAll();
        }
}
