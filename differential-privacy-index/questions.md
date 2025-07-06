
# Questions from the TP

## Q09 : Inspect the code and identify the parameters values and the goal of the example experiment that is per-formed.
- Bins -> Nombre de noeuds qu'il y a dans l'histogramme. Plus il y a de noeuds, plus l'histogramme du noeud sera perturbé puisqu'il y aura plus de bruit.

- Degree -> Definie la quantité de bruit qui est ajouté. Plus il est élevé, plus il y aura de bruit ajouté, mais la précision des résultats sera reduite.

- Epsilon -> Paramètre de confidentialité qui contrôle le bruit de LaPlace ajouté aux histogrammes. Plus il est grand, plus il y a de bruit et plus il est confidentiel.

- Proba dp -> Probabilité de répondre au critère de la differential privacy. Plus elle est petite, moins on a de chance d'y répondre. Par contre une probabilité elevée veut dire une complexité plus haute donc il faut trouver un équilibre.

- Frac -> Fraction de domaine de definition que la requête interroge.

- Recall -> Le nombre de vrais positif divisé par le nombre de vrai positif existant dans la "ground truth". Si égal à 1, tous les éléments pertinents ont été retournés.

Precision -> nombre de vrai positif divisé par le nombre total des données reçues.



## Q10 : why the recall is perfect and the precision extremely low.

Le recall est parfait parce qu'il n'y a pas encore de bruit, donc le nombre de vrai positif est le nombre de vrai positif trouves dans le ground truth.
La précision est basse au debut puisque la fraction de domaine interrogé n'est qu'une partie de la totalité. C'est pour cela que, du fait qu'il n'y ai aucun du bruit, la précision est à 1 quand toutes les données sont interrogées et augmente plus le `frac` (domaine interrogé) augmente.

# Encryption

## Q11-12 : Inspect pinedrq.py and spot the function in which the secret key and initialization vector must be generated, and the function in which the records must be encrypted. Then generate them.

La fonction dans laquelle il faut générer le vecteur de initialisation et la clé doit être implementée dans la class Keychain. 
Ils sont générés dans la methode __init__.

## Q13 : Encrypt the records based on the AES cipher in AES.MODE_CBC mode (use the API provided by the pycryptodome module). Do not forget to pad(\cdot) the sequence so that its size is a multiple of AES.block_size .

On sait que AES.MODE_CBC fait référence au chiffrement de blocs **Advanced Encryption Standard (AES)** et **CBC (Cipher Block Chaining)**.
Le chiffrement **AES** utilise une clé de longueur fixe pour chiffrer et déchiffrer les données en blocs de 16 bytes.
Le mode d'opération **CBC** ajoute un niveau de sécurité au chiffrement AES en enchaînant les blocs de texte en clair avant le chiffrement. Il nécessite un vecteur d'initialisation (aléatoire et unique pour chaque séquence chiffrée) qui est XORé avec le premier bloc de texte en clair avant d'être chiffré (chaque octet du IV est combiné avec l'octet correspondant du premier bloc de texte en clair en utilisant l'opération XOR).

De meme, en remplissant les derniers blocs avec des données supplémentaires, le **padding** garantit que la taille de la séquence à chiffrer est un multiple entier de la taille de bloc d'AES, ce qui est nécessaire pour l'algorithme de chiffrement.

Tout cela est implementé dans la fonction **encrypt**.

## Q14 : Spot the function in which the encrypted records must be decrypted. Be careful with following the exact reverse path followed by the encryption function.
La fonction decrypt, tout comme la fonction encrypt se trouve dans la classe Keychain. Ces fonctions peuvent être utilisées de la manière suivante :

```python
#Initialisation
message = "Message secret"
keychain = Keychain()

#Encodage du message
encrypted_message = keychain.encrypt(message.encode())

#Décodage du message
decrypted_message = keychain.decrypt(encrypted_message)
```


# Perturbation

## Q15 - 17 : Now spot the function in which the counts of the nodes must be perturbed and compute the perturbation.

La fonction qui doit perturber le count retourné par un noeud doit être `_perturb_histogram` de la classe Index, permettant ainsi de répondre aux critères de la differential privacy.

Pour cette implementation, nous avons calculé le scale factor de la distribution de LaPlace à partir de la formule du cours : 
$$
\frac{1}{\left(\frac{1}{2^i} \times \epsilon_{total}\right)}​
$$

