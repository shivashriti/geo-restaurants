package utils

import com.mongodb.DBCollection
import com.mongodb.casbah.Imports._
import com.typesafe.config._

object MongoDBClient {

  val config = ConfigFactory.load
  val username = config.getString("mongodb.username")
  val password = config.getString("mongodb.password")
  val databaseName = config.getString("mongodb.database")

  //get handle to mongodb client
  lazy val mongoClient: MongoClient = MongoClient(MongoClientURI(s"mongodb://$username:$password@ds113019.mlab.com:13019/dev1"))

  // get handle to the database
  lazy val database: MongoDB = mongoClient(databaseName)

  // get handle to a particular collection
  lazy val restaurants: MongoCollection = database("Restaurants")
  lazy val neighbourhoods: MongoCollection = database("neighbourhoods")

} 
