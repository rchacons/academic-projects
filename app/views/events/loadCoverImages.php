
<?php
if (isset($_POST['jsonArray']) && isset($_POST['idEvent'])) {
    $images = json_decode($_POST['jsonArray']);
    $eventId = $_POST['idEvent'];
    
    foreach ($images as $image) {
        echo '<td>';
            echo '<div class="imgContainer">';
                echo '<div>';
                    echo '<img src="' . URLROOT . '/' . $image->name . '" alt ="img" width="100" height="100">';
                echo '</div>';
                echo '<div class= "imgButton">';
                    echo "<button id='chooseCoverImage' type='button' onclick='chooseAndLoad(".$image->id.",".$eventId.",".json_encode($images).")'>Choisir</button>";
                echo '</div>';
            echo '</div>';
        echo '</td>';
    }
}
?>
