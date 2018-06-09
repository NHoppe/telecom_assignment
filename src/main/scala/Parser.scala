import scala.io.Source
import play.api.libs.json.Json
import models.SeenMalware

object Parser extends App {
  val dataSource = Source.fromFile("log.json")

  val seenMalwareObjects = dataSource.getLines.map(
    line => Json.parse(line).as[SeenMalware]
  )

  for(obj <- seenMalwareObjects) {
    println(obj)
  }
}
