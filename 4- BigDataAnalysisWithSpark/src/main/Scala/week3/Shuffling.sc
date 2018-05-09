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
  *   - Customizing partitioning is only possible with pair RDDs
  *
  * How to do that ?
  *   - Hash partitioning:
  *           Attempts to spread data evenly across partitions based on the key
  *
  *   - Range partitioning:
  *           Use order between keys to partition. When possible it is more
  *           efficient than hash.
  *
  *
  *  => .persist() is necessary in order to avoid partitioning each time you call the RDD
  *  => Some function don't keep the choosen partitioning (as map and flatMap) so be careful !!
  */