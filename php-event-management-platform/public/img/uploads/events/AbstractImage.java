/**
 * 
 * TP6 - GR3.1
 * Methodes isPixelOn, affect et Union
 * 
 * AGUESSY Balbina
 * CHACON Roberto
 * 
 */

public class AbstractImage {

    /**
     * @param x     abscisse du point
     * @param y     ordonnée du point
     * @pre         !this.isEmpty()
     * @return      true, si le point(x,y) est allumé dans this, false sinon
     */
    public boolean isPixelOn(int x, int y){
        int startingX = 0;
        int endingX = 255;                          //On definit les points de depart, arrive et le milieu des abscisses
        int middleX = (startingX + endingX)/2;

        int startingY = 0;
        int endingY = 255;                          //On definit les points de depart, arrive et le milieu des ordonnées
        int middleY = (startingY+endingY)/2;

        boolean horizontalCut = true;               //Boolean qui permettra de couper horizontalement ou verticalement la fenetre
        

        Iterator<Node> it = this.iterator();   
        
        while(it.getValue() == 2){
                if(horizontalCut){
                    if(y > middleY){
                        //On va à droite
                        it.goRight();
                        startingY = middleY;            //On redefinie le point de depart des ordonees 
                    }
                    else{
                        //On va à gauche
                        it.goLeft();
                        endingY = middleY;              //On redefinie le point d'arrive des ordonees 
                    }
                    middleY = (startingY + endingY)/2;  //On recalcule le point au milieu des deux points
                    horizontalCut = false;              //On met le boolean a faux pour que le boucle fasse maintenant la coupe Verticale
                }
                else{
                    if(x > middleX){
                        //On va à droite
                        it.goRight();
                        startingX = middleX;            //On redefinie le point de depart des abscisses 
                    }
                    else{
                        //On va à gauche
                        it.goLeft();
                        endingX = middleX;              //On redefinie le point d'arrive des abscisses 
                    }
                    middleX = (startingX + endingX)/2;  //On recalcule le point au milieu des deux points
                    horizontalCut = true;              //On met le boolean a faux pour que le boucle fasse maintenant la coupe Horizontale
                }
        }

        return (it.getValue() == 1);        //Apres avoir parcourou les etats impaires, on compare si la region dans laquelle on est tombé vaut 1 et on renvoie le resultat.

    }

    /**
     * this devient identique à image
     * 
     * @pre !image.isEmpty()
     * @pre this != image
     */
    public void affect(AbstractImage image){
        Iterator<Node> it1 = this.iterator();
        Iterator<Node> itImage = image.iterator();
        it1.clear();                        //On supprime le contenu de this
        affectImage(it1,itImage);
    }

    public void affectImage(Iterator<Node> it1, Iterator<Node> itImage){
        if(itImage.isEmpty()){          //Si l'abre est vide on fait rien
            return;
        }   
        it1.addValue(itImage.getValue());            //On ajoute a it1 la racine de it2

        if(itImage.getValue() == 2){             //Si le noeud a deux fils

            it1.goLeft()
            itImage.goLeft();
            affectImage(it1,itImage);       //D'abord on parcourt le fils gauche
            it1.goUp();
            itImage.goUp();

            it1.goRight();
            itImage.goRight(); 
            affectImage(it1,itImage);       //Ensuite on parcourt le fils droite
            it1.goUp();                     
            itImage.goUp();                 //Et on fait remonter les iterators 
        }

    }

    /**
     * this devient l'union image1 et image2 au sens des pixels allumés
     * 
     * @param image1 premiere image
     * @param image1 deuxieme image
     * @pre     !image.isEmpty() && !image2.isEmpty()
     * @pre this != image1
     * @pre this != image2
     */
    public void union(AbstractImage image1,AbstractImage image2){

        Iterator<Node> it = this.iterator();
        Iterator<Node> it1 = image1.iterator();         
        Iterator<Node> it2 = image2.iterator();
        unionImage(it,it1,it2);
    }

    public void unionImage(Iterator<Node> it ,Iterator<Node> it1, Iterator<Node> it2 ){
        //D'abord on doit traverser it1 et it2, et puis comparer ses valeurs 
        if(it1.getValue() == 2 && it2.getValue() == 2){     //1er Cas, les deux valent 2
            it.addValue(it1.getValue());

            it.goLeft();
            it1.goLeft();
            it2.goLeft();
            int leftTemp = it.getValue();
            unionImage(it, it1, it2);

            it.goUp();
            it1.goUp();
            it2.goUp();
            

            it.goRight();
            it1.goRight();
            it2.goRight();
            int rightTemp = it.getValue();
            unionImage(it, it1, it2);

            it.goUp();
            it1.goUp();
            it2.goUp();

            //Si jamais les deux fils ont la meme valeur et qu'elle est differente a 2, on efface les noeuds dans cette branche et on garde seulement un seule noeud avec la valeur
            if(leftTemp == rightTemp && rightTemp != 2 ){           
                it.clear();
                it.setValue(rightTemp);
            }

		}
        
        else if(it1.getValue() ==1 || it2.getValue() == 1 ){     //2e Cas, si l'un des deux vaut 1, on change la valeur de It a 1
            it.setValue(1);

        }
        else if(it1.getValue()== 0 && it2.getValue() == 2){     //3e Cas, si It1 vaut 0 et It2 vaut 2, on ajoute la valeur de It2 a It et on continue le parcours
            it.addValue(it2.getValue());
            
        }
        else if(it1.getValue()==2 && it2.getValue()==0){        //4e Cas, si It1 vaut 2 et It1 vaut 0, on ajoute la valeur de It1 a It et on continue le parcours
            it.addValue(it1.getValue());
        }

    }
