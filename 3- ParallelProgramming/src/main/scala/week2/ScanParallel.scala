package parallel

import common._
import util.Random.nextInt
import org.scalameter._

object Scan {

  // Benchmark parameters
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 20,
    Key.exec.benchRuns -> 10,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  // Test values
  def fun(x: Int, y: Int): Int = x+y
  val a0 = 100
  val array = Array.fill(10)(100).map(nextInt)
  val out = new Array[Int](array.length+1)
  val out2 = new Array[Int](array.length+1)

  // Sequential scan
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

  // Parallel scan: hypothesis on f being associative
  def scanLeftSeq(in: Array[Int], a0: Int, f: (Int, Int) => Int, out: Array[Int],
                  start: Int, end: Int): Unit = {
    out(start) = a0
    var a = a0
    var i = start
    while (i < end) {
      a = f(a, in(i))
      i += 1
      out(i) = a
    }
  }

  def scanLeftParallel(in: Array[Int], a0: Int, f: (Int, Int) => Int, out: Array[Int],
                       start: Int, end: Int, threshold: Int): Unit = {

    if (end - start < threshold)  scanLeftSeq(in, a0, f, out, start, end)
    else {
      val mid = (start + end) / 2
      val t1 = task { scanLeftParallel(in, a0, f, out, start, mid, threshold) }
      val t2 = task { scanLeftParallel(in, a0, f, out, mid, end, threshold) }
      t1.join()
      t2.join()
      for (i <- mid+1 to end) {out(i) = f(out(mid), out(i))} // Because f is assumed to be associative
    }

  }

  def main(args: Array[String]): Unit = {

    // Sequential computation
    val seqtime = standardConfig measure { scanLeft(array, a0, fun, out) }
    println(s"sequential time : $seqtime")

    // Optimal value: number of available threads
    val partime = standardConfig measure { scanLeftParallel(array, a0, fun, out2, 0, array.length, 60) }
    println(s"Parallel time : $partime")

    // Coherence check
    scanLeft(array, a0, fun, out)
    scanLeftParallel(array, a0, fun, out2, 0, array.length, 5)
    println(out.toList)
    println(out2.toList)
  }
}



