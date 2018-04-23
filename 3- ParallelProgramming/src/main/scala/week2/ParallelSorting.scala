package parallel

import common._
import util.Random.nextInt
import org.scalameter._

/**
  * Stackoverflow issues
  */

object ParallelSorting {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 50,
    Key.exec.benchRuns -> 10,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
    case (Nil, ys) => ys
    case (xs, Nil) => xs
    case (x :: xs1, y :: ys1) =>
      if (x < y) x :: merge(xs1, ys)
      else y :: merge(xs, ys1)
  }

  def sortSequential(xs: List[Int]): List[Int] = {

    val n = xs.length/2
    if (n == 0) xs
    else {
      val (first, second) = xs splitAt n  // Split the list in two smaller list
      merge(sortSequential(first), sortSequential(second))  // Sorting the smaller list and merging back
    }
  }

  def sortParallel(xs: List[Int], maxDepth: Int): List[Int] = {

    def sortParallelInter(xs: List[Int], depth: Int): List[Int] = {
      if (depth == maxDepth)
        sortSequential(xs)
      else {
        val n = xs.length / 2
        if (n == 0) xs
        else {
          val (first, second) = xs splitAt n                         // Split the list in two smaller list
          val t1 = task { sortParallelInter(first, depth + 1) }      // Sorting the first one
          val t2 = task { sortParallelInter(second, depth + 1) }     // Sorting the second one
          merge(t1.join(), t2.join())                                // Merging them back
        }
      }
    }

    sortParallelInter(xs, 0)
  }

  def main(args: Array[String]): Unit = {

    // Generation of random list
    val list = List.fill(4000)(100000).map(nextInt)

    // Sequential computation
    val seqtime = standardConfig measure { sortSequential(list) }
    println(s"sequential time : $seqtime")

    // Parallel computation
    // Not awesomely faster, but maybe on larger list
    // Moreover, List are not great when it come to parallelism (=> Arrays)
    val partime = standardConfig measure { sortParallel(list, 2) }
    println(s"Parallel time : $partime")

    // Benchmark
//    println(s"Speedup: ${seqtime / partime}")
  }
}