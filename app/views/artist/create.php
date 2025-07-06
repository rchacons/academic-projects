<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-control" content="no-cache``">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo SITENAME ?></title>
    <link rel="stylesheet" href="<?php echo URLROOT ?>/public/css/styleP.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400&display=swap" rel="stylesheet">

    <script type="text/javascript">

            function getXhr(){
                var xhr = null; 
				if(window.XMLHttpRequest) // Firefox et autres
				   xhr = new XMLHttpRequest(); 
				else if(window.ActiveXObject){ // Internet Explorer 
				   try {
			                xhr = new ActiveXObject("Msxml2.XMLHTTP");
			            } catch (e) {
			                xhr = new ActiveXObject("Microsoft.XMLHTTP");
			            }
				}
				else { // XMLHttpRequest non supporté par le navigateur 
				   alert("Votre navigateur ne supporte pas les objets XMLHTTPRequest..."); 
				   xhr = false; 
				} 
            return xhr;
			}

            /**
             * Methode qui affichera les categories 
             */
            function loadCatSelector(){
                
                var xhr = getXhr();
                xhr.onreadystatechange = function(){
                    if(xhr.readyState == 4 && xhr.status == 200){
						categories = xhr.responseText;
                        document.getElementById('category').innerHTML = categories;
                     
                    }
                }

                xhr.open("GET",'<?php echo URLROOT ?>/ArtistController/getCategoriesName',true);
                xhr.send(null);

            }



            /**
             * Methode qui sera appelee sur le click du button et affichera les type de categorie
             */
            function go(){
                
                var xhr = getXhr();

                xhr.onreadystatechange = function(){
                    if(xhr.readyState == 4 && xhr.status == 200){
						leselect = xhr.responseText;
						// On se sert de innerHTML pour rajouter les options a la liste
						document.getElementById('typeCat').innerHTML = leselect;
                    }
                }

                sel = document.getElementById('category');
                selectedCategory = sel.options[sel.selectedIndex].value;
                
                xhr.open("GET",'<?php echo URLROOT ?>/ArtistController/getCategoryType/'+selectedCategory,true);
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

<script>
    loadCatSelector();
</script>

<div class="container-event center">
    <h1>Ajoutez un nouvel Artiste !</h1>
    <form action="<?php echo URLROOT; ?>/ArtistController/validateInput" method="POST" enctype="multipart/form-data">
        <div class="form-item">
            <!-- start of input tag -->
            <input type="text" name="name"    
            <?php if(!empty($data['name'])) :?> 
                                     value='<?php echo $data['name'];?>'
            <?php else:?>
                                                        placeholder = "Nom de l'artiste..."       
            <?php endif;?> >  <!--End of input tag -->


            <span class="invalidFeedback">
                <?php echo $data['nameError']; ?>
            </span>
        </div>

        <div class="form-item">
            <p>Image de l'artiste: </p>
            <input type="file" name="artistImage" id="artistImage">
        </div>

        <div class="form-item">
            <input type="text" name="contact"   
            <?php if(!empty($data['contact'])) :?> 
                                     value='<?php echo $data['contact'];?>'
            <?php else:?>
                                                        placeholder = "Contact...."       
            <?php endif;?> >  <!--End of input tag -->

            <span class="invalidFeedback">
                <?php echo $data['contactError']; ?>
            </span>
        </div>

        <div class="form-item">
            <!-- start of input tag -->
            <input type="text" name="diffusion"    
            <?php if(!empty($data['diffusion'])) :?> 
                                     value='<?php echo $data['diffusion'];?>'
            <?php else:?>
                                                        placeholder = "Site de diffusion..."       
            <?php endif;?> >  <!--End of input tag -->


            <span class="invalidFeedback">
                <?php echo $data['diffusionError']; ?>
            </span>
        </div>

     
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
    

    
        <div class="form-item">
            <textarea name="description" placeholder="Description..."><?php echo (!empty($data['description'])) ? ($data['description']) : '';?></textarea>


            <span class="invalidFeedback">
                <?php echo $data['descriptionError']; ?>
            </span>
        </div>
        <button class="btn cancel" name="cancel" type="button" onclick="window.location.replace('<?php echo URLROOT.'/EventsController' ?>')">Cancel</button>
        <button class="btn green" name="submit" type="submit">Submit</button>
    </form>
</div>