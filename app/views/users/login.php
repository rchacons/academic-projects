<?php
   require APPROOT . '/views/layout/head.php';
?>

<div class="navbar">
    <?php
       require APPROOT . '/views/layout/navigation.php';
       
    ?>
</div>
<div class="container-login">
    <div class="wrapper-login">
        <h2>S'Identifier</h2>

        <form action="<?php echo URLROOT; ?>/UsersController/login" method ="POST">
            <input type="text" placeholder="Username *" name="username">
            <span class="invalidFeedback">
                <?php echo $data['usernameError']; ?>
            </span>

            <input type="password" placeholder="Password *" name="password">
            <span class="invalidFeedback">
                <?php echo $data['passwordError']; ?>
            </span>

            <button id="submit" type="submit" value="submit">S'identifier</button>

            <p class="options">Nouveau fan? <a href="<?php echo URLROOT; ?>/UsersController/register">Creez un compte !</a></p>
        </form>
    </div>
</div>