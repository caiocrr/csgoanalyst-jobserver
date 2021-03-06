package csgoanalyst

import com.typesafe.config.{ Config, ConfigFactory }
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.util.Try
import scala.runtime.ScalaRunTime._

import org.apache.spark.rdd.RDD
import java.io.File
import collection.mutable.ArrayBuffer

import scala.util.matching.Regex

object TopKillers extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val qtd = config.getString("qtd").toInt

    var attackersCount: RDD[(String, Int)] = sc.emptyRDD
    var demo = sc.textFile("/demo_manager/" + camp + "/*/*.txt").
      filter(line => line.startsWith("victim")).
      filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).map(fields => (fields(10).trim, 1))

    var attackersCountDemo = attackersName.reduceByKey((v1, v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    attackersCount.
      reduceByKey((v1, v2) => v1 + v2).
      sortBy { -_._2 }.
      take(qtd)
  }

}

object KDESpec extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val nickname = config.getString("nickname")
    var attackersCount: RDD[(String, Int)] = sc.emptyRDD
    var victimsCount: RDD[(String, Int)] = sc.emptyRDD

    var demo = sc.textFile("/demo_manager/" + camp + "/*/*.txt").
      filter(line => line.startsWith("victim")).
      filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).
      map(fields => (fields(10).trim, 1)).
      filter(_._1.contains(nickname))

    val victimsName = demo.map(line => line.split(',')).
      map(fields => (fields(1).trim, 1)).
      filter(_._1.contains(nickname))

    var attackersCountDemo = attackersName.reduceByKey((v1, v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    var victimsCountDemo = victimsName.reduceByKey((v1, v2) => v1 + v2)
    victimsCount = victimsCount.union(victimsCountDemo)

    val result = attackersCount.
      reduceByKey((v1, v2) => v1 + v2).
      join(victimsCount.
        reduceByKey((v1, v2) => v1 + v2))

    result.collect()
  }

}

object TopKD extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val qtd = config.getString("qtd").toInt
    var attackersCount: RDD[(String, Int)] = sc.emptyRDD
    var victimsCount: RDD[(String, Int)] = sc.emptyRDD

    var demo = sc.textFile("/demo_manager/" + camp + "/*/*.txt").
      filter(line => line.startsWith("victim")).
      filter(line => !line.contains("knife"))

    val attackersName = demo.map(line => line.split(',')).
      map(fields => (fields(10).trim, 1))

    val victimsName = demo.map(line => line.split(',')).
      map(fields => (fields(1).trim, 1))

    var attackersCountDemo = attackersName.reduceByKey((v1, v2) => v1 + v2)
    attackersCount = attackersCount.union(attackersCountDemo)

    var victimsCountDemo = victimsName.reduceByKey((v1, v2) => v1 + v2)
    victimsCount = victimsCount.union(victimsCountDemo)

    val result = attackersCount.
      reduceByKey((v1, v2) => v1 + v2).
      join(victimsCount.
        reduceByKey((v1, v2) => v1 + v2))

    result.take(qtd)
  }

}

object ListPlayers extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    var playersCount: RDD[(String)] = sc.emptyRDD

    val camp = config.getString("camp")
    var demo = sc.textFile("/demo_manager/" + camp + "/*/*.txt").
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

    val camp = config.getString("camp")
    val qtd = config.getString("qtd").toInt

    var demo = sc.textFile("/demo_manager/" + camp + "/*/*.txt").zipWithIndex.map { case (k, v) => (v, k) }
    var getMVPLine = demo.filter(_._2.contains("round_mvp"))

    var getMVPLineKeys = getMVPLine.keys.map(x => x + 2).zipWithIndex

    val playersMVP = demo.join(getMVPLineKeys).map {
      case (x, (y, z)) => y.trim
    }.map(x => x.split(" ")).map(x => (x(1), 1)).reduceByKey((v1, v2) => v1 + v2).sortBy { -_._2 }.take(qtd)

  }

}

object ListCamps extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val directoryName = "/demo_manager"

    val directory = new File(directoryName)
    val files = directory.listFiles // this is File[]
    val dirNames = ArrayBuffer[String]()
    for (file <- files) {
      if (file.isDirectory()) {
        dirNames += file.getName()
    }
      }
    dirNames.toList
  }

}

