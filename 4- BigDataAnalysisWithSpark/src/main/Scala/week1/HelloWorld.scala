/**
  * Word Count is the "Hello, Wolrd!" of programming with large-scale data.
  */

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object WordCount extends App {

/*  // Create an RDD
  val text: RDD[String] = spark.textFile("hdfs://...") // One element is a line

  // Count word occurences
  val count: RDD[String] = rdd.flatMap(line => line.split(" ")) // Separate lines into words
                              .map(word => (word, 1))           // Include something to count
                              .reduceByKey(_ + _)               // Sum up the 1s in the pairs*/
}
