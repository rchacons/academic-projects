<?php
if(isset($_POST['jsonImg'])){
    $image = json_decode($_POST['jsonImg']);
    $artistId = $_POST['artistId'];
    

    if(!empty($image)){
        echo '<div class="imgContainer">';
            echo '<div>';
                echo '<img src="'.URLROOT.'/'.$image->name.'" alt="coverImgNull" width="100" height="100"> ';
            echo '</div>';
    
            echo '<div class="imgButton">';
                echo '<button id="deleteImgBtn" type="button" onclick="removeArtistImage('.$image->id.','.$artistId.')" >Retirer</button>';

                echo '<table class="centerTable" id="artistImageTb">';
                echo '<tr>';
                    //Ici on ajoute le tableau pour selectionner la nouvelle image de couverture
                echo '</tr>';
            echo '</table>';
            echo '</div>';
        echo '</div>';
    }
    else{
        echo '<p>Ajoutez une photo !</p>';
        echo '<input type="file" name="artistImage" id="artistImage" >';
    }
    
}

?>