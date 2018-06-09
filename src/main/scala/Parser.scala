import scala.io.Source
import play.api.libs.json.Json
import slick.jdbc.SQLiteProfile.api._

import models.SeenMalware

object Parser extends App {
  val dataSource = Source.fromFile("log.json")

  val seenMalwares = dataSource.getLines.map(
    line => Json.parse(line).as[SeenMalware]
  )

  val database = Database.forConfig("sqlite")
  database.close
}
