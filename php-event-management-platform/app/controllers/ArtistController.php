<?php

class ArtistController extends Controller
{
    public $artistModel;

    public function __construct()
    {
        $this->artistModel = $this->loadModel('Artist');
    }

    public function index()
    {
        $artists = $this->artistModel->getAll();
        $data = [
            'artists' => $artists
        ];
        $this->loadView('index', $data);
    }


    /**
     * Cree un artiste dans la bdd
     * @post id de l'artiste cree
     */
    public function create()
    {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);
            $data = array(
                'name' => trim($_POST['name']),
                'contact' => $_POST['contact'],
                'category' => $_POST['category'],
                'diffusion' => $_POST['diffusion'],
                'typeCat' => $_POST['typeCat'],
                'description' => trim($_POST['description'])
            );

            return $this->artistModel->create($data);    //On renvoie l'id de l'artist si ceci a ete cree avec succes
        } else {
            $this->loadView('create');
        }
    }

    public function validateInput()
    {

        if (!isLoggedIn()) {
            header("Location : " . URLROOT . "/ArtistController/index");
        } else if (!isset($_SESSION['admin'])) {
            header("Location : " . URLROOT . "/ArtistController/index");
        }

        $data = [
            'name' => '',
            'contact' => '',
            'category' => '',
            'typeCat' => '',
            'diffusion' => '',
            'description' => '',
            'nameError' => '',
            'contactError' => '',
            'categoryIdError' => '',
            'descriptionError' => '',
            'diffusionError' => ''
        ];


        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

            $data = [
                'name' =>  trim($_POST['name']),
                'contact' => $_POST['contact'],
                'category' => $_POST['category'],
                'typeCat' => $_POST['typeCat'],
                'description' => trim($_POST['description']),
                'diffusion' => $_POST['diffusion'],
                'nameError' => '',
                'contactError' => '',
                'categoryError' => '',
                'descriptionError' => '',
                'pictureError' => ''
            ];

            foreach ($data as $key => $value) {
                echo '<p>' . $key . ' : ' . $value . '</p>';
            }

            if (isset($_FILES['artistImage']["tmp_name"][0])) {
                echo '<p>' . $_FILES['artistImage']["name"] . '</p>';
            }

            //On verifie les champs
            if (empty($data['name'])) {
                $data['nameError'] = "Le nom d'un Artiste ne peut pas être vide";
            }


            if (empty($data['description'])) {
                $data['descriptionError'] = 'La description ne peut pas être vide';
            }

            if (empty($data['diffusion'])) {
                $data['diffusionError'] = 'Le site de diffusion ne peut pas être vide';
            }


            if (empty($data['contact'])) {
                $data['contactError'] = 'Veuillez entrer le moyen de le contacter ';
            }

            if ($data['category'] == -1) {
                $data['categoryError'] = 'Veuillez choisir une categorie';
            }

            //On valide la photo
            if (isset($_FILES["artistImage"])) {
                require_once('ImagesController.php');
                $imageC = new ImagesController();
                $errorsImg = $imageC->validateImage("artistImage");
                if (count($errorsImg) > 0) {
                    $data['pictureError'] = $errorsImg;
                }
            }



            if (empty($data['nameError']) && empty($data['descriptionError']) && empty($data['diffusionError']) && empty($data['contactError']) && empty($data['categoryError']) && empty($data['pictureError'])) {

                //S'il y a une image a enregistrer on la soumis a la bdd, sinon on soumis juste les autres champs
                if (!empty($_FILES["artistImage"]["tmp_name"])) {
                    $idImageArtist = $imageC->uploadSingleImage("artistImage");         //On store les images et on recupere leurs ids
                }
                ///On enregistre les donnees de l'evenement dans la bdd
                $idArtist = $this->create();



                if (!empty($idImageArtist)) {
                    $this->artistModel->assignImage($idArtist, $idImageArtist);          //On assigne l'image a l'artiste
                }
                header("Location: " . URLROOT . "/ArtistController");
            }
        }
        $this->loadView('create', $data);
    }


    public function update($id)
    {
        $artist = $this->artistModel->findArtistById($id);

        if (!(isLoggedIn() && isset($_SESSION['admin']))) {
            header("Location: " . URLROOT . "/ArtistController");
        }

        //On recupere l'image de l'artist
        require_once('ImagesController.php');
        $imageC = new ImagesController();
        if (!empty($imageC->getArtistPictureByArtistId($id))) {
            $picture = $imageC->getArtistPictureByArtistId($id);
        } else {
            $picture = '';
        }

        $data = [
            'artist' => $artist,
            'name' => '',
            'contact' => '',
            'categoryId' => '',
            'description' => '',
            'diffusion' => '',
            'picture' => $picture,
            'nameError' => '',
            'contactError' => '',
            'categoryIdError' => '',
            'descriptionError' => '',
            'pictureError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);



            $data = [
                'id' => $id,
                'artist' => $artist,
                'name' => trim($_POST['name']),
                'contact' => $_POST['contact'],
                'category' => $_POST['category'],
                'typeCat' => $_POST['typeCat'],
                'description' => trim($_POST['description']),
                'diffusion' => $_POST['diffusion'],
                'picture' => $picture,
                'nameError' => '',
                'contactError' => '',
                'categoryIdError' => '',
                'descriptionError' => '',
                'pictureError' => ''
            ];


            if (empty($data['name'])) {
                $data['nameError'] = "Le nom d'un événement ne peut pas être vide";
            }

            if (empty($data['description'])) {
                $data['descriptionError'] = 'La description ne peut pas être vide';
            }

            if (empty($data['diffusion'])) {
                $data['diffusionError'] = 'Le site de diffusion ne peut pas être vide';
            }


            if (empty($data['contact'])) {
                $data['contactError'] = "L'artiste doit avoir un contact !";
            }


            if ($data['category'] == -1) {
                
                $data['category'] = $_POST['categoryDisplay'];
                $data['typeCat'] = $_POST['typeCatDisplay'];
            }
          

            if (isset($_FILES["artistImage"]["tmp_name"][0])) {
                $imageCont = new ImagesController();
                $errors = $imageCont->validateImage("artistImage");
                if (count($errors) > 0) {
                    $data['pictureError'] = $errors;
                }
            }




            if ((empty($data['nameError']) && empty($data['diffusionError']) && empty($data['descriptionError']) && empty($data['contactError'])) && empty($data['pictureError'])) {

                //S'il y a une nouvelle image de couverture
                if (!empty($_FILES['artistImage']["tmp_name"])) {
                    $idImageArtist = $imageC->uploadSingleImage("artistImage");
                }

                if (!empty($idImageArtist)) {
                    //delete previous image... and then
                    $this->artistModel->assignImage($artist->id, $idImageArtist);
                }

                if ($this->artistModel->update($data)) {
                    header("Location: " . URLROOT . "/ArtistController");
                } else {
                    die("Il y a eu un problème, réesayez !");
                }
            } else {
                $this->loadView('update', $data);
            }
        }

        $this->loadView('update', $data);
    }

    public function delete($id)
    {
        $artist = $this->artistModel->findArtistById($id);

        if (!(isLoggedIn() && isset($_SESSION['admin']))) {
            header("Location: " . URLROOT . "/ArtistController");
        }

        $data = [
            'artist' => $artist,
            'name' => '',
            'contact' => '',
            'categoryId' => '',
            'description' => '',
            'diffusion' => '',
            'nameError' => '',
            'contactError' => '',
            'categoryIdError' => '',
            'descriptionError' => '',
        ];


        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

            if ($this->artistModel->delete($id)) {
                header("Location: " . URLROOT . "/ArtistController");
            } else {
                die("Il y a eu un problème, réesayez !");
            }
        }
    }
    /**
     * Renvoie la liste complete des categories
     */
    public function getCategories()
    {
        return $this->artistModel->getCategories();
    }

    /**
     * Renvoie la liste complete des categories en JSON
     */
    public function getCategoriesJSON()
    {
        $result = $this->artistModel->getCategories();
    }

    /**
     * Renvoie les differents nom de categorie
     */
    public function getCategoriesName()
    {
        $result = $this->artistModel->getCategoriesName();

        // echo "<label>Categorie:</label>";
        // echo "<select id='category' name='category' onchange='go()'>";
        echo "<option value='-1'>Aucun</option>";

        foreach ($result as $catName) {
            echo "<option value='" . $catName->name . "'>" . $catName->name . "</option>";
        }
    }

    /**
     * Renvoie les differents types selon la categorie choisie
     */
    public function getCategoryType($selectedCategory)
    {
        $result = $this->artistModel->getCategoryType($selectedCategory);

        echo "<option value='-1'>Choisir un type</option>";

        foreach ($result as $catType) {
            echo "<option value='" . $catType->type . "'>" . $catType->type . "</option>";
        }
    }

    /**
     * Assigne un image a l'artist
     */
    public function assignImage($artistId, $imageId)
    {
        return $this->artistModel->assignImage($artistId, $imageId);
    }

    /**
     *  Retire une image de couverture du tableau event
     */
    public function removeImage($artistId)
    {
        return $this->artistModel->removeImage($artistId);
    }
}
