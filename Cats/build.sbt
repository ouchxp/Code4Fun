val commonSettings = Seq(
  scalaVersion := "2.13.0",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  )
)

lazy val macros = project.in(file("macros")).settings(commonSettings: _*)
lazy val cats = (project in file("cats")).
  settings(
    organization := "me.ouchxp",
    name := "Cats",
    version := "1.0",
    scalaVersion := "2.13.0",
    libraryDependencies ++= Seq(
      "com.github.mpilquist" %% "simulacrum" % "0.19.0",
      "org.typelevel" %% "cats-core" % "2.0.0-M4",
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_",
      "-Xlog-free-terms",
      "-Ymacro-annotations"
    )
  ).dependsOn(macros)
