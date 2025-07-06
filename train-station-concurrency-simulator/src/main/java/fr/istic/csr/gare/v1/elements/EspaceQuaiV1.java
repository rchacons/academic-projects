package fr.istic.csr.gare.v1.elements;

import fr.istic.csr.gare.v2.internals.Train;

public class EspaceQuaiV1 {

    private int nbVoies;

    private int nbVoiesLibre;

    private EspaceVenteV1 espaceVente;

    private TrainV1[] trains;


    public EspaceQuaiV1(int nbVoies, EspaceVenteV1 espaceVente) {
        this.nbVoies = nbVoies;
        this.nbVoiesLibre = nbVoies;
        this.espaceVente = espaceVente;
        this.trains = new TrainV1[nbVoies];
    }

    /**
     * Permet aux voyageurs de monter sur le train
     */
    public void monterTrain() {

        while(true){
            for (TrainV1 train : trains){
                if(train!=null && !train.isFull()){
                    train.addVoyageur();
                    return;
                }
            }
        }

    }


    /**
     * Gere l'arrivee du train dans le quai
     * @param train
     */
    synchronized public void arriverTrain(TrainV1 train) {

        // On verifie le nb de voies libres
        while (nbVoiesLibre < 1) {
            try {
                System.out.println("L'état du train : "+Thread.currentThread().getName()+" est :"+ Train.StatusTrain.B.status());
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        nbVoiesLibre--;

        // On cherche la place dispo parmi les voies du quai
        int i = 0;
        while (this.trains[i] != null && i < nbVoies) {
            if (i == nbVoies - 1) {
                i = 0;
            } else {
                i++;
            }
        }

        // On affecte la voie au train
        this.trains[i] = train;

        // On met à jour les tickets à vendre de l'espace vente
        this.espaceVente.updateBilletDispo(train, i, true);

    }

    /**
     * Gere le depart du train du quai
     * @param train
     */
    synchronized public void trainPartir (TrainV1 train) {

        // On selectionne la voie du train qui va partir
        int i = 0;
        while (this.trains[i] != train && i < this.trains.length) {
            if(i == this.trains.length -1){
                i = 0;
            } else {
                i++;
            }
        }

        // On met a jour le nb de billets dispo
        this.espaceVente.updateBilletDispo(train, i , false);

        // Le train est parti
        this.trains[i] = null;
        nbVoiesLibre++;

        System.out.println("L'état du train : "+Thread.currentThread().getName()+" est :"+ Train.StatusTrain.D.status());
        notifyAll();
    }

}

