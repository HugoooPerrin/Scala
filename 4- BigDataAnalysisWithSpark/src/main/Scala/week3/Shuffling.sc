/**
  * When you groupByKey, some data have to be moved from one node to another
  * in order to be group by key => that is SHUFFLING
  *
  *  => It may lead to high latency, and so, slower performance !
  *
  *  => That is why reduceByKey is more efficient than groupBy + reduce,
  *   since it only shuffles after a first reducing step on each node. The quantity
  *   of data sent from one node to another is so greatly reduced.
  *
  *
  *
  *
  * Spark uses hash partitioning to decide which key to put on which node:
  *
  * A RDD is always split into several partitions.
  *   - Data in a partition will always be on the same machine !
  *
  * How to do that ?
  *   - Hash partitioning:
  *
  *   - Range partitioning:
  *
  */