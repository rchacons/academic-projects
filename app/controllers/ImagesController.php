<?php

class ImagesController extends Controller
{
    public $imageModel;



    public function __construct()
    {
        $this->imageModel = $this->loadModel('Image');
    }

    /**
     * Permet de valider les images par leur taille, type de fichier et l'existence.
     * @pre le nom de la balise <input type "file" name=? >
     * @post Une liste d'erreurs si jamais il y en a
     */
    public function validateImage($nameFiles)
    {
        return $this->imageModel->validateImage($nameFiles);
    }

    /**
     *  Permet de soumettre les directions des images dans la bdd
     * @pre le nom de la balise <input type "file" name=? >
     *  @post array avec les id  si les images ont été soumises à la bdd
     */
    public function uploadImage($nameFiles)
    {
        return $this->imageModel->uploadImage($nameFiles);
    }


    /**
     *  Permet de soumettre les directions de l'image dans la bdd
     * @pre le nom de la balise <input type "file" name=? >
     *  @post array avec les id si les images ont été soumises à la bdd
     */
    public function uploadSingleImage($nameFiles)
    {
        return $this->imageModel->uploadSingleImage($nameFiles);
    }

    /**
     * Supprime une image de la bdd etant donné un id
     */
    public function deleteImage($id)
    {
        $this->imageModel->deleteImage($id);
    }

    /**
     *  Renvoie un array des images correspondantes à un evenement
     */
    public function getImageByEventId($eventId)
    {
        return $this->imageModel->getImageByEventId($eventId);
    }

    /**
     *  Renvoie un array des images correspondantes à un evenement encodes en JSON
     */
    public function getJsonImageByEventId($eventId)
    {
        echo json_encode($this->imageModel->getImageByEventId($eventId));
    }


    /**
     *  Renvoie l'image de couverture de l'evenement
     */
    public function getCoverImageByEventId($eventId)
    {
        return $this->imageModel->getCoverImageByEventId($eventId);
    }

    /**
     *  Renvoie l'image de couverture de l'evenement en JSON
     */
    public function getJsonCoverImgByEventId($eventId)
    {
        echo json_encode($this->imageModel->getCoverImageByEventId($eventId));
    }



    /**
     * Renvoie l'image de l'artiste
     */
    public function getArtistPictureByArtistId($artistId)
    {
        return $this->imageModel->getArtistPictureByArtistId($artistId);
    }

    /**
     * Renvoie l'image de l'artiste
     */
    public function getJsonArtistById($artistId){
        echo json_encode($this->imageModel->getArtistPictureByArtistId($artistId));
    }
}
