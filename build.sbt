name := "csgoanalyst-jobserver"

version := "1.0.0"

scalacOptions ++= Seq("-deprecation")

lazy val buildSettings = Seq(
  version := "0.1-SNAPSHOT",
   scalaVersion := "2.10.4"
)

resolvers += "Ooyala Bintray" at "http://dl.bintray.com/ooyala/maven"

libraryDependencies ++= Seq (
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.2",
  ("org.apache.spark" %% "spark-core" % "1.1.1").
    exclude("org.mortbay.jetty", "servlet-api").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog").
    exclude("junit", "junit").
    exclude("org.slf4j", "log4j12"),
  "ooyala.cnd" % "job-server" % "0.4.0" % "provided"
)

resolvers += "Job Server Bintray" at "https://dl.bintray.com/spark-jobserver/maven"
libraryDependencies += "spark.jobserver" %% "job-server-api" % "0.6.2" % "provided"