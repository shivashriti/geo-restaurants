package controllers

import java.util.concurrent.TimeUnit
import javax.inject._

import play.api._
import play.api.libs.json._
import play.api.mvc._
import play.api.Logger._
import play.api.routing.JavaScriptReverseRouter
import utils.AppDAO

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class ApplicationController @Inject()(cc: ControllerComponents, appDao: AppDAO)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index())
  }

  // finds current neighbourhood of user
  def findNeighbourhood = Action.async { implicit request =>
    Logger.info("ApplicationController: findNeighbourhood")
    val longitude = extractParameter(request, "longitude")
    val latitude = extractParameter(request, "latitude")

    Try(longitude.get.toDouble, latitude.get.toDouble) match {
      case Success((long, lat)) =>
        appDao.findNeighbourhood(long, lat).map { res =>
          res match {
            case Some(result) => Ok(Json.parse(result.toString)).as("application/json")
            case None => NotFound("Could not locate any neighbourhood with given coordinates")
          }
        }
      case Failure(e) =>
        logger.error(e.getMessage)
        Future.successful(BadRequest("please provide valid longitude and latitude"))
    }
  }

  // finds all restaurants in the current neighbourhood of user
  def findAllRestaurants() = Action.async { implicit request =>
    Logger.info("ApplicationController: findAllRestaurants")
    val longitude = extractParameter(request, "longitude")
    val latitude = extractParameter(request, "latitude")

    Try(longitude.get.toDouble, latitude.get.toDouble) match {
      case Success((long, lat)) =>
        appDao.findAllRestaurants(long, lat).map { res =>
          Ok(Json.toJson(res.map(o => Json.parse(o.toString)))).as("application/json")
        }
      case Failure(e) =>
        logger.error(e.getMessage)
        Future.successful(BadRequest("please provide valid longitude and latitude"))
    }
  }

  // finds all restaurants in the current neighbourhood of user within a particular distance (unsorted)
  def findRestaurantsWithinDistance() = Action.async { implicit request =>
    Logger.info("ApplicationController: findRestaurantsWithinDistance")
    val longitude = extractParameter(request, "longitude")
    val latitude = extractParameter(request, "latitude")
    val distance = extractParameter(request, "distance")

    Try(longitude.get.toDouble, latitude.get.toDouble, distance.get.toDouble) match {
      case Success((long, lat, dis)) =>
        appDao.findRestaurantsWithinDistance(long, lat, dis).map { res =>
          Ok(Json.toJson(res.map(o => Json.parse(o.toString)))).as("application/json")
        }
      case Failure(e) =>
        logger.error(e.getMessage)
        Future.successful(BadRequest("please provide valid longitude, latitude and nearby distance"))
    }
  }

  // finds all restaurants in the current neighbourhood of user within a particular distance (sorted from nearest to farthest)
  def findRestaurantsSorted() = Action.async { implicit request =>
    Logger.info("ApplicationController: findRestaurantsWithinDistance")
    val longitude = extractParameter(request, "longitude")
    val latitude = extractParameter(request, "latitude")
    val distance = extractParameter(request, "distance")

    Try(longitude.get.toDouble, latitude.get.toDouble, distance.get.toDouble) match {
      case Success((long, lat, dis)) =>
        appDao.findRestaurantsSorted(long, lat, dis).map { res =>
          Ok(Json.toJson(res.map(o => Json.parse(o.toString)))).as("application/json")
        }
      case Failure(e) =>
        logger.error(e.getMessage)
        Future.successful(BadRequest("please provide valid longitude, latitude and nearby distance"))
    }
  }

  def jsRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.ApplicationController.findAllRestaurants,
        routes.javascript.ApplicationController.findNeighbourhood,
        routes.javascript.ApplicationController.findRestaurantsWithinDistance
      )
    ).as("text/javascript")
  }

  def extractParameter(request: Request[AnyContent], param: String): Option[String] =
    request.queryString.getOrElse("distance", Seq()).headOption
}
