package parallel

import common._
import util.Random.nextInt
import org.scalameter._

object Scan {

  // Benchmark parameters
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 50,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  // Test values
  def fun(x: Int, y: Int): Int = x+y
  val a0 = 100
  val array = Array.fill(800000)(100).map(nextInt)
  val out = new Array[Int](array.length+1)
  val out2 = new Array[Int](array.length+1)


  // Sequential
  def scanLeft(in: Array[Int], a0: Int, f: (Int, Int) => Int, out: Array[Int]): Unit = {
    out(0) = a0
    var a = a0
    var i = 0
    while (i < in.length) {
      a = f(a, in(i))
      i += 1
      out(i) = a
    }
  }

  /**
    * Parallel implementation:
    * 1 - Turn the array into a tree with intermediate results (upsweep)
    * 2 - Parallel computation on subtrees (downsweep)
    * 3 - Add first element (prepend)
    */

  sealed abstract class TreeRes {
    val res: Int
  }
  case class Leaf(from: Int, to: Int,
                  override val res: Int) extends TreeRes
  case class Node(left: TreeRes,
                  override val res: Int,
                  right: TreeRes) extends TreeRes

  def reduceSeg(input: Array[Int], left: Int, right: Int,
                a0: Int, f: (Int, Int) => Int): Int = {
    var a = a0
    var i = left
    while (i < right) {
      a = f(a, input(i))
      i += 1
    }
    a
  }

  def upsweep(input: Array[Int], from: Int, to: Int,
              f: (Int, Int) => Int, threshold: Int): TreeRes = {
    if (to - from < threshold)
      Leaf(from, to, reduceSeg(input, from + 1, to, input(from), f))
    else {
      val mid = from + (to - from)/2
      val (treeLeft, treeRight) = parallel(upsweep(input, from, mid, f, threshold),
                                           upsweep(input, mid, to, f, threshold))
      Node(treeLeft, f(treeLeft.res, treeRight.res), treeRight)
    }
  }

  def scanLeftSeg(input: Array[Int], left: Int, right: Int,
                  a0: Int, f: (Int, Int) => Int, out: Array[Int]) = {
    if (left < right) {
      var i = left
      var a = a0
      while (i < right) {
        a = f(a, input(i))
        i += 1
        out(i) = a
      }
    }
  }

  def downsweep(input: Array[Int], a0: Int, f: (Int, Int) => Int,
                tree: TreeRes, out: Array[Int]): Unit = tree match {
    case Leaf(from, to, res) =>
      scanLeftSeg(input, from, to, a0, f, out)     // Sequential computation on leaf (arrays of size threshold)
    case Node(left, _, right) => {
      val (_, _) = parallel(downsweep(input, a0, f, left, out),
                            downsweep(input, f(a0, left.res), f, right, out))
    }
  }

  def scanLeftParallel(input: Array[Int],
                       a0: Int, f: (Int, Int) => Int, threshold: Int,
                       out: Array[Int]) = {
    val intermediateTree = upsweep(input, 0, input.length, f, threshold)
    downsweep(input, a0, f, intermediateTree, out)
    out(0) = a0
  }


  def main(args: Array[String]): Unit = {

    // Sequential computation
    val seqtime = standardConfig measure { scanLeft(array, a0, fun, out) }
    println(s"sequential time : $seqtime")

    // Optimal value: number of available threads
    val partime = standardConfig measure { scanLeftParallel(array, a0, fun, 100000, out2) }
    println(s"Parallel time : $partime")

/*    // Coherence check
    scanLeft(array, a0, fun, out)
    scanLeftParallel(array, a0, fun, 20, out2)
    println(out.toList)
    println(out2.toList)*/
  }
}



