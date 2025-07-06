package fr.istic.csr.gare.v2.database;

import fr.istic.csr.gare.v2.internals.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InMemoryDataBase {

    public static final  int CAPACITE_MAX = 100;

    public static final int VITESSE_MAX = 300;

    public static final int VITESSE_MIN = 50;

    public static final int QUAI_CAPACITE = 3;

    public static final int ARRET = 1000;


    private Map<Integer, Train> trains;

    private Collection<Voyageur> voyageurs;

    private EspaceVente espaceVente;


    private EspaceQuai espaceQuai;

    private int nbrTrain = 0;

    public InMemoryDataBase() {

        this.trains = new HashMap<>();
        this.voyageurs  = new ArrayList<>();
        this.espaceVente = new EspaceVente(QUAI_CAPACITE);
        this.espaceQuai = new EspaceQuai(QUAI_CAPACITE, espaceVente);

    }


    public InMemoryDataBase(Map<Integer, Train> trains, Collection<Voyageur> voyageurs,
                            EspaceVente espaceVente, EspaceQuai espaceQuai) {
        this.trains = trains;
        this.voyageurs = voyageurs;
        this.espaceVente = espaceVente;
        this.espaceQuai = new EspaceQuai(QUAI_CAPACITE, espaceVente);
    }

    /**
     * Gere la creation d'un train et l'ajout à la bdd
     * @param capacite du train
     * @param vitesse du train
     * @return le train ajouté
     */
    public synchronized Train createTrain(int capacite, int vitesse ){
            Train newTrain = new Train();

            //On verifie si la capacite et la vitesse des trains sont correctes
            if(IntStream.rangeClosed(VITESSE_MIN,VITESSE_MAX).boxed().
                    collect(Collectors.toList()).contains(vitesse) && capacite <= CAPACITE_MAX) {
                Train train = new Train(espaceQuai,vitesse, capacite,ARRET);
                this.trains.put(nbrTrain, train);
                train.setId_(nbrTrain);
                nbrTrain ++;
                newTrain = train;
            }
            return newTrain;
    }

    /**
     * Gere la creation d'un voyageur et l'ajout à la bdd
     * @param name du voyageur
     * @return Voyageur ajouté à la bdd
     */
    public synchronized Voyageur createVoyageur(String name) {
        Voyageur voyageur = new Voyageur(espaceVente,espaceQuai);
        voyageur.setNameVoyageur(name);
        voyageurs.add(voyageur);
        return  voyageur;
    }

    public Collection<Voyageur> getVoyageurs() {
        return this.voyageurs;
    }

    public Collection<Train> getTrain() {
        return this.trains.values();
    }


    public EspaceVente getEspaceVente() {
        return espaceVente;
    }


    public EspaceQuai getEspaceQuai() {
        return espaceQuai;
    }




}
