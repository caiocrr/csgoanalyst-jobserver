$ git clone https://github.com/spark-jobserver/spark-jobserver

$ cd spark-jobserver


//publish local jobserver on sbt

$ sbt publish-local
$ sbt
> job-server/re-start

//build jar

$ cd csgoanalyst-jobserver
$ sbt assembly


//deploy jar(scala)

>curl --data-binary @csgoanalyst-jobserver/target/scala-2.10/csgoanalyst-jobserver-assembly-0.1-SNAPSHOT.jar localhost:8090/jars/csgoanalyst

>curl 'localhost:8090/jars'



//create context that will be shared - NAO VAI SER PRECISO


>curl -X POST 'localhost:8090/contexts/users-context'

>curl 'localhost:8090/contexts'



//Run

//ADHOC(CONTEXTO TEMPORARIO)

>curl -X POST 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.GetAttackers'

>curl -d "qtd=qtd&camp=camp" 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.GetAttackers'




//PERMANENT CONTEXT

>curl -X POST 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.GetAttackers&context=users-context'




//VIEW JOB

>curl 'localhost:8090/jobs/jobid'








////////////////////////////

//ListCamps OK
>curl -X POST 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.ListCamps'

//ListPlayers OK
>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"MLG 2016"}' '192.168.91.128:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.ListPlayers'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Katowice 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.ListPlayers'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Cologne 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.ListPlayers'


//TopKillers OK
>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"MLG 2016"}' '192.168.91.128:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKillers'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Katowice 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKillers'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Cologne 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKillers'


//TOP KD(Não tá em ordem)
>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"MLG 2016"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKD'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Katowice 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKD'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Cologne 2015"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopKD'



//KDEspec OK
>curl -H "Content-Type: application/json" -X POST -d '{"nickname":"adreN","camp":"MLG 2016"}' 'localhost:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.KDESpec'



//TopMVP - não funcionando
>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"MLG 2016"}' '192.168.91.128:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopMVP'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Katowice 2015"}' '192.168.91.128:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopMVP'

>curl -H "Content-Type: application/json" -X POST -d '{"qtd":"10","camp":"Cologne 2015"}' '192.168.91.128:8090/jobs?appName=csgoanalyst&classPath=csgoanalyst.TopMVP'









>files: /demo_manager/~
