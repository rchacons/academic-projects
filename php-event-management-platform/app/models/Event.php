<?php

class Event
{
    private $id;
    private $name;
    private $description;
    private $startDate;
    private $endDate;
    private $classeDAO;

    public function __construct()
    {
    
        $this->classeDAO = DAOFactory::getDAOEvent(SELF::class);
    }

    /*
        On cree un evenement dans la bdd
        @post id de l'evenement
    */
    public function create($data)
    {
        return $this->classeDAO->create($data);
    }

    /**
     *  MAJ l'evenement 
     */
    public function update($data)
    {
        return $this->classeDAO->update($data);
    }

    /**
     * Assigne l'image a l'evenement
     */
    public function assignImage($eventId, $imageId)
    {
        return $this->classeDAO->assignImage($eventId, $imageId);
    }

    /**
     * Assigne une coverImage a l'evenement
     */
    public function assignCoverImage($eventId, $coverImageId)
    {
        return $this->classeDAO->assignCoverImage($eventId, $coverImageId);
    }

    /**
     *  Retire une image de couverture du tableau event
     */
    public function removeCoverImage($eventId)
    {
        return $this->classeDAO->removeCoverImage($eventId);
    }

    /**
     * On supprime l'evenement de la bdd
     */
    public function delete($id)
    {
        return $this->classeDAO->delete($id);
    }
    /*
        On recupere tous les evenements de la bdd
    */
    public function getAll()
    {
        return $this->classeDAO->getAll();
    }

    public function findEventById($id)
    {
        return $this->classeDAO->findEventById($id);
    }

    public function getId()
    {
        return $this->id;
    }


    public function setId($id)
    {
        $this->id = $id;
    }

    public function getName()
    {
        return $this->name;
    }


    public function setName($name)
    {
        $this->name = $name;
    }


    public function getDescription()
    {
        return $this->description;
    }

    public function setDescription($description)
    {
        $this->description = $description;
    }


    public function getStartDate()
    {
        return $this->startDate;
    }


    public function setStartDate($startDate)
    {
        $this->startDate = $startDate;
    }


    public function getEndDate()
    {
        return $this->endDate;
    }


    public function setEndDate($endDate)
    {
        $this->endDate = $endDate;
    }
}
