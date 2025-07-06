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
        //Fonction qui permet d'enlever l'image de couverture de la bdd et du repertoire, et recharge le tableaux 
        function removeArtistImage(idImage, artistId) {
            var answer = confirm('Vous en etes  sur ? ');
            if (answer) {

                var req = getXhr();
                req.onreadystatechange = function() {
                    if (req.readyState == 4 && req.status == 200) {
                        document.getElementById('artistImgMessage').innerHTML = "<i> Image Retiré </i>";
                        loadNewArtistImg(artistId, function(jsonArtistImage) {
                            loadArtistImg(artistId, JSON.parse(jsonArtistImage));

                        });

                    }
                }
                req.open("GET", '<?php echo URLROOT; ?>/ArtistController/removeImage/' + artistId, true);
                req.send(null);

            }
        }

        //CA MARCHE
        //Fonction qui permet de charger l'image de l'Artist
        function loadArtistImg(artistId, artistImgJson) {

            var req = getXhr();

            req.onreadystatechange = function() {
                if (req.readyState == 4 && req.status == 200) {
                    artistImg = document.getElementById('loadArtistImageDiv');
                    artistImg.innerHTML = req.responseText;
                }
            }

            req.open("POST", '<?php echo URLROOT ?>/ArtistController/loadView/loadArtistImg', true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send("artistId=" + artistId + "&jsonImg=" + JSON.stringify(artistImgJson));
        }

        //CA MARCHE
        //Fonction qui permet d'envoyer l'image de l'artiste
        function loadNewArtistImg(idArtist, callback) {
            var xhr = getXhr();
            var jsonImg = '';
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    callback(xhr.responseText);

                }
            }

            xhr.open("GET", '<?php echo URLROOT; ?>/ImagesController/getJsonArtistById/' + idArtist, true);
            xhr.send(null);

        }



        /**
         * Methode qui sera appelee sur le click du button et affichera les type de categorie
         */
        function go() {

            var xhr = getXhr();

            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    leselect = xhr.responseText;
                    // On se sert de innerHTML pour rajouter les options a la liste
                    document.getElementById('typeCat').innerHTML = leselect;
                }
            }

            sel = document.getElementById('category');
            selectedCategory = sel.options[sel.selectedIndex].value;

            xhr.open("GET", '<?php echo URLROOT ?>/ArtistController/getCategoryType/' + selectedCategory, true);
            xhr.send(null);

        }


        /**
         * Methode qui affichera les categories 
         */
        function loadCatSelector() {

            var xhr = getXhr();
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    categories = xhr.responseText;
                    document.getElementById('category').innerHTML = categories;

                }
            }

            xhr.open("GET", '<?php echo URLROOT ?>/ArtistController/getCategoriesName', true);
            xhr.send(null);

        }
    </script>


</head>

<body>


    <div class="navbar dark">
        <?php
        require APPROOT . '/views/layout/navigation.php';
        ?>
    </div>

    <?php
    //Avant de l'envoyer par Ajax on va l'encoder
    $artistImgJson = json_encode($data['picture']);
    $artistId = $data['artist']->id;

    ?>
    <script>
        loadArtistImg(<?php echo $artistId ?>, <?php echo $artistImgJson ?>);
        loadCatSelector();
    </script>

    <div class="container center">

        <h1>
            Update Artist

            <form name="updateArtist" action="<?php echo URLROOT; ?>/ArtistController/update/<?php echo $data['artist']->id ?>" method="POST" enctype="multipart/form-data">
                <div class="form-item">

                    <!-- MODIFICATION DU NOM -->
                    <input type="text" name="name" value="<?php echo $data['artist']->name ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['nameError']; ?>
                    </span>
                </div>

                <!-- MODIFICATION DE L'IMAGE DE L'ARTISTE -->
                <div class="form-item">
                    <p>Modifiez l'image de l'artiste </p>
                    <div id="loadArtistImageDiv">
                        <!-- Ici on charge l'image -->

                    </div>
                    <p id="artistImgMessage"></p>

                </div>

                <div class="form-item">

                    <!-- MODIFICATION DE CATEGORIE -->
                    <p>Modifiez les categories:</p>
                    <input type="text" name="categoryDisplay" value="<?php echo $data['artist']->categoryId->name; ?>">
                    <input type="text" name="typeCatDisplay" value="<?php echo $data['artist']->categoryId->type; ?>">

                    <div class="form-item">

                        <label>Categorie:</label>
                        <select id="category" name="category" onchange='go()'>
                            <!--Ici on affiche les differentes categories -->
                        </select>

                        <label>Type:</label>
                        <div id='cat' style="display:inline">
                            <select name='typeCat' id='typeCat'>
                                <!--Ici on affiche les differentes types selon la categorie -->
                            </select>
                        </div>


                        <span class="invalidFeedback">
                            <?php echo $data['categoryError']; ?>
                        </span>
                    </div>

                    <span class="invalidFeedback">
                        <?php echo $data['categoryError']; ?>
                    </span>
                </div>


                <!-- MODIFICATION DE CONTACT -->
                <div class="form-item">
                    <input type="text" name="contact" value="<?php echo $data['artist']->contact ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['capacityError']; ?>
                    </span>
                </div>

                 <!-- MODIFICATION DU SITE DE DIFFUSION-->
                <div class="form-item">

                    <!-- MODIFICATION DU NOM -->
                    <input type="text" name="diffusion" value="<?php echo $data['artist']->diffusion ?>">

                    <span class="invalidFeedback">
                        <?php echo $data['diffusionError']; ?>
                    </span>
                </div>


                <!-- MODIFICATION DE DESCRIPTION -->
                <div class="form-item">
                    <textarea name="description" placeholder="Enter your post..."><?php echo $data['artist']->description ?></textarea>

                    <span class="invalidFeedback">
                        <?php echo $data['descriptionError']; ?>
                    </span>
                </div>
                <button class="btn cancel" name="cancel" type="button" onclick="window.location.replace('<?php echo URLROOT . '/ArtistController' ?>')">Cancel</button>
                <button class="btn green" name="submit" type="submit">Submit</button>
            </form>
    </div>