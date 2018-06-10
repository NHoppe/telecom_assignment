import scala.io.Source
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Json

import models._

object Parser extends App {
  val dataSource = Source.fromFile("log.json")

  val seenMalwares = dataSource.getLines.map(
    line => Json.parse(line).as[SeenMalware]
  )

  val unknownMalwares = seenMalwares.filter(
    _.disposition == Disposition.UNKNOWN
  )

  unknownMalwares.foreach { malware =>
    SeenMalwaresDAO.findBySha(malware.sha256).onComplete {
      case Success(result) => result match {
        case Some(result) => println(result)
        case None => ()
      }

      case Failure(e) => e.printStackTrace
    }
  }
}
