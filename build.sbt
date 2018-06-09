name := "TelecomCodeChallenge"

version := "0.1"

scalaVersion := "2.11.6"

sbtVersion := "1.1.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.4.6",
  "com.typesafe.slick" %% "slick" % "3.2.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.2",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
)