<?php
//On appelle le modèle et le contrôleur principaux
require_once 'core/App.php';
require_once 'core/Controller.php';
require_once 'config/config.php';
require_once 'helpers/session_helper.php';


//On appelle les classes DAO
spl_autoload_register(function($class_name){
    require 'DAO/'.$class_name.'.php';
});

