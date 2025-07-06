<!DOCTYPE html>
<html lang="en" style="overflow: hidden;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        ANOMIC ELEMENTS
    </title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,900&display=swap" rel="stylesheet">
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="<?php echo URLROOT ?>/public/css/Accueil.css">
</head>

<body>
    <div class="contain" style="background-image: url(<?php echo URLROOT ?>/img/Accueil/banner.jpg);">
        <div id="topnav" class="navbar">
            <a href="#" class="logo">
                ANOMIC <span style="color: #e29f01;">ELEMENTS</span>
            </a>
            <div class="navbar-right menu">
                <a href="#" class="active">
                    Home
                </a>
                <a href="<?php echo URLROOT; ?>/ArtistController/index">Artistes </a>
                <a href="<?php echo URLROOT; ?>/EventsController/index">Evenements </a>
                <a href="<?php echo URLROOT; ?>/galerie">Galerie </a>
                <a href="<?php echo URLROOT; ?>/PagesController/contact">Contact </a>
                <dev class="btn-login">
                    <?php if (isset($_SESSION['user_id']) || isset($_SESSION['admin'])) : ?>
                        <a href="<?php echo URLROOT; ?>/UsersController/logout"> Se déconnecter </a>
                    <?php else : ?>
                        <a href="<?php echo URLROOT; ?>/UsersController/login"> S'identifier </a>
                    <?php endif; ?>
                </dev>
            </div>
            <div class="navbar-right">
                <a href="javascript:void(0);" class="icon" onclick="showMenu()">
                    <i class="fa fa-bars"></i>
                </a>
            </div>
        </div>

        <div class="slide-control">
            <i class='bx bxs-right-arrow'></i>
        </div>
        <div class="overlay"></div>
        <div class="col-5" style="z-index: 97;">
            <div class="info">
                <!-- info 1 -->




                <div class="event-info">
                    <h1>
                        ANO<span style="color: #e29f01;">MIC</span>
                    </h1>
                    <h1>
                        <span style="color: #e29f01;">DIS</span>TORSION
                    </h1>
                    <span>
                        ADN X ANOMIC ELEMENTS
                    </span>
                    <p>
                        ADN et Anomic Elements s'associent pour la troisième fois afin de vous proposer un événement
                        d’exception au Ninkasi Kao. Anomic Distorsions, c'est le souhait de mettre en avant une face plus underground de la psytrance à Lyon. A l'occasion deux artistes internationaux viennent faire leur première à Lyon !
                    </p>
                    <a href="<?php echo URLROOT; ?>/EventsController/index"><button>
                            Decouvrir les evenements
                        </button> </a>

                </div>
                <!-- end info 1 -->
                <!-- info 2 -->
                <div class="event-info">
                    <h1>
                        <span style="color: #e29f01;">Ray</span>zorks
                    </h1>
                    <span>
                        Producer/DJ Psychedelic Groovy Trance at Anomic Elements
                    </span>
                    <p>
                        Après un coup de foudre avec la mu-
                        sique psychédelique et de nombreux fes-
                        tivals, Florien se saisit des platines et tra-
                        vaille des dj sets psytrance allant de 150
                        à 155 BPM. Ses sélections se basent sur
                        des labels tel que Sangoma, Squarelab
                        ou encore Sonic Loom. Rayzork délivre
                        des sets aux atmosphères à la fois lumi-
                        neuse, entrainante et énergique avec une
                        touche finale beaucoup plus ténébreuse,
                        profonde, envoutante et mélancolique
                    </p>
                    <a href="<?php echo URLROOT; ?>/ArtistController/index"><button> Decouvrir les artistes ! </button> </a>
                </div>
                <!-- end info 2 -->
                <!-- info 3 -->
                <div class="event-info">
                    <h1>
                        <span style="color: #e29f01;">PI</span>
                    </h1>
                    <h1>
                        <span style="color: #e29f01;">R</span>2
                    </h1>
                    <span>
                        DJ
                    </span>
                    <p>
                        Pierre, longtemps passionné par la mu-
                        sique a découvert il y a quelques an-
                        nées l’univers de la psytrance. Son inté-
                        rêt croissant pour cet univers l’a mené
                        peu à peu à s’intéresser au DJ set. Tout
                        en restant ouvert aux différentes fa-
                        cettes de ce genre musical, ses sélec-
                        tions s’orientent pour l’instant vers une
                        psytrance dansante, aux sonorités darky
                        voire parfois forest.
                    </p>

                    <a href="<?php echo URLROOT; ?>/ArtistController/index"><button> Decouvrir les artistes ! </button> </a>

                </div>
                <!-- end info 3 -->
                <!-- info 4 -->
                <div class="event-info">
                    <h1>
                        <span style="color: #e29f01;">CONT</span>ACTEZ
                    </h1>
                    <h1>
                        NO<span style="color: #e29f01;">US</span>
                    </h1>
                    <span>
                        Info et Contact
                    </span>
                    <p>
                        Association basée sur la culture Psytrance ayant pour objet l'organisation d’événements musicaux et culturels.
                    </p>
                    <a href="<?php echo URLROOT; ?>/PagesController/contact"><button>
                            Decouvrir plus !
                        </button> </a>
                </div>
                <!-- end info 4 -->
            </div>
        </div>
        <div class="col-7">
            <div class="slider">
                <div class="slide">
                    <div class="img-holder" style="background-image: url(<?php echo URLROOT ?>/public/img/Accueil/anomic-elements.jpg);"></div>
                </div>
                <div class="slide">
                    <div class="img-holder" style="background-image: url(<?php echo URLROOT ?>/public/img/Accueil/rayzorks.jpg)">
                    </div>
                </div>
                <div class="slide">
                    <div class="img-holder" style="background-image: url(<?php echo URLROOT ?>/public/img/Accueil/pir2.png)"></div>
                </div>
                <div class="slide">
                    <div class="img-holder" style="background-image: url(<?php echo URLROOT ?>/public/img/Accueil/contact.jpg)"></div>
                </div>
            </div>
        </div>
    </div>

    <script src="<?php echo URLROOT ?>/public/js/Accueil.js"></script>
</body>

</html>