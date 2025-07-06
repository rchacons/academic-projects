package fr.istic.csr.gare.v2.internals;

public class Voyageur extends Thread {

    private EspaceVente espaceVente;

    private EspaceQuai espaceQuai;

    private  String nameVoyageur;

    public enum StatusVoyageur {
        A("A - en route vers la gare"),
        B("B - muni d’un ticket"),
        C("C - monté dans un train");


        private String status;

        StatusVoyageur(String status){
            this.status = status;
        }

        public String status(){
            return status;
        }
    }


    public Voyageur(EspaceVente espaceVente, EspaceQuai espaceQuai) {
        this.espaceVente = espaceVente;
        this.espaceQuai = espaceQuai;
        nameVoyageur = "name";
    }


    public String getNameVoyageur() {
        return nameVoyageur;
    }

    public void setNameVoyageur(String nameVoyageur) {
        this.nameVoyageur = nameVoyageur;
    }


    public void run() {
        System.out.println("L'état du voyageur : "+nameVoyageur+"/"+Thread.currentThread().getName()+" est : "+StatusVoyageur.A.status());
       espaceVente.vendreBillet();
        System.out.println("L'état du voyageur : "+nameVoyageur+"/"+Thread.currentThread().getName()+" est : "+StatusVoyageur.B.status());
        espaceQuai.monterTrain();
        System.out.println("L'état du voyageur : "+nameVoyageur+"/"+Thread.currentThread().getName()+" est : "+StatusVoyageur.C.status());

    }
}
