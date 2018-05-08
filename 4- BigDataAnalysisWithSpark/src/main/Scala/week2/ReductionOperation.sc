/**
  *  Reduction Operations:
  *
  *   => Walk through a collection and combine neigboring elements of the
  *     collection together to produce a single combined result.
  *
  *   => Some are not parallelizable (foldLeft, foldRight) because of type issues.
  *     It may be possible in some particular cases under the "aggregate" operation.
  *     That operation allow to foldLeft on different chunks and then combine them !
  *
  *   => Spark RDDs: fold, reduce, aggregate (foldLeft/foldRight doesn't exist).
  *
  *
  *
  *  Pair RDDs: Distributed Key-Value pairs (efficient way to handle big data)
  *
  *   => Key choice in design of MapReduce.
  *   => Convenient to work with JSON type data.
  *   => Allow to act on each key in parallel or to regroup data accross the network.
  *
  *   => RDD[(A, B)] are treated particularly in Spark:
  *     - reduceByKey
  *     - groupByKey
  *     - mapValues
  *     - keys
  *     - join, leftOuterJoin, rightOuterJoin
  *     - countByKey
  *
  */

/*
Creating a Pair RDD
*/

val rdd: RDD[WikipediaPage] = ???

// Type: org.apache.spark.rdd.RDD[(String, String)]
val pairRDD = rdd.map(page => (page.title, page.text))