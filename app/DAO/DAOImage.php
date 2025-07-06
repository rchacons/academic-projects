<?php

class DAOImage extends genericDAO
{
    private $image;



    public function __construct($image)
    {
        $this->image = $image;
        $this->setTable('images');
        $this->connect();
    }



    /**
     * Cree un objet dans la bdd
     */
    public function create($name)
    {
        $sql = "INSERT INTO images(id,name) VALUES (null,:name)";
        $data = array(
            ':name' => $name
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return $this->_connexion->lastInsertId();   //On renvoie l'id de l'image qu'on vient d'ajouter
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    /**
     * Supprime une image de la bdd etant donné un id
     */
    public function delete($id)
    {
        //Requete pour obtenir le nom de l'image (pour unlink apres)
        $sql = "SELECT * FROM images WHERE id = :id";

        //Requetes pour enlever l'image des tableaux
        $sql1 = "DELETE FROM eventsImages WHERE idImage = :id";
        $sql2 = "DELETE FROM images WHERE id = :id";

        $data = array(
            ':id' => $id
        );

        //On recupere l'image
        $query1 = $this->_connexion->prepare($sql);
        $query1->execute($data);
        $image = $query1->fetch(PDO::FETCH_OBJ);

        //On la supprime
        $query1 = $this->_connexion->prepare($sql1);
        $query2 = $this->_connexion->prepare($sql2);
        try {
            $query1->execute($data);
            $query2->execute($data);
            //On supprime l'image de notre repertoire
            unlink('../' . $image->name);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }


    /**
     *  Renvoie un array des images correspondantes à un evenement
     */
    public function getImageByEventId($eventId)
    {
        $sql = "SELECT * FROM images where id in( SELECT idImage FROM eventsImages WHERE idEvent = :idEvent)";
        $data = array(
            ':idEvent' => $eventId
        );
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
        return $query->fetchAll(PDO::FETCH_OBJ);
    }

    /**
     *  Renvoie l'image de couverture de l'evenement
     */
    public function getCoverImageByEventId($eventId)
    {
        $sql = "SELECT * FROM images WHERE id = (SELECT coverImage FROM event WHERE event.id = :idEvent)";
        $data = array(
            ':idEvent' => $eventId
        );
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
        return $query->fetch(PDO::FETCH_OBJ);
    }


    /**
     * Renvoie l'image de l'artiste
     */
    public function getArtistPictureByArtistId($artistId)
    {
        $sql = "SELECT * FROM images WHERE id = (SELECT picture FROM artist WHERE artist.id = :artistId)";
        $data = array(
            ':artistId' => $artistId
        );
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
        return $query->fetch(PDO::FETCH_OBJ);
    }

    /**
     * Renvoie un objet en fonction de l'id
     */
    public function read($id)
    {
        ////
    }

    /**
     * MAJ un objet
     */
    public function update($objet)
    {
        ////
    }
}
