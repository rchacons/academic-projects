<?php

class EventsController extends Controller
{
    public $eventModel;

    public function __construct()
    {
        $this->eventModel = $this->loadModel('Event');
    }

    public function index()
    {
        $events = $this->eventModel->getAll();
        $data = [
            'events' => $events
        ];
        $this->loadView('index', $data);
    }


    /**
     * Cree un evenement dans la bdd
     * @post id de l'evenement cree
     */
    public function create()
    {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

            $data = [
                'name' => trim($_POST['name']),
                'startDate' => $_POST['startDate'],
                'endDate' => $_POST['endDate'],
                'description' => trim($_POST['description']),
                'capacity' => $_POST['capacity'],
                'coverImageId' => ''
            ];

            // if ($this->eventModel->create($data)) {
            //     //header("Location: " . URLROOT . "/EventsController");
            //     return true;
            // } else {
            //     die("Il y a eu un problème, réesayez.!");
            // }
            return $this->eventModel->create($data);    //On renvoie l'id de l'evenement si ceci a ete cree avec succes
        } else {
            $this->loadView('create');
        }
    }

    public function validateInput()
    {
        if (!isLoggedIn()) {
            header("Location : " . URLROOT . "/EventsController/index");
        } else if (!isset($_SESSION['admin'])) {
            header("Location : " . URLROOT . "/EventsController/index");
        }

        $data = [
            'name' => '',
            'startDate' => '',
            'endDate' => '',
            'description' => '',
            'capacity' => '',
            'nameError' => '',
            'descriptionError' => '',
            'startDateError' => '',
            'endDateError' => '',
            'pictureError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);


            $data = [
                'name' => trim($_POST['name']),
                'startDate' => $_POST['startDate'],
                'endDate' => $_POST['endDate'],
                'description' => trim($_POST['description']),
                'capacity' => $_POST['capacity'],
                'coverImageId' => '',
                'nameError' => '',
                'descriptionError' => '',
                'startDateError' => '',
                'endDateError' => '',
                'capacityError' => '',
                'coverImageError' => '',
                'pictureError' => ''

            ];

            foreach ($data as $key => $value) {
                echo '<p>' . $key . ' : ' . $value . '</p>';
            }

            if (isset($_FILES['coverImage']["tmp_name"][0])) {
                echo '<p>' . $_FILES['coverImage']["name"] . '</p>';
            }

            //On verifie les champs
            if (empty($data['name'])) {
                $data['nameError'] = "Le nom d'un événement ne peut pas être vide";
            }

            if (empty($data['description'])) {
                $data['descriptionError'] = 'La description ne peut pas être vide';
            }

            if (empty($data['startDate'])) {
                $data['startDateError'] = 'Veuillez entrer une date de debut';
            }
            if (empty($data['endDate'])) {
                $data['endDateError'] = 'Veuillez entrer une date de fin';
            }

            if (empty($data['capacity'])) {
                $data['capacityError'] = 'Veuillez entrer une capacité';
            }

            //On valide le cover photo
            if (isset($_FILES["coverImage"])) {
                require_once('ImagesController.php');
                $imageC = new ImagesController();
                $errorsCover = $imageC->validateImage("coverImage");
                if (count($errorsCover) > 0) {
                    $data['coverImageError'] = $errorsCover;
                }
            }

            //On valide les images
            if (isset($_FILES["files"])) {
                require_once('ImagesController.php');
                $imageC = new ImagesController();
                $errors = $imageC->validateImage("files");
                if (count($errors) > 0) {
                    $data['pictureError'] = $errors;
                }
            }

            //Si tous les champs sont correctes 
            if (empty($data['nameError']) && empty($data['descriptionError']) && empty($data['startDateError']) && empty($data['endDateError']) && empty($date['capacityError']) && empty($data['coverImageError']) && empty($data['pictureError'])) {

                //On enregistre les donnees de l'evenement dans la bdd
                $idEvent = $this->create();

                //S'il y a une image de couverture a enregistrer on la soumis a la bdd, sinon on soumis juste les autres champs
                if (!empty($_FILES["coverImage"]["tmp_name"])) {
                    $idImageCouverture = $imageC->uploadSingleImage("coverImage");         //On store les images et on recupere leurs ids
                }

                //S'il y a une image a enregistrer on la soumis a la bdd, sinon on soumis juste les champs
                if (!empty($_FILES["files"]["tmp_name"])) {
                    $idImages = $imageC->uploadImage("files");         //On store les images et on recupere leurs ids
                }

                if (!empty($idImageCouverture)) {
                    $this->eventModel->assignImage($idEvent, $idImageCouverture);      //On assigne  l'image a l'evenement (table eventsImage)
                    $this->eventModel->assignCoverImage($idEvent, $idImageCouverture);  //On la define comme son image de couverture
                }

                if (!empty($idImages)) {
                    foreach ($idImages as $idImage) {
                        $this->eventModel->assignImage($idEvent, $idImage);      //On assigne chaque image a l'evenement
                    }
                }
                header("Location: " . URLROOT . "/EventsController");
            }
        }
        $this->loadView('create', $data);
    }


    public function update($id)
    {

        $event = $this->eventModel->findEventById($id);


        if (!(isLoggedIn() && isset($_SESSION['admin']))) {
            header("Location: " . URLROOT . "/EventsController");
        }
        //On recupere l'image de couverture de l'evenement
        require_once('ImagesController.php');
        $imageC = new ImagesController();
        if (!empty($imageC->getCoverImageByEventId($id))) {
            $coverImage = $imageC->getCoverImageByEventId($id);
        } else {
            $coverImage = '';
        }


        //On recupere les images correspondantes a l'evenement
        if (!empty($imageC->getImageByEventId($id))) {
            $images = $imageC->getImageByEventId($id);
        } else {
            $images = '';
        }

        $data = [
            'event' => $event,
            'name' => '',
            'coverImage' => $coverImage,
            'startDate' => '',
            'endDate' => '',
            'capacity' => '',
            'images' => $images,
            'description' => '',
            'nameError' => '',
            'capacityError' => '',
            'coverImageError' => '',
            'pictureError' => '',
            'descriptionError' => '',
            'startDateError' => '',
            'endDateError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

            $data = [
                'id' => $id,
                'event' => $event,
                'name' => trim($_POST['name']),
                'startDate' => $_POST['startDate'],
                'endDate' => $_POST['endDate'],
                'coverImage' => $coverImage,
                'capacity' => $_POST['capacity'],
                'images' => $images,
                'description' => trim($_POST['description']),
                'nameError' => '',
                'pictureError' => '',
                'capacityError' => '',
                'coverImageError' => '',
                'descriptionError' => '',
                'startDateError' => '',
                'endDateError' => ''
            ];



            if (empty($data['name'])) {
                $data['nameError'] = "Le nom d'un événement ne peut pas être vide";
            }

            if (empty($data['description'])) {
                $data['descriptionError'] = 'La description ne peut pas être vide';
            }

            if (empty($data['capacity'])) {
                $data['capacityError'] = "L'événement doit avoir une capacité";
            }


            if (isset($_FILES["files"]["tmp_name"][0])) {
                $imageCont = new ImagesController();
                $errors = $imageCont->validateImage("files");
                if (count($errors) > 0) {
                    $data['pictureError'] = $errors;
                }
            } else {
                $data['pictureError'] = 'Aucune photo ajoutée';
            }


            if ((empty($data['nameError']) && empty($data['descriptionError'] && empty($data['capacity']))) && empty($data['pictureError'])) {


                //S'il y a une nouvelle image de couverture
                if (!empty($_FILES['coverImage']["tmp_name"])) {
                    $idImageCouverture = $imageC->uploadSingleImage("coverImage");
                }

                if (!empty($idImageCouverture)) {
                    $this->eventModel->assignImage($event->id, $idImageCouverture);      //On assigne  l'image a l'evenement (table eventsImage)
                    $this->eventModel->assignCoverImage($event->id, $idImageCouverture);  //On la define comme son image de couverture
                }

                //S'il y a une image a enregistrer on la soumis a la bdd
                if (!empty($_FILES["files"]["tmp_name"][0])) {
                    $idImages = $imageCont->uploadImage("files");         //On store les images et on recupere leurs ids
                }

                if (!empty($idImages)) {
                    foreach ($idImages as $idImage) {
                        $this->eventModel->assignImage($id, $idImage);      //On assigne chaque image a l'evenement
                    }
                }

                if ($this->eventModel->update($data)) {
                    header("Location: " . URLROOT . "/EventsController");
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
        $event = $this->eventModel->findEventById($id);

        if (!(isLoggedIn() && isset($_SESSION['admin']))) {
            header("Location: " . URLROOT . "/EventsController");
        }

        $data = [
            'event' => $event,
            'name' => '',
            'startDate' => '',
            'endDate' => '',
            'description' => '',
            'nameError' => '',
            'descriptionError' => '',
            'dateError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

            if ($this->eventModel->delete($id)) {
                header("Location: " . URLROOT . "/EventsController");
            } else {
                die("Il y a eu un problème, réesayez !");
            }
        }
    }

    /**
     * Assigne un image de couverture a l'evenement
     */
    public function assignCoverImage($eventId, $imageId)
    {
        return $this->eventModel->assignCoverImage($eventId, $imageId);
    }

    /**
     *  Retire une image de couverture du tableau event
     */
    public function removeCoverImage($eventId)
    {
        return $this->eventModel->removeCoverImage($eventId);
    }


    // public function create()
    // {
    //     if (!isLoggedIn()) {
    //         header("Location : " . URLROOT . "/EventsController/index");
    //     } else if (!isset($_SESSION['admin'])) {
    //         header("Location : " . URLROOT . "/EventsController/index");
    //     }

    //     $data = [
    //         'name' => '',
    //         'startDate' => '',
    //         'endDate' => '',
    //         'description' => '',
    //         'nameError' => '',
    //         'descriptionError' => '',
    //         'dateError' => ''
    //     ];

    //     if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //         $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

    //         $data = [
    //             'name' => trim($_POST['name']),
    //             'startDate' => $_POST['startDate'],
    //             'endDate' => $_POST['endDate'],
    //             'description' => trim($_POST['description']),
    //             'nameError' => '',
    //             'descriptionError' => '',
    //             'dateError' => ''

    //         ];

    //         if (empty($data['name'])) {
    //             $data['nameError'] = "Le nom d'un événement ne peut pas être vide";
    //         }

    //         if (empty($data['description'])) {
    //             $data['descriptionError'] = 'La description ne peut pas être vide';
    //         }

    //         if (empty($data['startDate']) || empty($data['endDate'])) {
    //             $data['dateError'] = 'Veuillez entrer une date';
    //         }

    //         if (empty($data['nameError']) && empty($data['descriptionError']) && empty($date['dateError'])) {
    //             if ($this->eventModel->create($data)) {
    //                 header("Location: " . URLROOT . "/EventsController");
    //             } else {
    //                 die("Il y a eu un problème, réesayez.!");
    //             }
    //         } else {
    //             $this->loadView('create', $data);
    //         }
    //     }

    //     $this->loadView('create', $data);
    // }

}
