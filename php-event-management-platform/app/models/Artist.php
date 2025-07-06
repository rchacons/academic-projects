<?php

class Artist
{

    private $id;
    private $name;
    private $contact;
    private $category;
    private $broadcastSites;


    public function __construct()
    {

        $this->classeDAO = DAOFactory::getDAOArtist(SELF::class);
    }


    public function getAll()
    {
        return $this->classeDAO->getAll();
    }

    /*
        On cree un artiste dans la bdd
        @post id de l'artiste
    */
    public function create($data)
    {
        return $this->classeDAO->create($data);
    }

    /**
     *  MAJ l'artiste 
     */
    public function update($data)
    {
        return $this->classeDAO->update($data);
    }

    /**
     * Assigne l'image a l'artist
     */
    public function assignImage($artistId, $imageId)
    {
        return $this->classeDAO->assignImage($artistId, $imageId);
    }

    /**
     *  Retire l'image de l'artiste
     */
    public function removeImage($artistId)
    {
        return $this->classeDAO->removeImage($artistId);
    }

    /**
     * On supprime l'artist de la bdd
     */
    public function delete($id)
    {
        return $this->classeDAO->delete($id);
    }

    public function findArtistById($id)
    {
        return $this->classeDAO->findArtistById($id);
    }
    
    public function getCategories(){
        return $this->classeDAO->getCategories();
    }

     /**
     * Renvoie les differents nom de categorie
     */
    public function getCategoriesName(){
        return $this->classeDAO->getCategoriesName();
    }

     /**
     * Renvoie les differents types selon la categorie choisie
     */
    public function getCategoryType($selectedCategory)
    {
        return $this->classeDAO->getCategoryType($selectedCategory);
    }



    /**
     * Getters and setters
     */
    public function getId()
    {
        return $this->id;
    }

    public function setId($id)
    {
        $this->id = $id;
    }


    public function getname()
    {
        return $this->name;
    }


    public function setname($name)
    {
        $this->name = $name;
    }


    public function getCategorie()
    {
        return $this->category;
    }


    public function setCategorie($category)
    {
        $this->category = $category;
    }


    public function getSiteDiffusion()
    {
        return $this->broadcastSites;
    }


    public function setSiteDiffusion($broadcastSites)
    {
        $this->broadcastSites = $broadcastSites;
    }

    public function getContact()
    {
        return $this->contact;
    }


    public function setContact($contact)
    {
        $this->contact = $contact;

        return $this;
    }
}
