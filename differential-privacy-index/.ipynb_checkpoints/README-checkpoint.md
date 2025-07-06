# sbd-Projet-PinedRQ

## Q09 : Parameters

Bins ->  Nombre de noeuds qu'il y a dans l'histogramme. Plus de noeuds, plus l'histogramme du noeud sera perturbé puisqu'il y aura plus de bruit.

Degree -> Definie la quantite de bruit qui est ajouté. PLus élevée, plus il y aura de bruit ajouté, mais la précision des résultats sera reduite.

Epsilon -> Parametre de confidentialité qui contrôle le bruit de LaPlace ajouté aux histogrammes. Plus il est grand, plus il y a de bruit et plus il est confidentiel.

Proba dp -> Probabilite de repondre au critere de la differential privacy. Plus  elle est petite, moins on a de chance d'y repondre. Par contre une proba elevée veut dire une complexité plus haute donc il faut trouver un equilibre.

Frac -> Fraction de domaine de definition que la requete interroge.

Recall -> Le nb de vrais positif divisé par le nb de vrais positif existants dans la "ground truth". En gros, t'as trouve tout ce que tu voulais trouver.

Precision -> nb de vrais positif divise par le nb total des donnees recues.



## Q10 : why the recall is perfect and the precision extremely low.

Le recall est parfait parce qu'il n'y a pas encore de bruit, donc le nb de vrais positif est le nb de vrais positif trouves dans le ground truth.
La precision est basse au debut puisque la fraction de domaine interrogé n'est qu'une partie de la totalité. C'est pour cela que, du fait qu'il y a pas du bruit, la precision est à 1 quand toutes les données sont interrogés.


 Il y a pas encore d ebruit donc c'est pour ca qu'a la fin est a 1.