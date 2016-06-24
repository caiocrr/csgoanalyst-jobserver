
resolvers += "Ooyala Bintray" at "http://dl.bintray.com/ooyala/maven"


libraryDependencies += "spark.jobserver" %% "job-server-api" % "0.7.0-SNAPSHOT" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1" % "provided"