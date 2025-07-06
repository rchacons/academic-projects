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
    <link href="https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,900&display=swap" rel="stylesheet">

    <!-- CSS Libraries -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/animate/animate.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="<?php echo URLROOT ?>/public/css/style.css" rel="stylesheet">
</head>


<!-- new navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand" href="#page-top">ANOMIC <span style="color: #ffffff;">ELEMENTS</span></a>
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
<div class="container-event center">
<h1>Nous contacter</h1>
<!-- Contact Start -->
<div class="contact wow fadeInUp" data-wow-delay="0.1s" id="contact">
    <div class="container-fluid">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-4"></div>
                <div class="col-md-8">
                    <div class="contact-form">
                        <div id="success"></div>
                        <form name="sentMessage" id="contactForm" novalidate="novalidate">
                            <div class="control-group">
                                <input type="text" class="form-control" id="name" placeholder="Nom" required="required" data-validation-required-message="Please enter your name" />
                                <p class="help-block"></p>
                            </div>
                            <div class="control-group">
                                <input type="email" class="form-control" id="email" placeholder="Email" required="required" data-validation-required-message="Please enter your email" />
                                <p class="help-block"></p>
                            </div>
                            <div class="control-group">
                                <input type="text" class="form-control" id="subject" placeholder="Sujet" required="required" data-validation-required-message="Please enter a subject" />
                                <p class="help-block"></p>
                            </div>
                            <div class="control-group">
                                <textarea class="form-control" id="message" placeholder="Message" required="required" data-validation-required-message="Please enter your message"></textarea>
                                <p class="help-block"></p>
                            </div>
                            <div>
                                <button class="btn" type="submit" id="sendMessageButton">Envoyer Message !</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

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