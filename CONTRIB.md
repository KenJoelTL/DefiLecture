Alors vous avez décidé de contribué au projet DéfiLecture? Merveilleux! Le DéfiLecture est un beau projet qui incite à la lecture à travers un concours ludique et amicalement compétitif. Vous participez à une belle aventure!

Avant de contribuer, il y a quelques choses à savoir :

# Licence
Le projet DéfiLecture est placé sous [licence GPLv3](https://www.gnu.org/licenses/gpl-3.0.fr.html). En contribuant au projet, vous acceptez de placer vos contributions à ce projet sous la même licence.

# Gitlab

Le projet duquel est tiré l'application mise en production lors de l'événement Défi Lecture au Collège de Rosemont est hébergé sur le serveur Gitlab du département des techniques de l'informatique : https://git.dept-info.crosemont.quebec/plafrance/DefiLecture

## Branches

- La branche live est celle présentement mise en production.

- La branche master est la branche qui sera mise en production lors de la prochaine mise à jour.

## Étiquettes

- À chaque nouvelle mise à jour en production, une étiquette portant un numéro de version est appliquée à la branche live.

- Les versions ont la forme M.m.c où :
  - M est le numéro de révision majeure
  - m est le numéro de révision mineure (incrémenté à chaque itération du Défi Lecture)
  - c est un numéro de changement mineure intervenues au cours de l'événement (principalement des corrections de bug)

## Tickets

La plupart des changements devant être apportés au projets sont consignés dans des tickets sur Gitlab. Lorsque vous décidez de travailler sur un de ces tickets, ajoutez un commentaire sous le ticket mentionnant vos intentions. C'est aussi l'endroit tout désigné pour demander ou apporter plus de précisions.

## Contribuer

### Divergences : 
Afin de faciliter la gestion des contributeurs et minimiser le nombre de branches concurrente sur le projet source, toutes les contributions devront être faites à partir d'une divergence (ou bifurcation ou «fork»). Plusieurs développeurs peuvent collaborer sur la même divergence, selon leur mode de fonctionnement personnel.

### Branches : 
Normalement, les modifications apportées au projet devraient être développées sur une branche idoine. Seulement lorsqu'elles sont complétées devraient-elles être fusionnées à la branche principale, master.

### Demande de fusion dans une divergence : 
Lorsque votre travail vous semble digne d'être intégré à la branche master, vous en faites la fusion dans votre divergence. Seul le créateur de la divergence a ce pouvoir. Les autres collaborateurs devraient lui envoyer une «demande de fusion» (merge request), qu'il aura le loisir d'appliquer ou non. Attention, dans gitlab, la branche cible sélectionnée par défaut lors d'une de la création d'une demande de fusion est celle du projet d'origine et non celle de la divergence; assurez-vous de bien sélectionner la branche sur votre divergence.

### Demande de fusion sur le projet d'origine : 
Lorsque votre travail est terminé et qu'il peut être ajouté au projet principal, le créateur de la divergence doit produire une demande de fusion entre la branche «master» de sa divergence et celle du projet d'origine.

# Normes de contribution

Afin d'uniformiser toutes les contributions au code source, veuillez respecter les quelques règles suivantes dans chacun de vos commits :

Les exemples suivants supposent l'utilisation de NetBeans mais tous les IDEs qui se respectent devraient permettre les mêmes configurations.

Les fichiers de cette solution sont soumis à une vérification selon les standards définis par Google pour le langage de programmation *Java*. Pour de plus amples informations, visitez la [documentation officielle](https://google.github.io/styleguide/javaguide.html).

Voici les points récapitulatifs de ce standard :

1. Toujours sauvegarder les fichiers au format UTF-8
   Dans Netbeans, clic-droit sur le projet puis Properties. Dans l'onglet «Général», en bas, assurez-vous que l'encodage indique UTF-8. 
   Si vos commits incluent des différences pour tous les caractères accentués, c'est que vous avez enregistré votre fichier dans un mauvais format (probablement ISO-8859).
   Exemple de mauvais format. Lorsqu'on fait la commande «git diff test.h»:

    ```
	diff --git a/test.java b/test.java
    index 44d771b..8b0c19c 100644
    --- a/test.java
    +++ b/test.java
    @@ -1,4 +1,4 @@
    -//ceci est un test de fusion accentué
    +//ceci est un test de fusion accentu<E9>
    ```
	
2. Le code doit être correctement indenté à l'aide de 2 espaces par niveau d'indentation. Les caractères TAB (0x09) sont à proscrire. Assurez-vous que votre éditeur utilise bien 2 espaces et non le caractère TAB (0x09) et qu'il indente par incrément de 2 espaces.

3. Si vous travaillez sous Windows, assurez-vous que git traduit bien vos fins de ligne de CRLF en LF (c'est le comportement par défaut). Si `git diff` montre des différences à chaque fin de ligne, c'est qu'il y a un problème.

4. N'incluez pas dans vos commits des fichiers dont les seuls changements sont des «blancs» (espaces et retour de chariot) à moins que ça soit explicitement l'objectif de votre commit. (Par exemple, faire un commit pour corriger un problème d'indentation ou supprimer des espacements verticaux superflus).

5. Lorsque vous créez un nouveau fichier, n'oubliez pas d'inclure en en-tête la notice de license GPLv3.

6. N'utilisez jamais `git add *` pour ajouter des fichiers à vos commits. Vous risquez fort d'inclure des fichiers qui n'ont aucun lien avec votre commit. Utilisez `git add -u` pour mettre à jour tous les fichiers déjà connus de git et encore, seulement si vous êtes certain de ce que vous faites. Quoi qu'il en soit...

7. Avant de faire un commit, assurez-vous que tous les changements que vous vous apprêtez à commiter et ce dans chacun des fichiers, sont corrects et conformes aux normes. Utilisez `git diff` pour réviser vos changements et vous en assurer.

8. Donnez à vos commits des messages utiles décrivant les changements qu'ils apportent et pourquoi vous les faites. N'hésitez pas à y indiquer le numéro du ticket (issue) correspondant s'il y en a un (Ex.: «Corrigé le bug d'affichage dans la page principale #1234» )

9. Lorsque tous vos commits sont faits, avant de les publier sur gitlab (avant de faire un merge request), assurez-vous d'avoir fusionné la branche cible dans votre branche et d'avoir réglé tous les conflits qui pourraient survenir. Ainsi, le responsable des fusions n'aura pas à régler lui-même des conflits sur des fichiers qu'il ne connaît peut-être pas aussi bien que vous. Par exemple, si vous désirez fusionner votre branche «branche_travail» dans master : 

    ```
    //À partir de la branche branche_travail
    git checkout branche_travail
    //Fusionnez la destination pour incorporer les derniers changements que vous n'aviez pas encore.
    git pull origin master
    //S'il y a un conflit, réglez-le : 
    git mergetool fichierEnConflit.java
    git commit -m "Réglé les conflit dans fichierEnConflit.java"
    //Publiez votre branche
    git push origin branche_travail
    //Maintenant créez votre merge request
    ```

