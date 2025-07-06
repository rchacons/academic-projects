<?php
class UsersController extends Controller
{
    public $userModel;

    public function __construct()
    {
        $this->userModel = $this->loadModel('User');
    }


    public function register()
    {
        $data = [
            'username' => '',
            'email' => '',
            'password' => '',
            'confirmPassword' => '',
            'usernameError' => '',
            'emailError' => '',
            'passwordError' => '',
            'confirmPasswordError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {       //Ou on aurait pu utiliser if(isset($_POST['submit']))
            //Sanitize post data
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);    //Supprime les données potentiellement nuisibles pour l'application

            $data = [
                'username' => trim($_POST['username']),
                'email' => trim($_POST['email']),
                'password' => trim($_POST['password']),
                'confirmPassword' => trim($_POST['confirmPassword']),
                'usernameError' => '',
                'emailError' => '',
                'passwordError' => '',
                'confirmPasswordError' => ''
            ];

            $nameValidation = "/^[a-zA-Z0-9]*$/";
            $passwordValidation = "/^(.{0,7}|[^a-z]*|[^\d]*)$/i";

            //Validation du nom d'utilisateur en lettres et chiffres
            if (empty($data['username'])) {
                $data['usernameError'] = "Veuillez entrer votre nom d'utilisateur";
            } elseif (!preg_match($nameValidation, $data['username'])) {
                $data['usernameError'] = 'Le nom ne peut contenir que des lettres et des chiffres.';
            }

            //Validation de l'email
            if (empty($data['email'])) {
                $data['emailError'] = "Veuillez entrer votre email";
            } elseif (!filter_var($data['email'], FILTER_VALIDATE_EMAIL)) {
                $data['emailError'] = "Veuillez saisir le bon format";
            } else {
                if ($this->userModel->findUserByEmail($data['email'])) {
                    $data['emailError'] = "L'email est déjà pris";
                }
            }

            //Validation du mot de pass (longueur et valeurs numériques)
            if (empty($data['password'])) {
                $data['passwordError'] = 'Veuillez entrer un mot de passe';
            } elseif (strlen($data['password']) < 6) {
                $data['passwordError'] = 'Le mot doit contenir au moins 8 caracteres';
            } elseif (preg_match($passwordValidation, $data['password'])) {
                $data['passwordError'] = 'Le mot de passe doit contenir au moins une valeur numérique.';
            }

            //Validation confirmation mot de passe
            if (empty($data['confirmPassword'])) {
                $data['confirmPasswordError'] = 'Veuillez entrer un mot de passe';
            } elseif ($data['password'] != $data['confirmPassword']) {
                $data['confirmPasswordError'] = 'Les mots de passe ne correspondent pas, veuillez réessayer.';
            }

            //On s'assure que les erreurs sont vides 
            if (empty($data['usernameError']) && empty($data['emailError']) && empty($data['passwordError']) && empty($data['confirmPasswordError'])) {

                // Hash password
                $data['password'] = password_hash($data['password'], PASSWORD_DEFAULT);

                //Register user from model function
                if ($this->userModel->register($data)) {
                    //Redirect to the login page
                    header('location: ' . URLROOT . '/UsersController/login');
                } else {
                    die('Il y a eu un problème, réesayez.');
                }
            }
        }

        $this->loadView('register', $data);
    }

    public function login()
    {
        $data = [
            'title' => 'Login',
            'usernameError' => '',
            'passwordError' => ''
        ];

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            //Sanitize post data
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);    //Supprime les données potentiellement nuisibles pour l'application
            $data = [
                'username' => trim($_POST['username']),
                'password' => trim($_POST['password']),
                'usernameError' => '',
                'passwordError' => '',
            ];

            //Validation du nom d'utilisateur 
            if (empty($data['username'])) {
                $data['usernameError'] = "Veuillez entrer votre nom d'utilisateur";
            }

            //Validation du mot de pass 
            if (empty($data['password'])) {
                $data['passwordError'] = 'Veuillez entrer un mot de passe';
            }

            //On s'assure que les erreurs sont vides 
            if (empty($data['usernameError']) && empty($data['passwordError'])) {

                //On verifie s'il s'agit de l'admin
                if ($data['username'] == ADMLOGIN && $data['password'] == ADMPASS) {
                    $this->createAdminSession();
                } else {
                    //Sinon, on verifie l'identifiant et le mot de passe de l'utilisateur
                    $loggedInUser = $this->userModel->login($data['username'], $data['password']);

                    if ($loggedInUser) {
                        $this->createUserSession($loggedInUser);
                    } else {
                        $data['passwordError'] = 'Password or username is incorrect. Please try again.';

                        $this->loadView('login', $data);
                    }
                }
            }
        } else {
            $data = [
                'username' => '',
                'password' => '',
                'usernameError' => '',
                'passwordError' => ''
            ];
        }
        $this->loadView('login', $data);
    }

    public function createAdminSession()
    {
        $_SESSION['admin'] = ADMLOGIN;
        header('location:' . URLROOT . '/pages/index');
        
    }

    public function createUserSession($user)
    {
        $_SESSION['user_id'] = $user->id;
        $_SESSION['username'] = $user->username;
        $_SESSION['email'] = $user->email;
        header('location:' . URLROOT . '/pages/index');
    }

    public function logout()
    {
        unset( $_SESSION['admin']);
        unset($_SESSION['user_id']);
        unset($_SESSION['username']);
        unset($_SESSION['email']);
        header('location:' . URLROOT . '/UsersController/login');
    }
}
