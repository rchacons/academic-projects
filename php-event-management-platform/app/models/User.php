<?php

class User
{

    public function __construct()
    {
        $this->classeDAO = DAOFactory::getDAOUser(SELF::class);
    }

    /**
     * Cree un objet dans la bdd
     */
    public function create($objet)
    {
        ////
    }

    /**
     * Renvoie un objet en fonction de l'id
     */
    public function read($id)
    {
        ////
    }

    /**
     * Renvoie true si le mail existe deja dans la bdd
     */
    public function findUserByEmail($email)
    {
        return $this->classeDAO->findUserByEmail($email);
    }

    /**
     * Renvoie true si le nom d'utilisateur existe dans la bdd
     */
    public function findUserByUsername($username)
    {
        return $this->classeDAO->findUserByUsername($username);
    }

    /**
     * Enregistre les informations de l'utilisateur dans la bdd
     */
    public function register($data)
    {
        return $this->classeDAO->register($data);
    }

    
    public function login($username, $password)
    {
        return $this->classeDAO->login($username, $password);
    }
}
