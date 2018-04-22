name := "3- ParallelProgramming"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies += "junit" % "junit" % "4.10" % Test
libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter-core" % "0.6",
  "com.github.scala-blitz" %% "scala-blitz" % "1.1",
  "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
  "com.storm-enroute" %% "scalameter" % "0.6" % Test
)