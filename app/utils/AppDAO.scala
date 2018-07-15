package utils

import javax.inject._

import play.api.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.GeoCoords

trait AppDAO {
  def findNeighbourhood(longitude: Double, latitude: Double): Future[Option[DBObject]]
  def findAllRestaurants(longitude: Double, latitude: Double): Future[List[DBObject]]
  def findRestaurantsWithinDistance(longitude: Double, latitude: Double, distance: Double): Future[List[DBObject]]
  def findRestaurantsSorted(longitude: Double, latitude: Double, distance: Double): Future[List[DBObject]]
}

@Singleton
class AppDAOImpl extends AppDAO {

  import MongoDBClient._

  // finds current neighbourhood of user
  def findNeighbourhood(longitude: Double, latitude: Double): Future[Option[DBObject]] = Future {
    Logger.info(s"AppDao: findNeighbourhood")
    val geo = MongoDBObject("$geometry" ->
                    MongoDBObject("type" -> "Point",
                      "coordinates" -> GeoCoords(longitude, latitude)
      )
    )
    neighbourhoods.findOne("geometry" $geoIntersects geo)
  }

  // finds all restaurants in the current neighbourhood of user
  def findAllRestaurants(longitude: Double, latitude: Double): Future[List[DBObject]] = {
    Logger.info(s"AppDao: findAllRestaurants")
    findNeighbourhood(longitude, latitude).map { neighbourhood =>
      val geo = MongoDBObject("$geometry" -> neighbourhood.get("geometry"))
      val res: MongoCursor = restaurants.find("location" $geoWithin geo)
      res.foldLeft(List[DBObject]())((list, result) => result :: list)
    }
  }

  // finds all restaurants in the current neighbourhood of user within a particular distance (unsorted)
  def findRestaurantsWithinDistance(longitude: Double, latitude: Double, distance: Double): Future[List[DBObject]] = Future {
    Logger.info(s"AppDao: findRestaurantsWithinDistance")
    val earthRadius = 3963.2 // radius of earth in miles
    val res: MongoCursor = restaurants.find("location".$geoWithin $centerSphere (GeoCoords(longitude, latitude), distance / earthRadius))
    res.foldLeft(List[DBObject]())((list, result) => result :: list)
  }

  // finds all restaurants in the current neighbourhood of user within a particular distance (sorted from nearest to farthest)
  def findRestaurantsSorted(longitude: Double, latitude: Double, distance: Double): Future[List[DBObject]] = Future {
    Logger.info(s"AppDao: findRestaurantsSorted")
    val metersPerMile = 1609.34
    val res: MongoCursor = restaurants.find("location".$near(GeoCoords(longitude, latitude)).$maxDistance(distance * metersPerMile))
    res.foldLeft(List[DBObject]())((list, result) => result :: list)
  }
}
