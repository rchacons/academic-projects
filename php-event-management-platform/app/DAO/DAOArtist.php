<?php

class DAOArtist extends genericDAO
{

    private $artist;



    public function __construct($artist)
    {

        $this->artist = $artist;
        $this->setTable('artist');
        $this->connect();
    }

    public function getAll()
    {
        $sql = "SELECT * FROM artist ";
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute();
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }

        $result = $query->fetchAll(PDO::FETCH_OBJ); //Array des images

        foreach ($result as $artist) {
            //On modifie la categorie de chaque artiste pour montrer les donnees au lieu de l'id
            $category = $this->getCategory($artist->id);  //On recupere la categorie de l'artiste

            $artist->categoryId = $category;

            //Idem pour l'image
            $image = $this->getArtistImage($artist->id);

            $artist->picture = $image->name;
        }

        return $result;
    }

    /**
     * Cree un objet dans la bdd
     * @post id de l'artist
     */
    public function create($data)
    {
        //D'abord on recupere l'id de la categorie
        $sql1 = "SELECT idCategory FROM category WHERE name = :category AND type = :typeCat ";
        $data1 = array(
            ':category' => $data['category'],
            ':typeCat' => $data['typeCat']
        );
        $query1 = $this->_connexion->prepare($sql1);
        $query1->execute($data1);
        $result = $query1->fetch(PDO::FETCH_OBJ);
        

        $sql = "INSERT INTO artist(id,name,contact,diffusion,categoryId,picture,description) VALUES (null,:name,:contact,:diffusion,:categoryId,null,:description)";
        $data = array(
            ':name' => $data['name'],
            ':contact' => $data['contact'],
            ':diffusion' => $data['diffusion'],
            ':categoryId' => $result->idCategory,
            ':description' => $data['description']
        );
        $query = $this->_connexion->prepare($sql);
        try {
            if ($query->execute($data)) {
                return $this->_connexion->lastInsertId();   //On renvoie l'id de l'artiste qu'on vient d'ajouter
            } else {
                return false;
            }
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }



    public function assignImage($artistId, $imageId)
    {
        $sql = 'UPDATE artist SET picture = :imageId WHERE artist.id = :artistId';
        $data = array(
            ':artistId' => $artistId,
            ':imageId' => $imageId,
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

    public function findArtistById($id)
    {
        $sql = 'SELECT * FROM artist WHERE id = :id';
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

        $artist = $query->fetch(PDO::FETCH_OBJ);

        $category = $this->getCategory($artist->id); 
        $artist->categoryId = $category;

        
        $image = $this->getArtistImage($artist->id);
        
        $artist->picture = $image->name;

    

        return $artist;
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
    public function update($data)
    
    {

        //D'abord on recupere l'id de la categorie
        $sql1 = "SELECT idCategory FROM category WHERE name = :category AND type = :typeCat ";
        $data1 = array(
            ':category' => $data['category'],
            ':typeCat' => $data['typeCat']
        );
        $query1 = $this->_connexion->prepare($sql1);
        $query1->execute($data1);
        $result = $query1->fetch(PDO::FETCH_OBJ);

        //$sql = "UPDATE event SET name = :name, description = :description , startDate = :startDate ,endDate = :endDate , capacity = :capacity WHERE id = :id";
        $sql = "UPDATE artist SET name = :name , contact = :contact, diffusion = :diffusion, categoryId = :categoryId , description = :description WHERE id = :id";
        $data = array(
            ':id' => $data['id'],
            ':name' => $data['name'],
            ':contact' => $data['contact'],
            ':diffusion' => $data['diffusion'],
            ':categoryId' => $result->idCategory,
            ':description' => $data['description']
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
     * Supprime un objet de la bdd
     */
    public function delete($id)
    {
        $valid = false;


        //On doit le supprimer de la table artistBroadcasSites d'abord
        $sql1 = "DELETE FROM artistBroadcastSites WHERE idArtist = :id";

        //Requete pour recuperer l'id de l'image de l'artiste
        $sql2 = "SELECT * FROM images WHERE images.id = (SELECT picture FROM artist WHERE artist.id = :id) ";

        //Requete pour supprimer l'artiste
        $sql3 = "DELETE FROM artist WHERE id = :id";

        //Requete pour supprimer l'image
        $sql4 = "DELETE FROM images WHERE id = :id";

        $data = array(
            ':id' => $id
        );

        //On supprime la liason artiste - site diffusion
        $query1 = $this->_connexion->prepare($sql1);
        ($query1->execute($data)) ? $valid = true : $valid = false;

        //On recupere l'id de l'Image
        $query2 = $this->_connexion->prepare($sql2);
        ($query2->execute($data)) ? $valid = true : $valid = false;
        $result = $query2->fetchAll(PDO::FETCH_OBJ);

        //On supprime l'artiste
        $query3 = $this->_connexion->prepare($sql3);
        ($query3->execute($data)) ? $valid = true : $valid = false;

        //On supprime l'image

        $dataImg = array(':id' => $result->id);
        $query4 = $this->_connexion->prepare($sql4);
        if ($query4->execute($dataImg)) {
            unlink('../' . $result->name);             //On la supprime du dossier 
            $valid = true;
        } else {
            $valid = false;
        }

        return $valid;
    }

    /**
     *  Retire une image de couverture associé à l'event id
     */
    public function removeImage($artistId)
    {
        $sql = "UPDATE artist SET picture = null WHERE id = :id";
        $data = array(
            ':id' => $artistId
        );

        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
    }

    /**
     * Renvoie la categorie selon l'id de l'artiste
     */
    public function getCategory($idArtist)
    {

        $sql = "SELECT name, type from category where category.idCategory = (Select categoryId from artist where artist.id = :artistId);";
        $data = array(':artistId' => $idArtist);
        $query = $this->_connexion->prepare($sql);
        $query->execute($data);
        $category = $query->fetch(PDO::FETCH_OBJ);  //On recupere la categorie de l'artiste

        return $category;
    }

    /**
     * Renvoie la liste complete des categories
     */
    public function getCategories()
    {
        $sql = "SELECT * FROM category";
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
     * Renvoie les differents nom de categorie
     */
    public function getCategoriesName()
    {
        $sql = "SELECT DISTINCT name FROM category";
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute();
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }

        $result = $query->fetchAll(PDO::FETCH_OBJ);
        return $result;
    }

    /**
     * Renvoie les differents types selon la categorie choisie
     */
    public function getCategoryType($selectedCategory)
    {
        $sql = "SELECT type FROM category where name = :nameCat ";
        $data = array(':nameCat' => $selectedCategory);
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }

        $result = $query->fetchAll(PDO::FETCH_OBJ);
        return $result;
    }


    /**
     * Renvoie l'image selon l'id de l'artiste
     */
    public function getArtistImage($idArtist)
    {

        $sql = "SELECT * FROM images WHERE id = (SELECT picture FROM artist WHERE artist.id = :artistId)";
        $data = array(
            ':artistId' => $idArtist
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


    public function getArtist()
    {
        return $this->artist;
    }


    public function setArtist($artist)
    {
        $this->artist = $artist;
    }
}
