# LePetitBot ✨

<a href="https://discord.gg/FXS9zMm" target="_blank">
<img src=https://img.shields.io/badge/discord-7289DA.svg?&style=for-the-badge&logo=discord&logoColor=white alt=discord style="margin-bottom: 5px;"/></a>

LePetitBot est un bot discord utilisant les librairies suivantes :
- [JDA][jda]
- [Jetty][jetty]
- [Spotify -Web-Api-Java][spotify-api]
- [Lavaplayer][lavaplayer]

il permet la lecture de vidéos provenant de Youtube, mais aussi un système de commandes adaptées à la nouvelle API de Discord (/), ainsi qu'un système d'action row
permettant des intéractions utilisateurs plus fluide.

# Comment utiliser le bot ?

Deux API sont nécessaires au bon fonctionnement du bot, d'abord, un token de bot discord, en suite, une clé d'application spotify, (id & secret).
Il faudra ensuite créer un fichier **"credentials.json"** dans le dossier **"resources"**

Exemple de credentials.json :
```json
{
  "spotify": "idApplicationSpotify",
  "spotifySecret": "secretApplicationSpotify",
  "discordToken": "tokenDiscordBlablabla"
}
```
en suite, il faudra modifier les différentes variables intéragissant avec sois un serveur spécifique (guild), sois un channel spécifique.

NOTE : Vous devez, pour la connexion et le refresh du token à l'api de Spotify, vous connecter avec le lien qui sera publié dans la console lors de l'exécution.
Vous avez le choix, utiliser l'ip "localhost" si vous exécuter le bot localement ou avec la variable "nogui" au lancement, vous donnant une URL publique de connexion.

# Comment rajouter une commande ?

Pour créer une commande il suffit de créer une nouvelle class qui doit implémenter **"ITCommand"**, puis l'enregistrer dans l'arraylist **"commands"**
située dans **"CommandManager"**


[jda]: https://github.com/DV8FromTheWorld/JDA/
[jetty]: https://github.com/eclipse/jetty.project
[spotify-api]: https://github.com/spotify-web-api-java/spotify-web-api-java
[lavaplayer]: https://github.com/sedmelluq/lavaplayer

# Crédits

Copyright Fyz - 2022
Sous license MIT

Vous pouvez utiliser, modifier, et partager le code de ce repo avec la simple condition de me créditer en cas d'utilisation.