object ListMaps extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val directoryName = "/demo_manager/" + camp

    val directory = new File(directoryName)
    val files = directory.listFiles // this is File[]
    val dirNames = ArrayBuffer[String]()
    for (file <- files) {
      if (file.isDirectory()) {
        dirNames += file.getName()
    }
      }
    dirNames.toList
  }

}

object PosFirstKillers extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val qtd = config.getString("qtd").toInt
    val map = config.getString("map")

    var demo = sc.textFile("/demo_manager/" + camp + "/" + map + "/*.txt")

    //filter demo map    
    var getFreezeAndVictims = demo.filter(x => x.contains("round_freeze_end") || x.contains("victim")).zipWithIndex.map { case (k, v) => (v, k) }

    //filter lines round_freeze_and
    var getFreezeEnd = getFreezeAndVictims.filter(_._2.contains("round_freeze_end"))

    var getFirstKillsLines = getFreezeEnd.keys.map(x => x + 1).zipWithIndex

    val firstKills = getFreezeAndVictims.join(getFirstKillsLines).map {
      case (x, (y, z)) => y.trim
    }.filter(x => !x.contains("round_freeze_end")).
      map(line => line.split(',')).
      map(fields => Array(fields(12).trim, fields(13).trim))

    firstKills.collect()
  }

}

object PosSmokes extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val map = config.getString("map")

    var demo = sc.textFile("/demo_manager/" + camp + "/" + map + "/*.txt").zipWithIndex.map { case (k, v) => (v, k) }
       var getSmokeLine = demo.filter(_._2.contains("smokegrenade_expired"))

    var getSmokeXLine = getSmokeLine.keys.map(x => (x + 7, 1))
    var getSmokeYLine = getSmokeLine.keys.map(x => (x + 8, 1))
    
    val getSmokeX = demo.join(getSmokeXLine).
    map {
      case (x, (y, z)) => y.trim
    }.
    filter(x => x.startsWith("x:")).
    map(x => x.split(':')).
    map(x => x(1).trim).
    zipWithIndex.
    map { case (k, v) => (v, k) }
    
    val getSmokeY = demo.join(getSmokeYLine).map {
      case (x, (y, z)) => y.trim
    }.
    filter(x => x.startsWith("y:")).
    map(x => x.split(':')).
    map(x => x(1).trim).
    zipWithIndex.
    map { case (k, v) => (v, k) }
    
    val getSmokeXY = getSmokeX.join(getSmokeY).map {
      case (x, (y, z)) => Array(y,z)
    }
    
    getSmokeXY.collect();
    
    
  }

}


object PosMolotovs extends spark.jobserver.SparkJob {

  override def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = {
    spark.jobserver.SparkJobValid
  }

  override def runJob(sc: SparkContext, config: Config): Any = {

    val camp = config.getString("camp")
    val map = config.getString("map")

    var demo = sc.textFile("/demo_manager/" + camp + "/" + map + "/*.txt").zipWithIndex.map { case (k, v) => (v, k) }
       var getSmokeLine = demo.filter(_._2.contains("inferno_startburn"))

    var getSmokeXLine = getSmokeLine.keys.map(x => (x + 3, 1))
    var getSmokeYLine = getSmokeLine.keys.map(x => (x + 4, 1))
    
    val getSmokeX = demo.join(getSmokeXLine).
    map {
      case (x, (y, z)) => y.trim
    }.
    filter(x => x.startsWith("x:")).
    map(x => x.split(':')).
    map(x => x(1).trim).
    zipWithIndex.
    map { case (k, v) => (v, k) }
    
    val getSmokeY = demo.join(getSmokeYLine).map {
      case (x, (y, z)) => y.trim
    }.
    filter(x => x.startsWith("y:")).
    map(x => x.split(':')).
    map(x => x(1).trim).
    zipWithIndex.
    map { case (k, v) => (v, k) }
    
    val getSmokeXY = getSmokeX.join(getSmokeY).map {
      case (x, (y, z)) => Array(y,z)
    }
    
    getSmokeXY.collect()
    
    
  }

}

