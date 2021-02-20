val dottyVersion = "3.0.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "0.1.0",

    scalaVersion := dottyVersion,
    useScala3doc := true,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
