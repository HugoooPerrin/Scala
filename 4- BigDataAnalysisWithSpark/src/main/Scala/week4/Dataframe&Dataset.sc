/**
  * Most of operations on Spark can be written differently. They will all end up to
  * the same result but the execution time may be vastly different.
  *
  * => Spark + regular RDDs don't know anything about the schema of the data
  *     it's dealing with. Every element is seen as an object instance, Spark cannot
  *     see 'inside'.
  *
  *     The same can be said about computation and functional transformation.
  *
  * => Given a bit of extra structural information, Spark can do many optimization
  *     for us.
  *
  *     => Spark SQL makes this possible
  *
  *
  * Spark SQL (can be seen as a library)
  *
  *   => Get all the optimizations we are used to in the databases community on Spark jobs
  *
  *   => Support relational processing both within Spark programs (on RDDs)
  *   => High performance (using techniques from research database world)
  *   => Open to new data sources
  *
  *   Main APIs:
  *     -> SQL syntax (A relation is a table, an attribute is a column, a record/tuple is a row)
  *     -> DataFrames
  *     -> Datasets
  *
  *   Specialized backend components:
  *     -> Catalyst: query optimizer
  *     -> Tungsten: off-heap serializer
  *
  *
  *  import org.apache.spark.sql.types._
  *  import org.apache.spark.sql.functions._
  *  import org.apache.spark.sql.SparkSession
  *
  *  val spark = SparkSession.builder
  *                          .appName("SQL app")
  *                          .getOrCreate()
  *
  * Useful functions: .show(20)            Shows 20 first rows in a relational way
  *                   .printSchema()
  *
  * DataFrames:
  *   => have their own API, with SQL-like functions :)
  *   => As for RDDs, all operations are lazily evaluated !!!!!!!!!!!!!!!!!!!!
  *
  *   Transformations, actions:
  *     -> drop(), drop("all"), drop(Array('ID'))
  *     -> fill(0), fill(Map("ID" -> 0)), replace(Array('ID'), Map(124 -> 123))
  *     -> collect(), count(), take(n), show()
  *
  *   => Execution process is automatically optimized by SQL (whether you join before filter or the other
  *       way around) because Catalyst compiles Spark-SQL programs down to RDDs:
  *       -> Reordering the operations
  *       -> Reduce the amount of data we must read
  *       -> Pruning unneeded partitioning
  *       -> Highly-specialized data encoders (thanks to knowing the schema) (Tungsten)
  *       -> off-heap (avoid garbage collection overhead) (Tungsten)
  *
  *
  *   Careful => Spark dataframes are untyped, that means that you can make mistake (wrong name, wrong type)
  *               and still successfully compile. But then there would be exceptions during running time.
  *
  *           => Need structured data ! Difficult to expressed complicated regular scala class for instance
  *
  *
  *
  * Datasets (import spark.implicits._):
  *
  *   => Dataframes with typesafety
  *
  *   => type DataFrame = Dataset[Row]
  *
  *   => Can be thought as a compromise between RDDs & DataFrames (mix and match)
  *
  *   => Relational operations + typed operations + higher-order functions
  *
  *   => DF adn DS APIs are integrated.
  *
  *   => Instead of Column we now have typedColumns (add .as[T] )
  *
  *   Operations:
  *     -> groupByKey + reduceGroups / agg / mapGroupgs (to avoid -> mapValues + reduceGroups)
  *     -> coalesce
  *     -> repartition
  *
  *     -> new Aggregator[IN, BUF, OUT] to implement your own aggregation operation
  *
  *
  *
  *
  *
  * Note: Encoders are what convert your data between JVM objects and Spark SQL's specialized
  *         internal representation.
  */

/*
// Aggregator example
import spark.implicits._
import org.apache.spark.sql.expressions.Aggregator

val keyValues = List((3, "Me"), (1, "Thi"), (2, "Se"), (3, "ssa"))

val keyValuesDS = keyValues.toDS

val strConcat = new Aggregator[(Int, String), String, String] {
  def zero: String = ""
  def reduce(b: String, a: (Int, String)): String = b + a._2
  def merge(b1: String, b2: String): String = b1 + b2
  def finish(r: String): String = r
}.toColumn

keyValuesDS.groupByKey(pair => pair._1)
           .agg(strConcat.as[String])
*/
