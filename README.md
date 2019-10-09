We create a Web Service to fetch nearby restaurants in Scala using Play framework.

Mongo db has been used to store data on cluster provided over mlab. Import the data provided (here)[data/] in mongo db following the steps in (docs)[docs/DBSetup.md].

As we use mongo db's geo-spatial APIs to provide location based services, we need to create indexes on data as follows:

`db.Restaurants.createIndex({location:"2dsphere"});`

`db.neighbourhoods.createIndex({geometry:"2dsphere"});`

We use SAP UI5 to create view.

Google Maps APIs have been used to draw map, fetch location coordinates, mark spots etc. Click [here](https://developers.google.com/maps/documentation/javascript/get-api-key) to get Google API Key.

Follow (instructions)[docs/CFDeploy.md] to deploy the app on Cloud Foundry.
