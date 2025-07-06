<?php

class App
{

    protected $controller = 'Pages';

    protected $method = 'index';

    protected $params = [];

    public function __construct()
    {
        $url = $this->parseUrl();

        //On verifie si le controleur dans url est valide
        if (file_exists(APPROOT . '/controllers/' . $url[0] . '.php')) {
            $this->controller = $url[0];
            unset($url[0]);
        }

        require_once APPROOT . '/controllers/' . $this->controller . '.php';

        $this->controller = new $this->controller;            //On cree un objet a partir de la variable controller

        //Maintenant que controller est un objet, on peut verifier si la methode dans url est valide
        if (isset($url[1])) {
            if (method_exists($this->controller, $url[1])) {
                $this->method = $url[1];
                unset($url[1]);
            }
        }

        $this->params = $url ? array_values($url) : [];     //S'il existe des parametres dans l'url on modifie l'array, on laisse vide sinon

        //On appel la fonction
        call_user_func_array([$this->controller, $this->method], $this->params);
    }


    public function parseUrl()
    {
        if (isset($_GET['url'])) {
            $url = rtrim($_GET['url'], '/');
            $url = filter_var($url, FILTER_SANITIZE_URL);
            $url = explode('/', $url);
            return $url;
        }
    }
}
