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

  // I was not able to add a connection pool to SeenMalwaresDAO, so this
  // block fails with `rejectedExecution` exception after writing in the
  // database for a while
  unknownMalwares.foreach { unknownMalware =>
    SeenMalwaresDAO.findBySha(unknownMalware.sha256).onComplete {
      case Success(malware) => malware match {
        case Some(malware) =>
          println(s"UPDATE ${unknownMalware.sha256}")
          malware.count += 1
          SeenMalwaresDAO.update(unknownMalware)
        case None =>
          println(s"CREATE ${unknownMalware.sha256}")
          SeenMalwaresDAO.create(unknownMalware)
      }
      case Failure(e) => println(e.getStackTrace.head)
    }
  }
}
