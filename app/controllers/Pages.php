<?php
class Pages extends Controller {
    
    public function __construct() {
        //$this->userModel = $this->model('User');
    }

    public function index() {
       
        $data = [
            'title' => 'Home page'
        ];
        $this->loadView('index', $data);
    }

    public function about() {
        $this->loadView('about');
    }
}