package parallel

object MonteCarloPi extends App {

  import scala.util.Random
  import scala.math.floor
  import parallel.Parallel._

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000000 + "ms")
    result
  }

  def mcCount(iter: Int): Int = {
    val randomX = new Random
    val randomY = new Random
    var hits = 0
    for (i <- 0 until iter) {
      val x = randomX.nextDouble
      val y = randomY.nextDouble
      if (x*x + y*y < 1) hits += 1
    }
    hits
  }

  def monteCarloPiSeq(iter: Int): Double = 4.0*mcCount(iter)/iter

  def monteCarloPiPar(iter: Int): Double = {
    val ((pi1, pi2), (pi3, pi4)) = parallel(
      parallel(mcCount(floor(iter/4).toInt), mcCount(floor(iter/4).toInt)),
      parallel(mcCount(floor(iter/4).toInt), mcCount(iter - 3*floor(iter/4).toInt))
    )
    4.0*(pi1 + pi2 + pi3 + pi4)/iter
  }

  println(time{monteCarloPiSeq(100000000)})
}
