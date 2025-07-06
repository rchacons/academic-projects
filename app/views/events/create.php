<?php
require APPROOT . '/views/layout/head.php';
?>

<div class="navbar dark">
    <?php
    require APPROOT . '/views/layout/navigation.php';
    ?>
</div>

<div class="container-event center">
    <h1>Ajoutez un nouvel événement</h1>
    <form action="<?php echo URLROOT; ?>/EventsController/validateInput" method="POST" enctype="multipart/form-data">
        <div class="form-item">
            <!-- start of input tag -->
            <input type="text" name="name"    
            <?php if(!empty($data['name'])) :?> 
                                     value='<?php echo $data['name'];?>'
            <?php else:?>
                                                        placeholder = "Title..."       
            <?php endif;?> >  <!--End of input tag -->


            <span class="invalidFeedback">
                <?php echo $data['nameError']; ?>
            </span>
        </div>

        <div class="form-item">
            <p>Photo de couverture:</p>
            <input type="file" name="coverImage" id="coverImage">
        </div>

        <div class="form-item">
            <p>Date de début:</p>
            <input type="datetime-local" name="startDate"
            <?php if(!empty($data['startDate'])) :?> 
                                value='<?php echo $data['startDate'];?>'
            <?php endif;?>>

            <span class="invalidFeedback">
                <?php echo $data['startDateError']; ?>
            </span>
        </div>
        <div class="form-item">
            <p>Date de fin:</p>
            <input type="datetime-local" name="endDate"
            <?php if(!empty($data['endDate'])) :?> 
                                value='<?php echo $data['endDate'];?>'
            <?php endif;?> > 


            <span class="invalidFeedback">
                <?php echo $data['endDateError']; ?>
            </span>
        </div>
        
        <div class="form-item">
            <!-- start of input tag -->
            <input type="number" name="capacity"    
            <?php if(!empty($data['capacity'])) :?> 
                                     value='<?php echo $data['capacity'];?>'
            <?php else:?>
                                                        placeholder = "Capacité..."       
            <?php endif;?> >  <!--End of input tag -->

        </div>

        <div class="form-item">
            <p>Ajoutez des photos:</p>
            <input id="pictures" type="file" name="files[]" multiple="multiple">
            <span class="invalidFeedback">
                <ul>
                    <?php
                    if(!empty($data['pictureError'])){
                        foreach ($data['pictureError'] as $error) {
                            echo "<li>" . $error . "</li>";
                        }
                    }
                    ?>
                </ul>
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