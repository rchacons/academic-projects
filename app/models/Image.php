<?php
// ini_set('display_errors', 1);
// ini_set('display_startup_errors', 1);
// error_reporting(E_ALL);
class Image
{

    public function __construct()
    {
        $this->classeDAO = DAOFactory::getDAOImage(SELF::class);
    }

    /**
     * Permet de valider les images par leur taille, type de fichier et l'existence.
     * @pre le nom de la balise <input type "file" name=? >
     * @post Une liste d'erreurs si jamais il y en a
     */
    public function validateImage($nameFiles)
    {

        $errors = array();
        //$uploadedFiles = array(); for uploadImage();
        $extension = array("jpeg", "jpg", "png");     //Allowed extensions
        $bytes = 1024;
        $KB = 1024;
        $totalBytes = $bytes * $KB; //1 MB
        $fileDestination = "../public/img/uploads";


        $counter = 0;

        foreach ($_FILES[$nameFiles]["tmp_name"] as $key => $tmp_name) {
            $temp = $_FILES[$nameFiles]["tmp_name"][$key];
            $name = $_FILES[$nameFiles]["name"][$key];

            //Si on a pas soumis de photo
            if (empty($temp)) {
                break;
            }

            //On augmente le compteur des fichiers
            $counter++;

            //On verifie si l'extension corresponde
            $ext = pathinfo($name, PATHINFO_EXTENSION);
            if (!in_array(strtolower($ext), $extension)) {
                array_push($errors, $name . $name . " file type is invalid.");
            }
            //On verifie si la taille est plus petite  que la taille fixe
            if ($_FILES[$nameFiles]["size"][$key] > $totalBytes) {
                array_push($errors, $name . " file size is larger than the 1 MB ");
            }
            //On verifie si l'image existe deja
            if (file_exists($fileDestination . "/" . $name)) {
                array_push($errors, $name . "File already exist.");
            }

            //This goes to uploadImage();
            // if($uploadThisFile){
            //     $filename=basename($file_name,$ext);
            //     $newFileName=$filename.$ext;                
            //     move_uploaded_file($_FILES["files"]["tmp_name"][$key],$fileDestination.$newFileName);

            //     $query = "INSERT INTO UserFiles(FilePath, FileName) VALUES('Upload','".$newFileName."')";
            //      //I think here we can array_push($uploadedFiles and $filename)
            //     //mysqli_query($conn, $query);            
            // }

        }

        // if ($counter > 0) {
        //     if (count($errors) > 0) {
        //         echo "<b>Errors:</b>";
        //         echo "<br/><ul>";
        //         foreach ($errors as $error) {
        //             echo "<li>" . $error . "</li>";
        //         }
        //         echo "</ul><br/>";
        //     }
        // }
        return $errors;
    }

    /**
     *  Permet de soumettre les directions des images dans la bdd
     * @pre le nom de la balise <input type "file" name=? >
     *  @post array avec les id si les images ont été soumises à la bdd
     */
    public function uploadImage($nameFiles)
    {
        $idImages = array();

        foreach ($_FILES[$nameFiles]["tmp_name"] as $key => $tmp_name) {
            $temp = $_FILES[$nameFiles]["tmp_name"][$key];
            $name = $_FILES[$nameFiles]["name"][$key];
            $ext = pathinfo($name, PATHINFO_EXTENSION);
            $filename = basename($name, $ext);
            $newFileName = $filename . $ext;

            if ($nameFiles == 'artistImage') {

                $fileDestination = "public/img/uploads/artists";
            } else {
                $fileDestination = "public/img/uploads/events";
            }


            move_uploaded_file($temp, '../' . $fileDestination . '/' . $newFileName);       // le '../' est necessaire pour qu'il reconnaite le dossier(c'etait pas possible avec URLROOT)

            $nameDB = $fileDestination . '/' . $newFileName;    //nom du forme: public/img/upload/nomPhoto.png

            //On store l'image et on recupere les id 
            $id = $this->classeDAO->create($nameDB);
            array_push($idImages, $id);
        }
        return $idImages;
    }

    /**
     *  Permet de soumettre les directions de l'image dans la bdd
     * @pre le nom de la balise <input type "file" name=? >
     *  @post array avec les id si les images ont été soumises à la bdd
     */
    public function uploadSingleImage($nameFiles)
    {

        $temp = $_FILES[$nameFiles]["tmp_name"];
        $name = $_FILES[$nameFiles]["name"];
        $ext = pathinfo($name, PATHINFO_EXTENSION);
        $filename = basename($name, $ext);
        $newFileName = $filename . $ext;
        if ($nameFiles == 'artistImage') {
            $fileDestination = "public/img/uploads/artists";
        } else {
            $fileDestination = "public/img/uploads/events";
        }

        move_uploaded_file($temp, '../' . $fileDestination . '/' . $newFileName);       // le '../' est necessaire pour qu'il reconnaite le dossier(c'etait pas possible avec URLROOT)

        $nameDB = $fileDestination . '/' . $newFileName;    //nom du forme: public/img/upload/nomPhoto.png

        //On store l'image et on recupere les id 
        $id = $this->classeDAO->create($nameDB);

        return $id;
    }

    /**
     * Supprime une image de la bdd etant donné un id
     */
    public function deleteImage($id)
    {
        $this->classeDAO->delete($id);
    }


    /**
     *  Renvoie un array des images correspondantes à un evenement
     */
    public function getImageByEventId($eventId)
    {
        return $this->classeDAO->getImageByEventId($eventId);
    }

    /**
     *  Renvoie l'image de couverture de l'evenement
     */
    public function getCoverImageByEventId($eventId)
    {
        return $this->classeDAO->getCoverImageByEventId($eventId);
    }

    /**
     * Renvoie l'image de l'artiste
     */
    public function getArtistPictureByArtistId($artistId)
    {
        return $this->classeDAO->getArtistPictureByArtistId($artistId);
    }
}
