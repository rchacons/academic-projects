# sir-tp10-IntegrationBack-Front
### Par Roberto Chacon et Manh-Huan NGUYEN.

## Setup

Pour démarrer, veuillez lancer la classe ***RestServer*** de l'application back.

Ensuite, veuillez lancer le front depuis le dossier du projet *ticket-ui* :

```
ng serve
```

Une fois l'application lancé, vous pouvez l'ouvrir dans l'adresse : 

```
http://localhost:4200/
```

&nbsp;
## **Première étape** 
&nbsp;

### **Modification des routes**

Afin de regler les problèmes de CORS, nous avons crée un fichier ***proxy.config.json*** dans lequel nous avons ajouté la route vers l'API :

```
    "/ticket-api": {
        "target": "http://127.0.0.1:8090",
        "secure": "false",
        "pathRewrite": {
            "^/ticket-api": ""
        },
        "logLevel": "debug"
    }
```
Et nous avons ajouté la configuration du proxy dans le fichier de configuration CLI : ***angular.json***
```
    "serve": {
        "builder": "@angular-devkit/build-angular:dev-server",
        "options": {  
            "proxyConfig": "src/proxy.conf.json"
    }
```

Finalement, our utiliser plus simplement cette route dans notre code, nous avons crée une variable global dans ***environments.ts** afin de pouvoir l'appeler depuis n'importe où. 

```
export const environment = {
    production: false,
    ticketApi: "/ticket-api",
}
```


