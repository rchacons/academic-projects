<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-control" content="no-cache``">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo SITENAME ?></title>
    <link rel="stylesheet" href="<?php echo URLROOT ?>/public/css/styleP.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lato:wght@100;300;400&display=swap" rel="stylesheet">


    <script language="JavaScript">
        window.onerror = function(error) {
            // do something clever here
            alert(error); // do NOT do this for real!
        };

        //CA MARCHE
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

        //CA MARCHE
        function deleteAndLoad(imageId, eventId, jsonImg) {
            deleteImg(imageId, eventId);

            //l'idee ici est recuperer le nouveau JSON et appeller le loadtable encore une fois
            loadNewJsonImg(eventId, function(response) {
                loadTable(eventId, JSON.parse(response));
            });
        }


        function chooseAndLoad(imageId, eventId, jsonImg) {
            assignCoverImage(imageId, eventId);

        }

        //CA MARCHE
        //Fonction qui permet d'enlever l'image de la bdd et du repertoire
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

        
        //CA MARCHE
        //Fonction qui permet d'enlever l'image de couverture de la bdd et du repertoire, et recharge le tableaux 
        function removeCoverImage(idImage, eventId) {
            var answer = confirm('Vous en etes  sur ? ');
            if (answer) {

                var req = getXhr();
                req.onreadystatechange = function() {
                    if (req.readyState == 4 && req.status == 200) {
                        document.getElementById('coverImgMessage').innerHTML = "<i> Image Retiré </i>";
                        loadNewCoverImg(eventId, function(jsonCoverImage) {
                            loadNewJsonImg(eventId,function(jsonImages){
                                loadCoverImg(eventId, JSON.parse(jsonCoverImage),JSON.parse(jsonImages))
                            });
                        });

                    }
                }
                req.open("GET", '<?php echo URLROOT; ?>/EventsController/removeCoverImage/' + eventId, true);
                req.send(null);

            }
        }


        //CA MARCHE
        //Fonction qui permet d'afficher les possibilites des image de couverture de la bdd et du repertoire 
        function changeCoverImage(eventId, imgJson) {
            var req = getXhr();
            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {
                    var tableDisplay = document.getElementById('coverImageTb');
                    tableDisplay.innerHTML = req.responseText;
                }
            }
            req.open("POST", '<?php echo URLROOT ?>/EventsController/loadView/loadCoverImages', true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send("idEvent=" + eventId + "&jsonArray=" + JSON.stringify(imgJson));
        }


        //CA MARCHEE
        //Fonction qui assigne une image de couverture a un evenement
        function assignCoverImage(imageId, eventId) {

            var req = getXhr();
            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {
                    document.getElementById('coverImgMessage').innerHTML = "<i> Image Modifie avec succes ! </i>";
                    loadNewCoverImg(eventId, function(jsonCoverImage) {
                            loadNewJsonImg(eventId,function(jsonImages){
                                loadCoverImg(eventId, JSON.parse(jsonCoverImage),JSON.parse(jsonImages))
                            });
                        });
                }
            }
            req.open("GET", '<?php echo URLROOT ?>/EventsController/assignCoverImage/' + eventId + '/' + imageId, true);
            req.send(null);
        }


        //CA MARCHE
        //Fonction qui permet de charger la table des images a modifier
        function loadTable(eventId, imgJson) {

            // alert((imgJson));
            // alert(eventId);

            var req = getXhr();

            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {

                    tableDisplay = document.getElementById('imgTable');

                    tableDisplay.innerHTML = req.responseText;
                }
            }

            req.open("POST", '<?php echo URLROOT ?>/EventsController/loadView/loadImages', true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send("idEvent=" + eventId + "&jsonArray=" + JSON.stringify(imgJson));

        }

        //CA MARCHE
        //Fonction qui permet de charger l'image de couverture de l'evenement
        function loadCoverImg(eventId, coverImgJson, imagesJSON) {

            var req = getXhr();

            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {
                    coverImg = document.getElementById('loadCoverImageDiv');
                    coverImg.innerHTML = req.responseText;
                }
            }

            req.open("POST", '<?php echo URLROOT ?>/EventsController/loadView/loadCoverImage', true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send("eventId=" + eventId + "&jsonImg=" + JSON.stringify(coverImgJson) + "&imagesJSON=" + JSON.stringify(imagesJSON));
        }

        //CA MARCHE
        //Fonction qui permet d'envoyer l'image de couverture
        function loadNewCoverImg(idEvent, callback) {
            var xhr = getXhr();
            var jsonImg = '';
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    callback(xhr.responseText);

                }
            }

            xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/getJsonCoverImgByEventId/' + idEvent, true);
            xhr.send(null);

        }

        //CA MARCHE
        //Renvoie le nouveau JSON des images correspondantes a l'event apres un changement
        function loadNewJsonImg(idEvent, callback) {
            var xhr = getXhr();
            var jsonImg = '';
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    callback(xhr.responseText);
            
                }
            }

            xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/getJsonImageByEventId/' + idEvent, true);
            xhr.send(null);
        }
    </script>


