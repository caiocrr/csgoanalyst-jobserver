package csgoanalyst

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.util.Try


object GetAttackers extends spark.jobserver.SparkJob {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    val demo = sc.textFile("file:/home/caiocrr/Desktop/csgodemos/test1.txt").
      filter(line => line.startsWith("victim"))

    
//    case class Attacker(item1: String, item2: String, item3: String, item4: String, item5: String, item6: String, item7: String, item8: String, item9: String, item10: String, item11: String)
    
    val attackers = demo.map(line => line.split(','))
    val attackersFiltered = attackers.map(fields => (fields(9),fields(10),fields(11),fields(12),fields(13),fields(14),fields(15),fields(16),fields(17),fields(18),fields(19)))
        
    
    attackersFiltered.collect()
   }
  
}


object WordCountExample extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    Try(config.getString("input.string"))
      .map(x => spark.jobserver.SparkJobValid)
      .getOrElse(spark.jobserver.SparkJobInvalid("No input.string config param"))
  }

  override def runJob(sc: SparkContext, config: Config): Any = {
    sc.parallelize(config.getString("input.string").split(" ").toSeq).countByValue
  }
}



 