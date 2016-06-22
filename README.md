$ git clone https://github.com/ooyala/spark-jobserver
$ cd spark-jobserver


//publish local jobserver on sbt

$ sbt publish-local
$ sbt
> re-start


//deploy jar(scala)

curl --data-binary @csgoanalyst-jobserver/target/scala-2.10/csgoanalyst-jobserver_2.10-0.1-SNAPSHOT.jar localhost:8090/jars/csgoanalyst



curl 'localhost:8090/jars'



//create context that will be shared - NAO VAI SER PRECISO


curl -X POST 'localhost:8090/contexts/users-context'

curl 'localhost:8090/contexts'



//Run


//ADHOC(CONTEXTO TEMPORARIO)
curl -X POST 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.GetAttackers'


//PERMANENT CONTEXT
curl -X POST 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.GetAttackers&context=users-context'




//VIEW JOB
curl 'localhost:8090/jobs/jobid'


//SBT JAR
sbt package