</head>

<body>


    <?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);
    ?>

    <div class="navbar dark">
        <?php
        require APPROOT . '/views/layout/navigation.php';
        ?>
    </div>

    <?php
    //Avant de l'envoyer par Ajax on va l'encoder
    $coverImgJson = json_encode($data['coverImage']);
    $imgJson = json_encode($data['images']);
    $eventId = $data['event']->id;

    ?>
    <script>
        loadCoverImg(<?php echo $eventId ?>, <?php echo $coverImgJson ?>, <?php echo $imgJson ?>);
        loadTable(<?php echo $eventId ?>, <?php echo $imgJson; ?>);
    </script>

    <div class="container center">

        <h1>
            Update Event

            <form name="updateEvent" action="<?php echo URLROOT; ?>/EventsController/update/<?php echo $data['event']->id ?>" method="POST" enctype="multipart/form-data">
                <div class="form-item">

                    <!-- MODIFICATION DU NOM -->
                    <input type="text" name="name" value="<?php echo $data['event']->name ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['nameError']; ?>
                    </span>
                </div>

                <!-- MODIFICATION DE L'IMAGE DE COUVERTURE -->
                <div class="form-item">
                    <p>Modifiez l'image de couverture: </p>
                    <div id="loadCoverImageDiv">
                        <!-- Ici on charge l'image -->
                    </div>

                </div>

                <!-- MODIFICATION DE DATE DE DEBUT -->
                <div class="form-item">
                    <input type="datetime-local" name="startDate" value="<?php echo $data['event']->startDate ?>">


                    <span class="invalidFeedback">
                        <?php echo $data['startDateError']; ?>
                    </span>
                </div>

                <!-- MODIFICATION DE DATE DE FIN -->
                <div class="form-item">
                    <input type="datetime-local" name="endDate" value="<?php echo $data['event']->endDate ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['endDateError']; ?>
                    </span>
                </div>

                <!-- MODIFICATION DE CAPACITE -->
                <div class="form-item">
                    <input type="text" name="capacity" value="<?php echo $data['event']->capacity ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['capacityError']; ?>
                    </span>
                </div>

                <!-- AJOUTE DES NOUVELLES IMAGES -->
                <div class="form-item">
                    <p>Ajoutez des nouvelles photos:</p>
                    <input type="file" name="files[]" multiple="multiple">
                    <span class="invalidFeedback">
                        <ul>
                            <?php
                            if (!empty($data['pictureError'])) {
                                foreach ($data['pictureError'] as $error) {
                                    echo "<li>" . $error . "</li>";
                                }
                            }
                            ?>
                        </ul>
                    </span>
                </div>

                <!-- MODIFICATION DES IMAGES EXISTANTES -->
                <div class="form-item">
                    <div id="imgTableDiv">
                        <p>Modifiez les images existantes: </p>
                        <table class="centerTable" id="imgTable">
                            <tr>
                                <!-- Ici on affichera la liste des images -->
                            </tr>
                        </table>
                        <p id="imgMessage"></p>
                    </div>

                </div>

                <!-- MODIFICATION DE DESCRIPTION -->
                <div class="form-item">
                    <textarea name="description" placeholder="Enter your post..."><?php echo $data['event']->description ?></textarea>

                    <span class="invalidFeedback">
                        <?php echo $data['descriptionError']; ?>
                    </span>
                </div>
                <button class="btn cancel" name="cancel" type="button" onclick="window.location.replace('<?php echo URLROOT . '/EventsController' ?>')">Cancel</button>
                <button class="btn green" name="submit" type="submit">Submit</button>
            </form>
    </div>