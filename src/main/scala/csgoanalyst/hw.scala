package csgoanalyst

import org.apache.spark.SparkContext, SparkContext._
import com.typesafe.config.Config

object GetAttackers extends spark.jobserver.SparkJob {

  override def runJob(sc: SparkContext, config: Config) = {
    
    val demo = sc.textFile("file:/home/caiocrr/Desktop/csgodemos/test1.txt").
          filter(line => line.startsWith("victim"))

    val attackersFiltered = demo.
          map(line => line.split(',')).
          map(fields => (fields(9),fields(10),fields(11),fields(12),fields(13),fields(14),fields(15),fields(16),fields(17),fields(18),fields(19)))
    
    attackersFiltered.take(1);
  }
  
  def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = spark.jobserver.SparkJobValid
}

