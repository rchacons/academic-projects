<?php

class DAOEvent extends genericDAO
{
    private $event;



    public function __construct($event)
    {
        $this->event = $event;
        $this->setTable('event');
        $this->connect();
    }

    /**
     * Cree un objet dans la bdd
     * @post id de l'evenement
     */
    public function create($data)
    {
        $sql = "INSERT INTO event(id,name,description,startDate,endDate,capacity,coverImage) VALUES (null,:name,:description,:startDate,:endDate,:capacity,null)";
        $data = array(
            ':name' => $data['name'],
            ':description' => $data['description'],
            ':startDate' => $data['startDate'],
            ':endDate' => $data['endDate'],
            ':capacity' => $data['capacity']
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return $this->_connexion->lastInsertId();   //On renvoie l'id de l'evenement qu'on vient d'ajouter
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }


    public function assignImage($eventId, $imageId)
    {
        $sql = 'INSERT INTO eventsImages(idImage,idEvent) VALUES (:idImage,:idEvent)';
        $data = array(
            ':idImage' => $imageId,
            ':idEvent' => $eventId,
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return true;
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    public function assignCoverImage($eventId, $coverImageId)
    {
        $sql = 'UPDATE event SET coverImage = :coverImageId WHERE event.id = :eventId';
        
        $data = array(
            ':coverImageId' => $coverImageId,
            ':eventId' => $eventId,
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return true;
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    public function findEventById($id)
    {
        $sql = 'SELECT * FROM event WHERE id = :id';
        $data = array(
            ':id' => $id
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
     * MAJ l'evenement
     */
    public function update($data)
    {
        $sql = "UPDATE event SET name = :name, description = :description , startDate = :startDate ,endDate = :endDate , capacity = :capacity WHERE id = :id";
        $data = array(
            ':id' => $data['id'],
            ':name' => $data['name'],
            ':description' => $data['description'],
            ':startDate' => $data['startDate'],
            ':endDate' => $data['endDate'],
            ':capacity' => $data['capacity']
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return true;
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    /**
     * Supprime l'evenement de la bdd
     */
    public function delete($id)
    {
        $valid = false;

        //On veut recuperer l'id de l'image lié à l'evenement
        $sql = "SELECT * FROM images where id in (SELECT idImage from eventsImages where idEvent = :id)";


        //Requete pour supprimer la liason entre les deux
        $sql1 = "DELETE FROM eventsImages WHERE idEvent = :id";

        //Requete pour supprimer l'evenement 
        $sql2 = "DELETE FROM event WHERE id = :id";

        //Requete pour supprimer l'image
        $sql3 = "DELETE FROM images WHERE id = :id";

        $data = array(
            ':id' => $id
        );



        //On recupere l'id de l'Image
        $query1 = $this->_connexion->prepare($sql);
        ($query1->execute($data)) ? $valid = true : $valid = false;
        $result = $query1->fetchAll(PDO::FETCH_OBJ);


        //On supprime la Liason
        $query = $this->_connexion->prepare($sql1);
        ($query->execute($data)) ? $valid = true : $valid = false;

        //On retire l'image de couverture
        $this->removeCoverImage($id);


        //On supprime la/les Image(s)
        if (!empty($result)) {
            foreach ($result as $image) {

                $dataImg= array(':id' => $image->id);
                $query1 = $this->_connexion->prepare($sql3);
                if($query1->execute($dataImg)){
                    unlink('../'.$image->name);             //On la supprime du dossier 
                    $valid = true; 
                } else{$valid = false;}
                  
            }
        }


        //On supprime l'Event
        $query = $this->_connexion->prepare($sql2);
        ($query->execute($data)) ? $valid = true : $valid = false;

        return $valid;

    }


    /**
     *  Retire une image de couverture associé à l'event id
     */
    public function removeCoverImage($eventId)
    {
        $sql = "UPDATE event SET coverImage = null WHERE id = :id";
        $data = array(
            ':id' => $eventId
        );

        $query= $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    /**
     * Get the value of event
     */
    public function getEvent()
    {
        return $this->event;
    }


    public function setEvent($event)
    {
        $this->event = $event;
    }
}
