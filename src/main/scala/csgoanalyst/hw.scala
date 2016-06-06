package csgoanalyst

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.util.Try


object GetAttackers extends spark.jobserver.SparkJob {
//  def main(args: Array[String]) {
//    val conf = new SparkConf().setMaster("local[4]").setAppName("csgoanalyst")
//    val sc = new SparkContext(conf)
//    val config = ConfigFactory.parseString("")
//    val results = runJob(sc, config)
//    println(results)
//
//  }

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    val demo = sc.textFile("file:/home/caiocrr/Desktop/csgodemos/test1.txt").
      filter(line => line.startsWith("victim"))

    demo.map(line => line.split(',')).
         map(fields => (fields(9),fields(10),fields(11),fields(12),fields(13),fields(14),fields(15),fields(16),fields(17),fields(18),fields(19))).
         collect();
   }
  
}


object WordCountExample extends spark.jobserver.SparkJob {
//  def main(args: Array[String]) {
//    val conf = new spark.jobserver.SparkConf().setMaster("local[4]").setAppName("WordCountExample")
//    val sc = new spark.jobserver.SparkContext(conf)
//    val config = ConfigFactory.parseString("")
//    val results = runJob(sc, config)
//    println("Result is " + results)
//  }

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    Try(config.getString("input.string"))
      .map(x => spark.jobserver.SparkJobValid)
      .getOrElse(spark.jobserver.SparkJobInvalid("No input.string config param"))
  }

  override def runJob(sc: SparkContext, config: Config): Any = {
    sc.parallelize(config.getString("input.string").split(" ").toSeq).countByValue
  }
}



 