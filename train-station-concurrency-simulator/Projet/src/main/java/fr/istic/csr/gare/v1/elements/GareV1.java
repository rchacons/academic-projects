package fr.istic.csr.gare.v1.elements;

import java.util.Random;

public class GareV1 {

        /**
         * Nb max des trains qui peuvent etre au meme temps dans la gare (en quai et en attente)
         */
        public static final int GARE_CAPACITE_MAX = 10;

        /**
         * Capacite max des voyageurs que le train peut prendre.
         */
        public static final int TRAIN_CAPACITE_MAX = 100;

        /**
         * Nb max de voyageurs qui arrivent à la gare
         */
        public static final int VOYAGEUR_MAX = 200;

        /**
         * Nb de voie par quai
         */
        public static final int QUAI_CAPACITE = 2;

        /**
         * Temps pendant lequel les voyageurs peuvent monter.
         */
        public static final int ARRET_TRAIN = 5000;

        /**
         * Temps d'attente avant de demarrer le procesus de chaque voyaguer
         */
        public static  final int TEMP_ATTENTE = 100;

        private EspaceQuaiV1 espaceQuai;

        private EspaceVenteV1 espaceVente;

        private TrainV1[] trains;

        private VoyageurV1[] voyageurs;

        private int nbTrain = 0;

        private int nbVoyageur = 0;

        public GareV1 () {
            this.trains = new TrainV1[GARE_CAPACITE_MAX];
            this.voyageurs = new VoyageurV1[VOYAGEUR_MAX];
            this.espaceVente = new EspaceVenteV1(QUAI_CAPACITE);
            this.espaceQuai = new EspaceQuaiV1(QUAI_CAPACITE, this.espaceVente);
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

            // Une fois tous les trains seront parti, on fini le programme
            System.exit(0);
        }

        private void createTrain() {
            while (this.nbTrain < GARE_CAPACITE_MAX) {
                Random random = new Random();
                int vitesse = random.nextInt(300-50) + 50 ;
                int capacite = random.nextInt(TRAIN_CAPACITE_MAX);
                TrainV1 train = new TrainV1(espaceQuai,vitesse, capacite, ARRET_TRAIN );
                this.trains[nbTrain] = train;
                nbTrain++;
            }
        }


        private void createVoyageur() {
            while(nbVoyageur < VOYAGEUR_MAX) {
                VoyageurV1 voyageur = new VoyageurV1(espaceVente, espaceQuai);
                this.voyageurs[nbVoyageur] = voyageur;
                nbVoyageur++;
            }
        }


}