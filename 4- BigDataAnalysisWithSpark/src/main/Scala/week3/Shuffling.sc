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
  *  => Some function don't propagate the choosen partitioning (as map and flatMap) (because
  *      they can affect the keys) so be careful !!
  *  => Choosing a relevant partitioner may highly increase performance, by dramatically decrease shuffling and latencies
  *
  *
  *
  *  ==> RULE OF THUMB: A shuffle can occur when a resulting RDD depends on other elements from
  *                     either the same RDD or another RDD
  *
  *
  *  Dependencies are important because a fonction from one RDD to another RDD is actually a fonction from the
  *  partitions of the former to the partitions of the latter:
  *
  *   => Narrow dependencies:
  *       Each partition of the parent is used by at most one partition of the child.
  *       Fast, no shuffling involved
  *       Fast recomputation if fault (only failed partition to compute again from begining)
  *
  *   => Wide dependencies:
  *       Each partition of the parent may be depended on by multiple child partitions.
  *       Slow, requires all or some of the data to be shuffled
  *       Slow recomputation if fault (more computation to do)
  *
  *
  *
  *  Lineages are the key to fault tolerance in Spark:
  *
  *   Ideas for functional programming
  *   enable this fault tolerance in Spark. This is because these are immutable, so we can't update or
  *   change any of the data inside of an RDD. And then by using higher-order functions like map, flatMap and filter
  *   to do functional transformations on our immutable data, what we have is effectively a Directed Acyclic Graph.
  *
  *  => This means that we can actually, recover from failures by recomputing lost partitions from the lineage graphs.
  *
  */