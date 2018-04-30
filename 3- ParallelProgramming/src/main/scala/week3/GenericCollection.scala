import scala.collection._

/* Generic collection traits allow us to write code that is unaware of parallelism:
 it may or may not run in parallel*/

// No need to add ".par" in the code, only to the input data

object Generic extends App {

  def time[R](block: => R): (Long, R) = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    val time = (t1 - t0)/1000
    (time, result)
  }

  def largestPalindrome(xs: GenSeq[Int]): Int = {
    xs.aggregate(Int.MinValue)(
      (largest, n) =>
        if (n > largest && n.toString == n.toString.reverse) n else largest,
      math.max
    )
  }

  val array = (0 until 999999).toArray
  val arrayPar = (0 until 999999).toArray.par

  // We can call the function with both a sequential array and a parallel array (2x faster)
  val measure = time {largestPalindrome(array)}
  val measurePar = time {largestPalindrome(arrayPar)}

  println(s"Speedup: ${measure._1.toFloat/measurePar._1}")
}

