name := "Scala"

version := "1.0"

scalaVersion := "2.13.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.23"
libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.10.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.0"

scalacOptions ++= Seq(
  "-feature"
)
