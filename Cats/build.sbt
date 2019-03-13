val macroParadise = compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


val commonSettings = Seq(
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1"
  )
)

lazy val macros    = project.in(file("macros")).settings(commonSettings : _*)
lazy val cats = (project in file("cats")).
  settings(
    organization := "me.ouchxp",
    name := "Cats",
    version := "1.0",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "com.github.mpilquist" %% "simulacrum" % "0.15.0",
      "org.typelevel" %% "cats-core" % "1.6.0",
      "com.softwaremill.scalamacrodebug" %% "macros" % "0.4.1",
      macroParadise,
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_",
      "-Xlog-free-terms"
    )
  ).dependsOn(macros)
