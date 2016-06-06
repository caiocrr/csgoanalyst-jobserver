package csgoanalyst

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._


object GetAttackers extends spark.jobserver.SparkJob {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[4]").setAppName("csgoanalyst")
    val sc = new SparkContext(conf)
    val config = ConfigFactory.parseString("")
    runJob(sc, config)

  }

  def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = spark.jobserver.SparkJobValid
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    val demo = sc.textFile("file:/home/caiocrr/Desktop/csgodemos/test1.txt").
      filter(line => line.startsWith("victim"))

    val attackersFiltered = demo.
          map(line => line.split(',')).
          map(fields => (fields(9),fields(10),fields(11),fields(12),fields(13),fields(14),fields(15),fields(16),fields(17),fields(18),fields(19)))
    
    attackersFiltered.collect().foreach(println);
  }
}






 