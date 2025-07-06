<nav class="top-nav">
    <ul>
        <li>
            <?php if(isset($_SESSION['admin'])) : ?>
                <span>Admin</span> 
            <?php endif; ?>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/index">Home </a>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/PagesController/about">À propos </a>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/ArtistController/index">Artistes </a>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/EventsController/index">Evenements </a>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/galerie">Galerie </a>
        </li>
        <li>
            <a href="<?php echo URLROOT; ?>/PagesController/contact">Contact </a>
        </li>
        <li class="btn-login">
            <?php if (isset($_SESSION['user_id']) || isset($_SESSION['admin'])) : ?>
                <a href="<?php echo URLROOT; ?>/UsersController/logout"> Se déconnecter </a>
            <?php else : ?>
                <a href="<?php echo URLROOT; ?>/UsersController/login"> S'identifier </a>
            <?php endif; ?>
        </li>
    </ul>

</nav>
