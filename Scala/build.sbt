name := "Scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2"
libraryDependencies += "org.scala-lang.modules" % "scala-async_2.11" % "0.9.6-RC2"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"

scalacOptions ++= Seq(
  "-Xmax-classfile-name",
  "128",
  "-feature"
)
