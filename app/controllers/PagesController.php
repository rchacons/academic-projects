<?php

class PagesController extends Controller
{
    

    public function __construct()
    {
        
    }

    public function about()
    {
        $this->loadView('about');
    }

    public function contact()
    {
        $this->loadView('contact');
    }


}