Nous avons decidé d'utiliser cette formule pour mieux répartir le privacy budget entre les différents niveaux de l'arbre plutôt qu'un unique scale factor uniforme.

Donc les étapes que nous avons suivies pour implementer les perturbations sont les suivantes : 
* Nous avons calculé le scale factor de chaque niveau de l'arbre.
* Pour chaque niveau : 
    * On récupère les noeuds de ce niveau.
    * Pour chaque noeud :
        * On génère la perturbation avec le scale factor de ce niveau.
        * On applique le bruit au count du noeud.


## Q18 Perturb the count of each node by adding to it a random variable sampled from the Laplace distribution well parameterized (you can use the API provided by the scipy module).

Pour cela, nous avons changé la géneration du bruit qui utilisait le module numpy : 
```python
noise = np.random.laplace(0,scale)
```
pour la fonction **laplace.rvs** du module SciPy : 
```python 
noise = laplace.rvs(scale=scale)
```
En utilisant donc cette nouvelle fonction, nous avons accès à plus de paramètres et de méthodes statistiques pour mieux exploiter cette distribution.

## Q19 : You can truncate negative values to 0.  Which differential privacy property guarantees the security of this operation ?

Pour cela, il fallait vérifier si la valeur perturbée était négative, et dans le cas où elle l'était, il fallait mettre le count du noeud à 0.

Afin de répondre aux critères de la propriété de la pertubation aléatoire, il est important de mettre les valeurs perturbées négatives à 0. En effet, cela permet dans un premier temps d'avoir un histogramme valide car les enfants ne peuvent avoir un histogramme négatif. Cela permet aussi d'avoir une somme de counts des enfants en cohérence avec le parent.

Cela vérifie le critère de confidentialité `epsilon privacy`, assurant ainsi que l'ajout de bruit ne permet pas à un attaquant de distinguer la réponse obtenue à la réponse qu'il aurait obtenue si les données d'un utilisateur étaient retirées de la base de données. 

Autrement dit, le bruit aléatoire ne modifie pas de manière significative la sortie de l'histogramme lorsqu'il est suffisamment grand.


## Q20 : Spot the function in which the cloud processes a query.

La méthode dans laquelle le cloud traite une requête est la méthode **query** définie dans la classe **Cloud**. 
La méthode prend en entrée une plage de valeurs à rechercher, et appelle la fonction du même nom de la classe **Index**, qui est responsable de trouver les feuilles de l'index qui contiennent des enregistrements se trouvant dans la plage spécifiée.
Ensuite elle retourne simplement les feuilles trouvées, qui contiennent des enregistrements chiffrés, tout cela dans une séquence des buckets.

 ## Q21 : Design and implement a query processing strategy that improves precision (at the cost of the recall). Warning: keep the naive query processing strategy somewhere. It will be useful as a baseline.

Pour ameliorer la precision, nous avons crée une requête qui ne renvoie que les buckets dont les noeuds sont contenues dans l'intervalle spécifié. 
- Tout d'abord nous obtenons toutes les feuilles depuis le noeud root
- Ensuite, pour chaque feuille, nous vérifions si ses limits gauches et droits se trouvent dans l'intervalle de la rêquete.
- Si c'est le cas, nous stockons le bucket lié à cette feuille.
- Finalement nous renvoyons la liste des buckets pour qu'elle soit processé plus tard.

# Stepping Back

## Q22 - 24 : Visualize the impact of your query code on the recall and precision obtained (simply run Step 3 and Step 4 inRunMe.ipynb), explain the variatons, and compare with the naive approach.

Tout d'abord nous remarquons une amelioration significative dans la performance. Le temps d'exécution de la première approche est d'environ 1m30s tandis que le temps d'exécution de l'approche ameliorée est d'environ 50s.

De plus, nous pouvons remarquer une différence significative dans la précision entre l'approche naîve et l'approche améliorée. La précision moyenne est passée de 0,46 dans l'approche naïve à 0,93 dans l'approche améliorée. La valeur minimale de la précision est également beaucoup plus élevée avec l'approche améliorée, ce qui montre que les résultats sont plus stables. 

En outre, le "recall" reste constante à 1 pour les deux approches, ce qui indique que les deux requêtes sont capables d'identifier correctement toutes les données se situant dans les intervalles donnés. Cela est representé dans les courbes.

La méthode query développée pour améliorer les résultats de la requête naïve permet donc une meilleure précision dans le retour des requêtes en retournant des résultats pertinents à plus de 90% avec des performances à l'exécution bien supérieure.