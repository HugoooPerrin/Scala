name := "4- BigDataAnalysisWithSpark"

version := "0.1"

scalaVersion := "2.11.12" // "2.12.5"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.8.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0",
  "org.apache.spark" %% "spark-sql" % "2.1.0"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false