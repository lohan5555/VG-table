# VG-table
VG-table - l'app mobile qui vous permet de composer une table de saison !

## Fonctionnalités
- Afficher une liste de fruits et de légumes de saison en fonction du mois souhaité
- Faire une recherche pour trouver une recette et affiche les résultats
- Ajouter une recette au favoris


## Amélioration possible
- cliquer sur une card de la page Saison lance une recherche de recette avec le produit de la card
- permettre d'annuler une suppression de recette avec une snackBar
- changer la charte graphique et l’icône de l’application en fonction de la saison.
- des filtres pour les recettes (mois, gluten, végan, …) puisque l'API le permet.
- faire en sorte que les recherches affiches des résultats pertinents, c'est-à-dire contenants des fruits ou légumes de saison

## Technologies
- Jetpack Compose - Framework UI
- Coil - affichage d'image à partir d'une URL
- Retrofit - appel API
- Room - persistance local des données

## API
- ImpactCO2 : permet de récupérer la liste des fruits et légumes de saison
- Spoonacular : permet de récupérer les recettes
  (Utilisation de la version gratuite de l'API, donc limite de 50 requêtes par jour.
  Pour pallié à ça, on limite les requêtes API, les recettes en favoris sont sauvegardés en local)

## Image

### Page saison:
<img width="300" height="600" alt="Screenshot_20251206_191425" src="https://github.com/user-attachments/assets/07441918-46ae-4b33-b11e-8fd34cbc51b2" />

### Page favoris:
<img width="300" height="600" alt="Screenshot_20251206_191533" src="https://github.com/user-attachments/assets/978ebe04-9ddd-4b6e-9af3-18c6f91a7237" /> 

### Page recherche:
<img width="300" height="600" alt="Screenshot_20251206_191512" src="https://github.com/user-attachments/assets/f3169266-f610-4fc0-9bf0-6505866a1c86" />



