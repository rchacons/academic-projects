window.onerror = function(error) {
    // do something clever here
    alert(error); // do NOT do this for real!
};

function getXhr() {
    var xhr = null;
    if (window.XMLHttpRequest) // Firefox et autres
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject) { // Internet Explorer 
        try {
            xhr = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
    } else { // XMLHttpRequest non supporté par le navigateur 
        alert("Votre navigateur ne supporte pas les objets XMLHTTPRequest...");
        xhr = false;
    }
    return xhr;
}

function deleteAndLoad(imageId, eventId, jsonImg) {
    deleteImg(imageId, eventId);
   loadNewJsonImg(eventId);
   // alert(newJsonImg);
    //loadTable(eventId, loadNewJsonImg(eventId));
}

//Fonction qui permet d'enlever l'image de la bdd et du repertoire, et recharge le tableaux 
function deleteImg(idImage, eventId) {
    var answer = confirm('Vous en etes  sur ? ');

    if (answer) {

        var xhr = getXhr();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                document.getElementById('imgMessage').innerHTML = "<i> Image Supprimée </i>";

            }
        }

        xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/deleteImage/' + idImage, true);
        xhr.send(null);
    }
}


//Fonction qui permet d'enlever l'image de couverture de la bdd et du repertoire, et recharge le tableaux 
function removeCoverImage(idImage, eventId) {
    var answer = confirm('Vous en etes  sur ? ');
    if (answer) {
        $.post('<?php echo URLROOT; ?>/EventsController/removeCoverImage/' + eventId, function() {

            $('#imgMessage').html("<i> Image Retirée </i>");

            //On cree un timeout pour recharger le tableau
            setTimeout(function() {
                $("#coverImgTable").load('<?php echo URLROOT; ?>/EventsController/update/' + eventId + ' #coverImgTable');
            }, 100);
        });
    }
}

//Fonction qui permet d'enlever l'image de couverture de la bdd et du repertoire, et recharge le tableaux 
function changeCoverImage(eventId) {

    document.getElementById("imgCoverTable").style.display = "";
    //On cree un timeout pour recharger le tableau

}

function loadTable(eventId, imgJson) {

    // alert((imgJson));
    // alert(eventId);

    var req = getXhr();

    req.onreadystatechange = function() {
        if (req.readyState == 4 && req.status == 200) {
            var tableDisplay = document.getElementById('imgTable');
            tableDisplay.innerHTML = req.responseText;
        }
    }

    req.open("POST", '<?php echo URLROOT ?>/EventsController/loadView/test2', true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    req.send("idEvent=" + eventId + "&jsonArray=" + JSON.stringify(imgJson));

}

//Renvoie le nouveau JSON des images correspondantes a l'event apres un changement
function loadNewJsonImg(idEvent) {
    var xhr = getXhr();
    var jsonImg = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            jsonImg = xhr.responseText;
            loadTable(idEvent,JSON.parse(jsonImg));
        }
    }

    xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/getJsonImageByEventId/' + idEvent, true);
    xhr.send(null);
}