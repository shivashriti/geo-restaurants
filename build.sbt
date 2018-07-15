lazy val settings = Seq(
  name := "geo-restaurants",
  version := "0.1.0-SNAPSHOT",
  libraryDependencies ++= Seq(
    "org.mongodb" %% "casbah" % "3.1.1",
    guice
  )
)

lazy val root = project.in(file("."))
  .enablePlugins(PlayScala)
  .settings(settings)


