## Setting up database:
### 1) Sign up on [Mlab](https://mlab.com) and login
### 2) Click on create new
![image1](Resources/1.%20Click%20on%20create%20new.png)
### 3) Select AWS free sandbox
![image2](Resources/2.%20Select%20AWS%20free%20sandbox.png)
### 4) Select a region and click continue 
![image3](Resources/3.%20Select%20a%20region%20and%20click%20continue.png)
### 5) Provide database name and click on continue
![image4](Resources/4.%20Provide%20database%20name%20and%20click%20on%20continue.png)
### 6) Preview and submit order
![image5](Resources/5.%20Preview%20and%20submit%20order.png)

## Adding Data (via shell)
### 1) Download [mongo db](https://www.mongodb.com/download-center#atlas)
### 2) Install it, following [instructions](https://docs.mongodb.com/getting-started/shell/installation/)
### 3) Make sure you create a data directory before starting mongo shell
### 4) Start mongo shell:
run `mongod.exe` via terminal.
### 5) Open another prompt and import data
`mongoimport --uri <mongo_uri> <Full path for json file including filename> -c <collection_name>`

e.g. `mongoimport --uri mongodb://<username>:<password>@ds113019.mlab.com:13019/<database> C:\user1\base\home\geo\data\Customers.json -c Customers`
### 6) Connect to your cluster on cloud
`mongo ds113019.mlab.com:13019/dev1 -u <username>`

provide password on prompt
### 7) Switch to your database
`use <database>`

`show collections` must display you the collections you've added.

`db.<collection_name>.findOne()` {to check if data is imported}

## Adding Data (via mlab ui)
### 1)	Navigate to your database and click on add collection
![image6](Resources/6.%20Navigate%20to%20database%20and%20click%20on%20Add%20collection.png)
### 2)	Provide a collection name and click on create
![image7](Resources/7.%20Add%20new%20Collection.png)
### 3)	Select your newly added collection and click on add document
![image8](Resources/8.%20Select%20newly%20added%20Collection.png)
![image9](Resources/9.%20Click%20on%20Add%20Document.png)
### 4)	Add valid json document and you may click on “create and go back” to “create and continue editing” for going back with added doc or editing the present document respectively.
![image10](Resources/10.%20Create%20Document.png)
