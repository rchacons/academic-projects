
class Site {

    /* Constantes communes à tous les sites */
    
    // STOCK_INIT < STOCK_MAX
    static final int STOCK_INIT = 5;
    static final int STOCK_MAX = 10;
    static final int BORNE_SUP = 8;
    static final int BORNE_INF = 2;
    private int nbVelos;
    
    private int order;
    
    
    public Site(int order){
        this.order = order;
        this.nbVelos = STOCK_INIT;
    }
    
    public int getOrder(){
        return this.order;
    }
    
    public void setOrder(int order){
        this.order = order;
    }

    public int getNbVelos(){
        return this.nbVelos;
    }
    
    public void setNbVelos(int nbVelos){
        this.nbVelos= nbVelos;
    }
    
    public synchronized void restituerVelo(){
    
        while(nbVelos == STOCK_MAX){
            try {
                System.out.println(Thread.currentThread().getName()+" attend que quelqu'un libère un espace du site : "+getOrder());
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbVelos++;
        System.out.println(Thread.currentThread().getName()+" vient de deposer un vélo dans le site : "+getOrder()+", il contient maintenant: "+getNbVelos()+" velos.");
        notify();
    }

    public synchronized void emprunterVelo(){
        while(nbVelos <= 0){
            try{
                System.out.println(Thread.currentThread().getName()+" attend que quelqu'un dépose un vélo dans le site : "+getOrder());
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        nbVelos--;
        System.out.println(Thread.currentThread().getName()+" vient de prendre un vélo du site : "+getOrder()+", il contient maintenant: "+getNbVelos()+" velos.");
        notify();
    }

 

    public void afficher(){
        System.out.println("Le site "+getOrder()+" contient "+getNbVelos()+" velos.");
    }


    
}