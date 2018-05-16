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
  * DataFrames have their own API, with SQL-like functions :)
  *
  * As for RDDs, all operations are lazily evaluated !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  *
  */