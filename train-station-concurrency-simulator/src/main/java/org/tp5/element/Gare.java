package org.tp5.element;

import java.util.Random;

public class Gare {

    public static final int GARE_CAPACITE_MAX = 5;

    public static final int TRAIN_CAPACITE_MAX = 100;

    public static final int VOYAGEUR_MAX = 600;

    public static final int QUAI_CAPACITE = 2;

    public static final int ARRET_TRAIN = 1000;

    public static  final int TEMP_ATTEND = 10;

    private Quai quai;

    private Billeterie billeterie;

    private Train [] trains = new Train[GARE_CAPACITE_MAX];

    private Voyageur [] voyageurs = new Voyageur[VOYAGEUR_MAX];

    private int nbrTrain = 0;

    private int nbrVoyageur = 0;

    private void createTrain() {
        while (nbrTrain < GARE_CAPACITE_MAX) {
            Random random = new Random();
            int vitesse = random.nextInt(300-50) + 50 ;
            int capacite = random.nextInt(TRAIN_CAPACITE_MAX);
            Train train = new Train(quai,vitesse, capacite, ARRET_TRAIN );
            this.trains[nbrTrain] = train;
            nbrTrain ++;
        }
    }

    private void createVoyageur() {
        while(nbrVoyageur < VOYAGEUR_MAX) {
            Voyageur voyageur = new Voyageur(billeterie, quai);
            this.voyageurs[nbrVoyageur] = voyageur;
            nbrVoyageur ++;
        }
    }

    public Gare () {
        this.billeterie = new Billeterie(QUAI_CAPACITE);
        this.quai = new Quai(QUAI_CAPACITE, this.billeterie);
        this.createTrain();
        this.createVoyageur();

        for(int i = 0; i< nbrTrain; i++ ) {
            this.trains[i].start();
        }


        for (int i = 0; i < nbrVoyageur; i++) {
            this.voyageurs[i].start();
            try {
                Thread.sleep(TEMP_ATTEND);
            } catch (InterruptedException interruptedException){

            }
        }
    }
}
