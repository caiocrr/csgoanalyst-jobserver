package csgoanalyst

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.util.Try
import org.apache.spark.rdd.RDD
import org.apache.hadoop.fs.FileSystem


object GetAttackers extends spark.jobserver.SparkJob with spark.jobserver.NamedRddSupport {


  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    val demo = sc.textFile("file:/home/caiocrr/Desktop/csgodemos/test1.txt").
      filter(line => line.startsWith("victim"))

//    case class Attacker(item1: String, item2: String, item3: String, item4: String, item5: String, item6: String, item7: String, item8: String, item9: String, item10: String, item11: String)
    
    val attackers = demo.map(line => line.split(',')).map(fields => Array(fields(9),fields(10),fields(11),fields(12),fields(13),fields(14),fields(15),fields(16),fields(17),fields(18),fields(19)))
    
    attackers.collect()   
    
    
   }
  
}


object getDemoName extends spark.jobserver.SparkJob {

   override def runJob(sc: SparkContext, config: Config): Any = {
        import java.io.File
        for (file <- new File("/home/caiocrr/Desktop/csgodemos").listFiles) { processFile(file) }     
  }

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  } 
}



 