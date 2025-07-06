<?php

class DAOFactory{

    public static function getDAOEvent($object){
        return new DAOEvent($object);
    }

    public static function getDAOUser($object){
        return new DAOUser($object);
    }

    public static function getDAOArtist($object){
        return new DAOArtist($object);
    }

    public static function getDAOImage($object){
        return new DAOImage($object);
    }
}