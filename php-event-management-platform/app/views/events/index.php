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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/animate/animate.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="<?php echo URLROOT ?>/public/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="<?php echo URLROOT ?>/public/css/style.css" rel="stylesheet">
    <script>
        $(window).on("load resize ", function() {
            var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
            $('.tbl-header').css({
                'padding-right': scrollWidth
            });
        }).resize();
    </script>
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
<div class="container-event">
<?php if (isset($_SESSION['admin'])) : ?>
    <a class="btn green" href="<?php echo URLROOT; ?>/EventsController/create">
        Create
    </a>
<?php endif; ?>
</div>
<?php foreach ($data['events'] as $event) : ?>
    <?php if (isset($_SESSION['admin'])) : ?>
        <a class="btn orange" href="<?php echo URLROOT . "/EventsController/update/" . $event->id ?>">
            Update
        </a>
        <form action="<?php echo URLROOT . "/EventsController/delete/" . $event->id ?>" method="POST">
            <input type="submit" name="delete" value="Delete" class="btn red" onclick="return confirm('Vous êtes sûr ?')">
        </form>
    <?php endif; ?>
    <div class="event" id="event">
        <div class="col-lg-6">
            <div class="event-item wow fadeInUp" data-wow-delay="0.1s">
                <div class="event-img">
                    <img src="img/blog-1.jpg" alt="event">
                </div>
                <div class="event-text">
                    <h2>
                        <?php echo $event->name; ?>
                    </h2>
                    <div class="event-meta">
                        <p><i class="far fa-calendar-alt"></i><?php echo 'Date Debut:' . date('F j h:m', strtotime($event->startDate)); ?></p>
                        <p><i class="far fa-calendar-alt"></i><?php echo 'Date Fin:' . date('F j h:m', strtotime($event->endDate)); ?></p>

                    </div>
                    <p>
                        <?php echo $event->description; ?>
                    </p>
                    <a class="btn" href="">Lire plus! <i class="fa fa-angle-right"></i></a>
                </div>
            </div>
        </div>
    </div>
<?php endforeach; ?>
<!-- Footer Start -->
<div class="footer wow fadeIn" data-wow-delay="0.3s">
    <div class="container-fluid">
        <div class="container">
            <div class="footer-info">
                <h2>ANOMIC <span style="color: #e29f01;">ELEMENTS</span></h2>
                <div class="footer-menu">
                    <p>+012 345 67890</p>
                    <p>info@example.com</p>
                </div>
                <div class="footer-social">
                    <a href=""><i class="fab fa-twitter"></i></a>
                    <a href=""><i class="fab fa-facebook-f"></i></a>
                    <a href=""><i class="fab fa-youtube"></i></a>
                    <a href=""><i class="fab fa-instagram"></i></a>
                    <a href=""><i class="fab fa-linkedin-in"></i></a>
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