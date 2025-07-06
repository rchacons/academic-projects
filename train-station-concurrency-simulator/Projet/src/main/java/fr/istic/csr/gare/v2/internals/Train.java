package fr.istic.csr.gare.v2.internals;

public class Train extends Thread{

    private int id_;

    private String idShown;

    private EspaceQuai espaceQuai;

    private int vitesse;

    private  int capacite;

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


    public Train() {

    }



    public Train(EspaceQuai espaceQuai, int vitesse, int capacite, int arret) {
        this.espaceQuai = espaceQuai;
        this.vitesse = vitesse;
        this.capacite = capacite;
        this.placeLibre = capacite;
        this.arret = arret;
        this.idShown = "id";
        this.setDaemon(true);
    }

    public int getNbrVoyageur() {
        return nbrVoyageur;
    }

    public void run() {

        this.nbrVoyageur = 0;
        System.out.println("L'état du train : "+ idShown +"/"+Thread.currentThread().getName()+" est :"+ StatusTrain.A.status());

        try {
            Thread.sleep(10000 / this.vitesse);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        this.espaceQuai.arriverTrain(this);
        System.out.println("L'état du train : "+ idShown +"/"+Thread.currentThread().getName()+" est :"+ Train.StatusTrain.C.status());

        try {
            Thread.sleep(this.arret);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        this.espaceQuai.trainPartir(this);
    }

    public Boolean isFull(){
        return this.nbrVoyageur == this.capacite;
    }

    public void addVoyageur(){
        this.nbrVoyageur++;
        this.placeLibre--;
    }


    public int getPlaceLibre() {
        return placeLibre;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getVitesse() {
        return  this.vitesse;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
        this.idShown = String.valueOf(id_);
        ;
    }

    public String getIdShown() {
        return idShown;
    }

    public void setIdShown(String idShown) {
        this.idShown = idShown;
    }


}
