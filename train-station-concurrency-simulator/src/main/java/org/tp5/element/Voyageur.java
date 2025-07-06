package org.tp5.element;

public class Voyageur extends Thread {

    private Billeterie billeterie;

    private Quai quai;

    public Voyageur(Billeterie billeterie, Quai quai) {
        this.billeterie = billeterie;
        this.quai = quai;
    }

    public void run() {
       int voie =  billeterie.vendreBillet();
       quai.monterTrain(voie);
    }
}
