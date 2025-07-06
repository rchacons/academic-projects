public class Camion extends Thread{

     Site[] sites;
     int stock; 


     public Camion(Site[] sites){
         this.sites = sites;
         //On initialise le stock du camion a 5.
         this.stock=5;

     }

     public void restockSite(Site site){

        // Entree Section critique
        int nbVelos = site.getNbVelos();

        if(nbVelos > Site.BORNE_SUP){
            System.out.println("Le camion est en train d'enlever des velos du site : "+site.getOrder());
            // decharger
            while(nbVelos > Site.STOCK_INIT){
                nbVelos--;
                this.stock++;
            }
            //On met a jour le nb de velos
            site.setNbVelos(nbVelos);

            afficherApresRestock(site);
            
            
        }
        else if(nbVelos < Site.BORNE_INF){
            
            System.out.println("Le camion est en train de restocker le site : "+site.getOrder());
            // charger
            while((nbVelos < Site.STOCK_INIT) && (this.stock > 0)){
                nbVelos++;
                this.stock--;
            }
            //On met a jour le nb de velos
            site.setNbVelos(nbVelos);

            afficherApresRestock(site);

            
        }
        else {
            // BORNE_INF < NbVelo < BORNE_SUP donc on fait rien
            return;
        }

        // Sortie Section critique
        
        if(this.stock == 0){
            System.out.println("Le camion ne dispose plus de velos");
        }
     }
    

     public void run() {
    
        int nbSites = this.sites.length;

        while(!Thread.currentThread().isInterrupted()){

            for(int i = 0;i<nbSites;i++){

                Site currentSite = sites[i];

                // On bloque le site
                synchronized(currentSite){
                    System.out.println("Le camion est arrivé au site: "+i+", lequel dispose de "+currentSite.getNbVelos()+" velos.");
                    restockSite(currentSite);
                    System.out.println("Le camion est parti du site: "+i);
                    currentSite.notifyAll();
                }
                
                // Une fois le site restitue, on bloque le thread pour 1000 ms (puisque la difference entre le site d'arrive et le site de depart est toujours 1)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void afficherApresRestock(Site site) {
        System.out.println("Le camion a fini de restocker le site  " + site.getOrder() + ", il contient maintenant " + site.getNbVelos() + " velos.");
    }
       

}