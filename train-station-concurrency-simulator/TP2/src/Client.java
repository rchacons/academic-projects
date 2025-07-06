public class Client  extends Thread{

    // Site de depart
    Site siteDepart;

    // Site d'arrive
    Site siteArrive;


    public Client(Site siteDepart, Site siteArrive){
        this.siteDepart = siteDepart;
        this.siteArrive = siteArrive;
    }

    public void run() {

        int duration = Math.abs(siteArrive.getOrder()-siteDepart.getOrder());

        
        
        this.siteDepart.emprunterVelo();
        

        try {
            System.out.println(Thread.currentThread().getName()+" se rend du site "+siteDepart.getOrder()
            +" au site "+siteArrive.getOrder());
            Thread.sleep(duration*1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        this.siteArrive.restituerVelo();
        
        
      
    }


    /**
     * Si le site de depart ne dispose plus de velo, le client attend
     */

     /**
      * Une fois sur place, il restitue le velo
      S'il y a plus de places, le client doit attendre qu'un emplaement se libere.
      */
    /**
     * Chaque nouveau client est un nouveau thread
     */

     /**
      * Chaque client dispose d'une carte montrant ou emprunter un velo.
      */
}