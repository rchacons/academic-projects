<?php
abstract class genericDAO
{

    //Propriété qui contiendra l'instance de la connexion
    protected $_connexion;

    //Propreites premettant de personnaliser les requêtes
    private $table;


    /**
     * Fonction d'initialisation de la base de données
     * 
     * @return void
     */
    public function connect()
    {
        //On supprime la connexion précédente
        $this->_connexion = null;

        //On essaie de se connecter à la base
        try {
            
            $this->_connexion = new PDO("mysql:host=" . PDO_HOST . ";" . "dbname=" . PDO_DBBASE, PDO_USER, PDO_PW);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

     /**
     * Cree un objet dans la bdd
     */
    abstract public function create($objet);

    /**
     * Renvoie un objet en fonction de l'id
     */
    abstract public function read($id);

    /**
     * MAJ un objet
     */
    abstract public function update($objet);

    /**
     * Supprime un objet de la bdd
     */
    abstract public function delete($obj);

    /**
     * Méthode permettant d'obtenir tous les enregistrements de la table choisie
     */
    public function getAll()
    {

        $sql = "SELECT * FROM " .$this->table;                 //On peut pas avoir une requete prepare avec les noms des tables et colonnes, donc on concatenne directement
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute();
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
        return $query->fetchAll(PDO::FETCH_OBJ);
    }



    /**
     * Get the value of table
     */ 
    public function getTable()
    {
        return $this->table;
    }

    /**
     * Set the value of table
     *
     * @return  self
     */ 
    public function setTable($table)
    {
        $this->table = $table;

    }
}
