<?php
require APPROOT . '/views/layout/head.php';
?>

<div class="navbar dark">
    <?php
    require APPROOT . '/views/layout/navigation.php';
    ?>
</div>

<div class="container-event">
    <?php if (isset($_SESSION['admin'])) : ?>
        <a class="btn green" href="<?php echo URLROOT; ?>/ArtistController/create">
            Create
        </a>
    <?php endif; ?>


    <?php foreach ($data['artists'] as $artist) : ?>
        <div class="container-item">
            <?php if (isset($_SESSION['admin'])) : ?>
                <a class="btn orange" href="<?php echo URLROOT . "/ArtistController/update/" . $artist->id ?>">
                    Update
                </a>
                <form action="<?php echo URLROOT . "/ArtistController/delete/" . $artist->id ?>" method="POST">
                    <input type="submit" name="delete" value="Delete" class="btn red" onclick="return confirm('Vous êtes sûr ?')">
                </form>
            <?php endif; ?>
            <div class="container-img">
                <img src="<?php  echo URLROOT.'/'.$artist->picture ?>" alt="imgArtist" width="100" height="100"> 
            </div>
            <h2>
                <?php echo $artist->name; ?>
            </h2>
            <h3>
                <?php echo 'Contact :'.$artist->contact; ?>
            </h3>
            <h3>
                <?php echo 'Site de diffusion :'.$artist->diffusion; ?>
            </h3>
            <h3>
                <p>Categorie: <?php echo $artist->categoryId->name; ?> </p>
                <p>Style: <?php echo $artist->categoryId->type; ?> </p>
            </h3>
        

            <p>
                <?php echo $artist->description; ?>
            </p>
        </div>
    <?php endforeach; ?>
</div>