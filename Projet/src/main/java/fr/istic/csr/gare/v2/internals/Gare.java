package fr.istic.csr.gare.v2.internals;

import fr.istic.csr.gare.v2.database.InMemoryDataBase;

import java.util.Random;

public class Gare {

    /**
     * Nb max des trains qui peuvent etre au meme temps dans la gare (en quai et en attente)
     */
    public static final int GARE_CAPACITE_MAX = 5;

    /**
     * Capacite max des voyageurs que le train peut prendre.
     */
    public static final int TRAIN_CAPACITE_MAX = 200;

    /**
     * Nb max de voyageurs qui arrivent à la gare
     */
    public static final int VOYAGEUR_MAX = 100;


    /**
     * Temps pendant lequel les voyageurs peuvent monter.
     */
    public static final int ARRET_TRAIN = 5000;

    /**
     * Temps d'attente avant de demarrer le procesus de chaque voyaguer
     */
    public static final int TEMP_ATTENTE = 100;

    private EspaceQuai espaceQuai;

    private EspaceVente espaceVente;

    private Train[] trains;

    private Voyageur[] voyageurs;

    private int nbTrain = 0;

    private int nbVoyageur = 0;

    private InMemoryDataBase dataBase;

    public Gare(InMemoryDataBase dataBase) {
        this.dataBase = dataBase;

        this.trains = new Train[GARE_CAPACITE_MAX];
        this.voyageurs = new Voyageur[VOYAGEUR_MAX];

        this.espaceVente = dataBase.getEspaceVente();
        this.espaceQuai = dataBase.getEspaceQuai();

        createTrain();
        createVoyageur();

        // On initialise tous les trains
        for(int i = 0; i< nbTrain; i++ ) {
            this.trains[i].start();
        }

        // On initialise tous les voyageurs
        for (int i = 0; i < nbVoyageur; i++) {
            this.voyageurs[i].start();
            try {
                Thread.sleep(TEMP_ATTENTE);
            } catch (InterruptedException interruptedException){

            }
        }

        // On attend que tous les trains soient finis
        for(int i = 0; i< nbTrain; i++ ) {
            try {
                this.trains[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Pour la v2 on a commente la commnade ci-dessous afin de garder encore le program ouvert et pouvoir lancer nos curls
        // Une fois tous les trains seront parti, on fini le programme
        //System.exit(0);
    }

    private void createTrain() {
        while (this.nbTrain < GARE_CAPACITE_MAX) {
            Random random = new Random();
            int vitesse = random.nextInt(300-50) + 50 ;
            int capacite = random.nextInt(TRAIN_CAPACITE_MAX);
            Train train = new Train(espaceQuai,vitesse, capacite, ARRET_TRAIN );
            this.trains[nbTrain] = train;
            nbTrain++;
        }
    }


    private void createVoyageur() {
        while(nbVoyageur < VOYAGEUR_MAX) {
            Voyageur voyageur = new Voyageur(espaceVente, espaceQuai);
            this.voyageurs[nbVoyageur] = voyageur;
            nbVoyageur++;
        }
    }


}
