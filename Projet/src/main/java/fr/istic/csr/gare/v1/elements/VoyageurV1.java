package fr.istic.csr.gare.v1.elements;

import fr.istic.csr.gare.v1.elements.EspaceQuaiV1;
import fr.istic.csr.gare.v1.elements.EspaceVenteV1;
import fr.istic.csr.gare.v2.internals.Voyageur;

public class VoyageurV1 extends Thread {

    private EspaceVenteV1 espaceVente;

    private EspaceQuaiV1 espaceQuai;

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

    public VoyageurV1(EspaceVenteV1 espaceVente, EspaceQuaiV1 espaceQuai) {
        this.espaceVente = espaceVente;
        this.espaceQuai = espaceQuai;
    }

    public void run() {
        System.out.println("L'état du voyageur : "+Thread.currentThread().getName()+" est : "+ Voyageur.StatusVoyageur.A.status());
        espaceVente.vendreBillet();
        System.out.println("L'état du voyageur : "+Thread.currentThread().getName()+" est : "+ Voyageur.StatusVoyageur.B.status());
        espaceQuai.monterTrain();
        System.out.println("L'état du voyageur : "+Thread.currentThread().getName()+" est : "+ Voyageur.StatusVoyageur.C.status());

    }
}
