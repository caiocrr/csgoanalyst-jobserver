package csgoanalyst

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.util.Try
import org.apache.spark.rdd.RDD
import scala.runtime.ScalaRunTime._
import org.apache.spark.rdd.RDD
import java.io.File


object TopKillers extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    
    val qtd = config.getString("qtd").toInt
    
    var attackersCount: RDD[(String,Int)] = sc.emptyRDD

    val demo = sc.textFile("/home/caiocrr/Desktop/demo_manager/txts/*.txt").
    filter(line => line.startsWith("victim")).
    filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).map(fields => (fields(10).trim, 1))

    var attackersCountDemo = attackersName.reduceByKey((v1,v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    attackersCount.
    reduceByKey((v1,v2) => v1 + v2).
    sortBy {- _._2}.
    take(qtd)
    
   }
  
}


object KDESpec extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    
    val nickname = config.getString("nickname")
    
    var attackersCount: RDD[(String,Int)] = sc.emptyRDD
    var victimsCount: RDD[(String,Int)] = sc.emptyRDD
            
    val demo = sc.textFile("/home/caiocrr/Desktop/demo_manager/txts/*.txt").
    filter(line => line.startsWith("victim")).
    filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).
    map(fields => (fields(10).trim, 1)).
    filter(_._1.contains(nickname))  

    val victimsName = demo.map(line => line.split(',')).
    map(fields => (fields(1).trim, 1)).
    filter(_._1.contains(nickname))

    var attackersCountDemo = attackersName.reduceByKey((v1,v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    var victimsCountDemo = victimsName.reduceByKey((v1,v2) => v1 + v2)
    victimsCount = victimsCount.union(victimsCountDemo)

    val result = attackersCount.
    reduceByKey((v1,v2) => v1 + v2).
    join(victimsCount.
    reduceByKey((v1,v2) => v1 + v2))

    result.collect()
    
   }
  
}


object TopKD extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    
    val qtd = config.getString("qtd").toInt
    
    var attackersCount: RDD[(String,Int)] = sc.emptyRDD
    var victimsCount: RDD[(String,Int)] = sc.emptyRDD
            
    val demo = sc.textFile("/home/caiocrr/Desktop/demo_manager/txts/*.txt").
    filter(line => line.startsWith("victim")).
    filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).
    map(fields => (fields(10).trim, 1))

    val victimsName = demo.map(line => line.split(',')).
    map(fields => (fields(1).trim, 1))

    var attackersCountDemo = attackersName.reduceByKey((v1,v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    var victimsCountDemo = victimsName.reduceByKey((v1,v2) => v1 + v2)
    victimsCount = victimsCount.union(victimsCountDemo)

    val result = attackersCount.
    reduceByKey((v1,v2) => v1 + v2).
    join(victimsCount.
    reduceByKey((v1,v2) => v1 + v2))

    result.take(qtd)
    
   }
  
}



object ListPlayers extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    
    var playersCount: RDD[(String)] = sc.emptyRDD
     
    val demo = sc.textFile("/home/caiocrr/Desktop/demo_manager/txts/*.txt").
    filter(line => line.startsWith("victim"))

    val playersName = demo.map(line => line.split(',')).
    map(fields => (fields(1).trim)).distinct()

    playersCount = playersCount.union(playersName)

    val result = playersCount.
    distinct()

    result.sortBy(string => string).collect()

    
   }
  
}




object TopMVP extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    

    val qtd = config.getString("qtd").toInt
    
    var demo = sc.textFile("/home/caiocrr/Desktop/demo_manager/txts/*.txt").zipWithIndex.map{case (k,v) => (v,k)}
    var getMVPLine = demo.filter(_._2.contains("round_mvp"))
    
    var getMVPLineKeys = getMVPLine.keys.map(x => x+2).zipWithIndex

    val playersMVP = demo.join(getMVPLineKeys).map{
      case (x, (y,z)) => y.trim
    }.map(x => x.split(" ")).map(x => (x(1), 1)).reduceByKey((v1,v2) => v1 + v2).sortBy {- _._2}.collect()
     
   }
  
}

 