package org.tp5.element;

public class Billeterie {

    private final int IMPRESSION_TICKET = 5;

    private int nbrTicketDispo;

    private  Train[] trains;

    public  Billeterie (int nbrVoies){
        this.nbrTicketDispo = 0;
        this.trains = new Train[nbrVoies] ;
    }

    synchronized public void updateBilletDispo(Train train, int voie, boolean arrive) {
        if(arrive) {
            this.trains[voie] = train;
            nbrTicketDispo += train.getPlaceLibre();
        } else {
            this.trains[voie] = null;
            nbrTicketDispo -= train.getPlaceLibre();
        }
        notifyAll();
    }

    synchronized public int vendreBillet() {
        if(this.nbrTicketDispo <= 0)
        {
            try {
                wait();
            }catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        int i = 0;
        while (this.trains[i] == null || this.trains[i].getPlaceLibre() < 1) {
            if(i == this.trains.length -1){
                i = 0;
            } else {
                i++;
            }
            nbrTicketDispo --;
            this.trains[i].setPlaceLibre(this.trains[i].getPlaceLibre()-1);
            try {
                Thread.sleep(IMPRESSION_TICKET);
            } catch (InterruptedException interruptedException) {

            }
        }
        notifyAll();
        return i;
    }

}
