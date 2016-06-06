
resolvers += "Ooyala Bintray" at "http://dl.bintray.com/ooyala/maven"


libraryDependencies += "spark.jobserver" %% "job-server-api" % "0.6.2" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1"