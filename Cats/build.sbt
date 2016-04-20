val catsVersion = "0.3.0"
val catsAll = "org.spire-math" %% "cats" % catsVersion
val macroParaside = compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
val kindProjector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.6.3")
val resetAllAttrs = "org.scalamacros" %% "resetallattrs" % "1.0.0"

val specs2Version = "3.7.3" // use the version used by discipline
val specs2Core  = "org.specs2" %% "specs2-core" % specs2Version
val specs2Scalacheck = "org.specs2" %% "specs2-scalacheck" % specs2Version
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.4"

lazy val root = (project in file(".")).
  settings(
    organization := "me.ouchxp",
    name := "Cats",
    version := "1.0",
    scalaVersion := "2.11.8",

    libraryDependencies ++= Seq(
      catsAll,
      specs2Core % Test, specs2Scalacheck % Test, scalacheck % Test,
      macroParaside, kindProjector, resetAllAttrs
    ),

    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )
