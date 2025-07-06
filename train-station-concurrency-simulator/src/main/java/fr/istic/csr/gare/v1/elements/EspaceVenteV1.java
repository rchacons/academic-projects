package fr.istic.csr.gare.v1.elements;

public class EspaceVenteV1 {

    private final long IMPRESSION_TICKET = 100;

    private int nbrTicketDispo;

    private  TrainV1[] trains;

    public EspaceVenteV1(int nbrVoies){
        this.nbrTicketDispo = 0;
        this.trains = new TrainV1[nbrVoies] ;
    }

    /**
     * Mettre a jour le nb de billet dispo a partir des nouveaux trains qui arrivent
     * @param train: le train qui vient d'arriver
     * @param voie: la voie est une
     * @param arrive
     */
    synchronized public void updateBilletDispo(TrainV1 train, int voie, boolean arrive) {
        if(arrive) {
            // Si le train arrive, on augmente le nombre de places disponibles
            this.trains[voie] = train;
            nbrTicketDispo += train.getPlaceLibre();
        } else {
            // Sinon, on enleve le nombre de places disponibles dans le train du nombre de tickets
            this.trains[voie] = null;
            nbrTicketDispo -= train.getPlaceLibre();
            if(nbrTicketDispo < 0)
                nbrTicketDispo = 0;
        }
        notifyAll();
    }

    /**
     * Gere les ventes des tickets et met a jour les places du train
     * @return i : le voie dans laquelle le voyageur a achete le billet
     */
    synchronized public void vendreBillet() {
        while(this.nbrTicketDispo <= 0)
        {
            try {
                wait();
            }catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        nbrTicketDispo --;
        //this.trains[i].setPlaceLibre(this.trains[i].getPlaceLibre()-1);
        try {
            Thread.sleep(IMPRESSION_TICKET);
        } catch (InterruptedException interruptedException) {

        }
    }

}