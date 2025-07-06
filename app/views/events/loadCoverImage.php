<?php
if(isset($_POST['jsonImg'])){
    $image = json_decode($_POST['jsonImg']);
    $eventId = $_POST['eventId'];
    $imagesJSON = json_decode($_POST['imagesJSON']);
    

    if(!empty($image)){
        echo '<div class="imgContainer">';
            echo '<div>';
                echo '<img src="'.URLROOT.'/'.$image->name.'" alt="coverImgNull" width="10" height="500"> ';
            echo '</div>';
    
            echo '<div class="imgButton">';
                echo '<button id="deleteImgBtn" type="button" onclick="removeCoverImage('.$image->id.','.$eventId.')" >Retirer</button>';
                echo "<button id='modifyImageBtn' type='button'  onclick='changeCoverImage(".$eventId.",".json_encode($imagesJSON).")'>Changer</button>";
                echo '<table class="centerTable" id="coverImageTb">';
                echo '<tr>';
                    //Ici on ajoute le tableau pour selectionner la nouvelle image de couverture
                echo '</tr>';
            echo '</table>';
            echo '</div>';
        echo '</div>';
    }
    else{
        echo '<input type="file" name="coverImage" id="coverImage" >';
        echo "<p> <button id='modifyImageBtn' type='button' onclick='changeCoverImage(".$eventId.",".json_encode($imagesJSON).")' > Ou en selectionner une ! </button> </p>";
        echo '<table class="centerTable" id="coverImageTb">';
            echo '<tr>';
                //Ici on ajoute le tableau pour selectionner la nouvelle image de couverture
            echo '</tr>';
        echo '</table>';
    }
    echo '<p id="coverImgMessage"></p>';
}

?>



