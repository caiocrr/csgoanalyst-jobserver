
resolvers += "Ooyala Bintray" at "http://dl.bintray.com/ooyala/maven"


libraryDependencies += Seq (
  ("org.apache.spark" %% "spark-core" % "1.1.1").
    exclude("org.mortbay.jetty", "servlet-api").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog").
    exclude("junit", "junit").
    exclude("org.slf4j", "log4j12")
)

libraryDependencies += "spark.jobserver" %% "job-server-api" % "0.6.2" % "provided"
