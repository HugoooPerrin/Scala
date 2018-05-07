/**
  * Using Scala make it easier to scale small problems to the large using Spark, whose API is
  * close to 1-to-1 with Scala's collections, in a functional way.
  *
  * Spark advantages on Hadoop:
  *   => More expressive, flexible
  *   => Performance (running time, developer productivity)
  *   => Interactivity
  *   => Good for data science (thanks to iteration possibilities)
  *   => Awesome with latency
  *
  * Data-Parallel paradigm (shared memory):
  *   => Split data
  *   => Workers independently operate on the data shards in parallel
  *   => Combine when done
  *
  * Distributed Data-Parallel paradigm (distibuted memory):
  *   => Split data over several nodes
  *   => Nodes independently operate on the data shards in parallel
  *   => Combine when done
  *
  *   => NEW CONCERN: now we have to worry about network latency between nodes and partial failure
  *
  *   Latency cannot be masked completely, that has an actual impact on the programming model !!!!!!!!!
  *
  *   Reading data from disk is 100x slower than reading it from memory and communication in a network is
  *   1 000 000x slower than in-memory
  *
  *   Fault tolerance:
  *     => It is what made possible to scale to 100s or 10000s machines (Hadoop first, then Spark)
  *     => In Hadoop it comes with a price: lot of intermediate data written on disk / Network communication operations
  *     => Spark uses strategies from functional programming to handles latency issues:
  *       - Keeping track of immutable data and the transformations applied to the original dataset
  *       - All stay in memory and there is a network communication only when there is a failure
  *
  *   Result: Spark is 100x faster than Hadoop, while being way more flexible
  *
  *
  *
  *  Resilient Distributed Datasets (RDDs):
  *
  *   => Similar to immutable Scala collections
  *   => Heavy use of higher-order functions (map, flatMap)
  *   => Created an RDD:
  *     - Transformation of an existing RDD
  *     - From a SparkContext (or SparkSession) object
  *         (represents the connection between the Spark cluster and your running application)
  *   => Transformations:
  *     - Return a RDD
  *     - Lazy (resulting RDD not immediately computed)    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  *     - map, flatMap, filter, distinct
  *   => Actions:
  *     - Result is saved to an external storage system (not a RDD)
  *     - eager (result is immediately computed            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  *     - collect, count, take, reduce, fold, foreach, takeSample, TakeOrdered, SaveAsTextFile
  *
  *  Laziness/eagerness is how Spark can limit network communication using the programming model (substitution)
  *
  *  Main differences with Scala:
  *     => Evaluation:
  *       - By default, RDDs are recomputed each time you run an action on them,
  *         which is expensive if you need to use a dataset more than once.
  *
  *       - 5 different memory level for the .persist() function (memory, disk, serialized)
  *
  *       - It is not always immediately obvious upon first glance on what part of the cluster
  *       a line of code might run on.
  *
  *
  */


// Logistic Regression
def sc = ??? // SparkContext
val numIterations = ???
val alpha = ???
val d = ???

val points = sc.textFile(...).map(parsePoint)          // Here, since transformations are lazy, points is re-evaluated
                                                       // each time it is called in the for-loop
                                                       // HIGHLY INEFFICIENT

          // You can control what is cached in memory: .persist() or .cache()

var w = Vector.zeros(d)
for (i <- 1 to numIterations) {
  val gradient = points.map({ p => (1 / (1 + exp(-p.y * w.dot(p.x))) - 1) * p.y * p.y }).reduce(_ + _)
  w -= alpha * gradient
}

