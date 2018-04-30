import scala.collection.mutable

import scala.collection._

object Violation extends App {

  val graph = mutable.Map[Int, Int]() ++= (0 until 1000).map(i => (i, i+1))
  graph(graph.size - 1) = 0
  for ((k, v) <- graph.par) graph(k) = graph(v) // All elements are shifted by one
  val violation = graph.find({ case (i, v) => v != (i + 2) % graph.size })
  println(s"violations: $violation")

  /**
    * Never write to a collection that is concurrently traversed
    * Never read from a collection that is concurrently modified
    *
    * ==> Non deterministic programs
    */

  // Exception for TrieMap
  val graph2 = concurrent.TrieMap[Int, Int]() ++= (0 until 1000).map(i => (i, i+1))
  graph2(graph2.size - 1) = 0
  val previous = graph2.snapshot()            // Very efficient method of complexity o(1)
  for ((k, v) <- graph2.par) graph2(k) = previous(v) // All elements are shifted by one
  val violation2 = graph2.find({ case (i, v) => v != (i + 2) % graph2.size })
  println(s"violations: $violation2")

}
