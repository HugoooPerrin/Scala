import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import math.Ordering

object Brouillon extends App {

  System.setProperty("hadoop.home.dir", "C:\\winutils")

  val conf: SparkConf = new SparkConf()
    .setAppName("WikipediaRanking")
    .setMaster("local")
  val sc: SparkContext = new SparkContext(conf)

  val words = sc.parallelize(List("jbgt", "eghtgrtg", "ujbhzritub", "gufiozgb", "ijgbriue",
    "pamqsjhfa", "pqogjazg", "izoehgr", "paogemrg", "wnbvaez", "xvqsere"))

  val letters = List("a", "p", "x", "m")

  val result = words.flatMap(w => letters.filter(l => w.contains(l)).map((_, w))).groupByKey()
  val pairs = result.mapValues(_.size).collect().toList.sortWith((l1, l2) => l1._2 > l2._2)

  val result2 = words.flatMap(w => letters.filter(l => w.contains(l)).map((_, 1))).reduceByKey(_ + _)

  println("\n\n" + result2.collect().toList + "\n")

//  println("\n" + pairs + "\n\n")

  sc.stop()
}
