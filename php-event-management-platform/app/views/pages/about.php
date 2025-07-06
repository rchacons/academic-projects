<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>ANOMIC ELEMENTS</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free Website Template" name="keywords">
    <meta content="Free Website Template" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- CSS Libraries -->
    <link href="<?php echo URLROOT ?>/public/https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="<?php echo URLROOT ?>/public/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/animate/animate.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="<?php echo URLROOT ?>/public/css/style.css" rel="stylesheet">
</head>


<body data-spy="scroll" data-target=".navbar" data-offset="51">
    <!-- new navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand" href="#page-top">ANOMIC <span style="color: #e29f01;">ELEMENTS</span></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
                <i class="fas fa-bars ms-1"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                    <li class="nav-item">
                        <?php if (isset($_SESSION['admin'])) : ?>
                            <span>Admin</span>
                        <?php endif; ?>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/index">Home </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/PagesController/about">À propos </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/ArtistController/index">Artistes </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/EventsController/index">Evenements </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/galerie">Galerie </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?php echo URLROOT; ?>/PagesController/contact">Contact </a>
                    </li>
                    <li class="btn-login nav-item">
                        <?php if (isset($_SESSION['user_id']) || isset($_SESSION['admin'])) : ?>
                            <a class="nav-link" href="<?php echo URLROOT; ?>/UsersController/logout"> Se déconnecter </a>
                        <?php else : ?>
                            <a class="nav-link" href="<?php echo URLROOT; ?>/UsersController/login"> S'identifier </a>
                        <?php endif; ?>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- About Start -->
        <div class="container-fluid">
            <div class="row align-items-center">
                <div class="col-lg-3">
                        <img src="<?php echo URLROOT ?>/public/img/Accueil/anomic-elements.jpg" alt="Image" width='250' height="250">
                    
                </div>
                <div class="col-lg-6">
                    <div class="about-content">
                        <div class="section-header text-left">
                            <h2>A propos de nous</h2>
                        </div>
                        <div class="about-text">
                            <div class="container-event" id="aboutUs">
                                <p>
                                    Anomic Element, c’est le projet commun d’une bande de potes passionnés par la culture Psytrance. Ce projet associatif s’inscrit dans une longue épopée, parsemée de moments partagés dans des événements en tous genres, il s’est peu à peu concrétisé grâce à ses éléments moteurs.
                                    Partant de simples discussions, né d’un courant d’air, ce projet est désormais en train de gonfler ses voiles !
                                </p>
                                <p>
                                    Cette bande de potes se disait « et pourquoi pas nous ? », c’est vrai ça, pourquoi pas ? Si on est aussi passionnés par la musique et par tous ces univers farfelus dans lesquels les soirées Psy nous plongent, on doit être capable de le partager aussi !
                                    Maintenant que c’est concret, l’idée principale de notre association est d’organiser des soirées Psytrance, mais pas que, on aime bien la Psytrance aussi !
                                </p>
                                <p>Ce genre musical étant d’une largeur parfois insolente, il offre un nombre incalculable de possibilités en termes de création d’ambiances et d’atmosphères. </p>
                                <p>On cherchera à explorer ces atmosphères avec vous pour vous plonger à notre tour dans des univers fantaisistes, grâce à des décors réfléchis, créés pour mettre en valeur les artistes et le thème de chaque événement.
                                    Issus des Free Parties, notre collectif est adepte du partage et de l’ouverture d’esprit, mais pas que, on aime surtout cette ambiance où chacun des éléments éclaire les autres de son brin de folie, c’est de là qu’est venu le nom du projet. </p>
                                <p>Des éléments anomiques sont des éléments qui ne respectent pas les lois et les normes de l’environnement dans lequel ils évoluent. On s’est dit que ce terme reflétait plutôt bien notre fougue et celle du public Psytrance</p>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- About End -->
    <!-- Footer Start -->
    <div class="footer wow fadeIn" data-wow-delay="0.3s">
        <div class="container-fluid">
            <div class="container">
                <div class="footer-info">
                    <h2>ANOMIC <span style="color: #e29f01;">ELEMENTS</span></h2>

                    <div class="footer-social">

                        <a href="https://www.facebook.com/anomicelements/about/?ref=page_internal"><i class="fab fa-facebook-f"></i></a>
                    </div>
                </div>
            </div>
            <div class="container copyright">
                <p>Copyright &copy; 2021, Balbina & Roberto. Designed by <a href="http://gettemplate.com/" rel="designer">AMONIC ELEMENTS</a></p>
            </div>
        </div>
    </div>
    <!-- Footer End -->



    <!-- Back to top button -->
    <a href="#" class="btn back-to-top"><i class="fa fa-chevron-up"></i></a>


    <!-- Pre Loader -->
    <div id="loader" class="show">
        <div class="loader"></div>
    </div>


    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/easing/easing.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/wow/wow.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/waypoints/waypoints.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/typed/typed.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/lib/lightbox/js/lightbox.min.js"></script>

    <!-- Contact Javascript File -->
    <script src="<?php echo URLROOT ?>/public/mail/jqBootstrapValidation.min.js"></script>
    <script src="<?php echo URLROOT ?>/public/mail/contact.js"></script>

    <!-- Template Javascript -->
    <script src="<?php echo URLROOT ?>/public/js/main.js"></script>
</body>

</html>