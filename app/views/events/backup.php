<p>Modifiez l'image de couverture: </p>
<div id="loadCoverImageDiv"></div>
<!-- Ca va en dessus  -->
<?php if (!empty($data['coverImage'])) : ?>

    <div class="imgContainer">
        <div>
            <img id="imgCover" src='<?php echo URLROOT . '/' . $data['coverImage']->name; ?>' alt="img" width="100" height="100">
        </div>
        <div class="imgButton">
            <button id="deleteImageBtn" type="button" onclick="removeCoverImage('<?php echo $data['coverImage']->id; ?>','<?php echo $data['event']->id ?>')">Retirer</button>
            <button id="modifyImageBtn" type="button">Changer</button>
        </div>
    </div>

<?php else : ?>
    <input type="file" name="coverImage" id="coverImage">
    <p><button id="modifyImageBtn" type="button" onclick='changeCoverImage(<?php echo $eventId ?>,<?php echo $imgJson ?>)'>Ou en selectionner une ! </button></p>
    <table class="centerTable" id="coverImageTb">
        <tr>
            <!-- Ici on ajoute le tableau pour selectionner la nouvelle image de couverture-->
        </tr>
    </table>
<?php endif; ?>
<p id="coverImgMessage"></p>


<script>
    function deleteAndLoad(imageId, eventId, jsonImg) {
        deleteImg(imageId, eventId);
        loadNewJsonImg(eventId, function(response) {
            loadTable(eventId, response);
            //document.getElementById('imgCover').src = '<?php echo URLROOT ?>/' + response['name'];
        });

    }



    //Renvoie le nouveau JSON des images correspondantes a l'event apres un changement
    function loadNewJsonImg(idEvent, callback) {
        var xhr = getXhr();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                callback(xhr.responseText);
            }
        }

        xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/getJsonImageByEventId/' + idEvent, true);
        xhr.send(null);
    }
</script>