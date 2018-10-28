Alors vous avez décidé de contribué au projet DéfiLecture? Merveilleux! Le DéfiLecture est un beau projet qui incite à la lecture à travers un concours ludique et amicalement compétitif. Vous participez à une belle aventure!

Avant de contribuer, il y a quelques choses à savoir :

# Licence
Le projet DéfiLecture est placé sous licence GPLv3 [!licence GPLv3](https://www.gnu.org/licenses/gpl-3.0.fr.html). En contribuant au projet, vous acceptez de placer vos contributions à ce projet sous la même licence.

# Normes de contribution

Afin d'uniformiser toutes les contributions au code source, veuillez respecter les quelques règles suivantes dans chacun de vos commits :

Les exemples suivants supposent l'utilisation de NetBeans mais tous les IDEs qui se respectent devraient permettre les mêmes configurations.

1. Toujours sauvegarder les fichiers au format UTF-8
   Dans Netbeans, clic-droit sur le projet puis Properties. Dans l'onglet «Général», en bas, assurez-vous que l'encodage indique UTF-8. 
   Si vos commits incluent des différences pour tous les caractères accentués, c'est que vous avez enregistré votre fichier dans un mauvais format (probablement ISO-8859).
   Exemple de mauvais format. Lorsqu'on fait la commande «git diff test.h»:

    ```
	diff --git a/test.h b/test.h
    index 44d771b..8b0c19c 100644
    --- a/test.h
    +++ b/test.h
    @@ -1,4 +1,4 @@
    -//ceci est un test de fusion accentué
    +//ceci est un test de fusion accentu<E9>
    ```
	
2. Le code doit être correctement indenté à l'aide de 4 espaces par niveau d'indentation. Les caractères TAB (0x09) sont à proscrire. Par défaut, Netbeans insère automatiquement 4 espaces lorsqu'on indente une ligne. Si ce n'est pas le cas (s'il insère un seul caractère TAB), allez dans le menu Tool/Options et dans l'onglet Editor, sélectionnez le style «Netbeans». Si ce n'est pas déjà fait, cochez «Expand Tab to spaces» et entrez 4 dans «Indent Size».

3. Si vous travaillez sous Windows, assurez-vous que git traduit bien vos fins de ligne de CRLF en LF (c'est le comportement par défaut). Si `git diff` montre des différences à chaque fin de ligne, c'est qu'il y a un problème.

4. N'incluez pas dans vos commits des fichiers dont les seuls changements sont des «blancs» (espaces et retour de chariot) à moins que ça soit explicitement l'objectif de votre commit. (Par exemple, faire un commit pour corriger un problème d'indentation ou supprimer des espacements verticaux superflus).

5. Lorsque vous créez un nouveau fichier, n'oubliez pas d'inclure en en-tête la notice de license GPLv3.

6. N'utilisez jamais `git add *` pour ajouter des fichiers à vos commits. Vous risquez fort d'inclure des fichiers qui n'ont aucun lien avec votre commit. Utilisez `git add -u` pour mettre à jour tous les fichiers déjà connus de git et encore, seulement si vous êtes certain de ce que vous faites.

7. Avant de faire un commit, assurez-vous que tous les changements que vous vous apprêtez à commiter et ce dans chacun des fichiers, sont corrects et conformes aux normes. Utilisez `git diff` pour réviser vos changements et vous en assurer.

8. Donnez à vos commits des messages utiles décrivant les changements qu'ils apportent et pourquoi vous les faites. N'hésitez pas à y indiquer le numéro du ticket (issue) correspondant s'il y en a un (Ex.: «Corrigé le bug d'affichage dans la page principale #1234» )

9. Lorsque tous vos commits sont faits, avant de les publier sur gitlab (avant de faire un merge request), assurez-vous d'avoir fusionné la branche cible dans votre branche et d'avoir réglé tous les conflits qui pourraient survenir. Ainsi, le responsable des fusions n'aura pas à régler lui-même des conflits sur des fichiers qu'il ne connaît peut-être pas aussi bien que vous. Par exemple, si vous désirez fusionner votre branche «branche_travail» dans master : 

    ```
    //À partir de la branche branche_travail
    git checkout branche_travail
    //Fusionnez la destination pour incorporer les derniers changements que vous n'aviez pas encore.
    git pull origin master
    //S'il y a un conflit, réglez-le : 
    git mergetool fichierEnConflit.cpp
    git commit -m "Réglé les conflit dans fichierEnConflit.cpp"
    //Publiez votre branche
    git push origin branche_travail
    //Maintenant créez votre merge request
    ```

