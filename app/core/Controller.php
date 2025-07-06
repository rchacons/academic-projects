<?php

abstract class Controller{

    /**
     * Permet de charger un modèle
     */
    public function loadModel($model){
        if (file_exists(APPROOT.'/models/'.$model.'.php')){
            
            require_once (APPROOT.'/models/'.$model.'.php'); 
            
            return new $model();
        }
       else{
            echo 'Le model existe pas';
       } 
    }

    /**
     * Permet d'afficher une vue 
     * @param string $view
     * @param array $data
     */
    public function loadView($view,$data = []){
     
        $folder = substr(strtolower(get_class($this)), 0 , strpos (strtolower(get_class($this)), "controller"));
        
        if (file_exists(APPROOT.'/views/'.$folder.'/'.$view . '.php')) {
            require_once APPROOT.'/views/'.$folder.'/'.$view . '.php';
        } else {
            //require_once APPROOT.'/views/index.php';
            die("View does not exists");
        }
    }
}