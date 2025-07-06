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
        <h2>S'inscrire</h2>

        <form action="<?php echo URLROOT; ?>/UsersController/register" method ="POST">
            <input type="text" placeholder="Username *" name="username">
            <span class="invalidFeedback">
                <?php echo $data['usernameError']; ?>
            </span>

            <input type="email" placeholder="Email *" name="email">
            <span class="invalidFeedback">
                <?php echo $data['emailError']; ?>
            </span>

            <input type="password" placeholder="Password *" name="password">
            <span class="invalidFeedback">
                <?php echo $data['passwordError']; ?>
            </span>

            <input type="password" placeholder="Confirm Password *" name="confirmPassword">
            <span class="invalidFeedback">
                <?php echo $data['confirmPasswordError']; ?>
            </span>

            <button id="submit" type="submit" value="submit">S'inscrire</button>
        </form>
    </div>
</div>