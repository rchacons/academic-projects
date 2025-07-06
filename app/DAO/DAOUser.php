<?php

class DAOUser extends genericDAO
{
    private $user;



    public function __construct($user)
    {
        $this->user = $user;
        $this->setTable('user');
        $this->connect();
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

        $sql = 'SELECT * FROM users WHERE email = :email';
        $data = array(
            ':email' => $email
        );
        $query = $this->_connexion->prepare($sql);
        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }
        //Si on trouve que l'email existe deja on renvoie true
        if ($query->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie true si le nom d'utilisateur existe dans la bdd
     */
    public function findUserByUsername($username)
    {

        $sql = 'SELECT * FROM users WHERE username = :username';
        $data = array(':username' => $username);
        $query = $this->_connexion->prepare($sql);
        $query->execute($data);

        try {
            if ($query->rowCount() > 0) {
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
     * Enregistre les informations de l'utilisateur dans la bdd
     */
    public function register($data)
    {
        //D'abord on verifie si le nom d'utilisateur existe deja

        $sql = 'INSERT INTO users(username,email,password) VALUES (:username,:email,:password)';
        $data = array(
            ':username' => $data['username'],
            ':email' => $data['email'],
            ':password' => $data['password'],
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

    public function login($username, $password)
    {
        $sql = 'SELECT * FROM users WHERE username = :username';

        $data = array(
            ':username' => $username,
        );
        $query = $this->_connexion->prepare($sql);

        try {
            $query->execute($data);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            die();
        }



        $row = $query->fetch(PDO::FETCH_OBJ);

        $hashedPassword = $row->password;

        if (password_verify($password, $hashedPassword)) {
            return $row;
        } else {
            return false;
        }
    }

    /**
     * MAJ un objet
     */
    public function update($objet)
    {
        ////
    }

    /**
     * Supprime un objet de la bdd
     */
    public function delete($obj)
    {
        ////
    }


    public function getUser()
    {
        return $this->user;
    }


    public function setUser($user)
    {
        $this->user = $user;
    }
}
