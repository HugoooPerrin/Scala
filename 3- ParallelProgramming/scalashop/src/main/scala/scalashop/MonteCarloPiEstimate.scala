package parallel

object MonteCarloPi extends App {

  import common._
  import scala.util.Random
  import scala.math.floor
  import org.scalameter._

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 20,
    Key.exec.benchRuns -> 10,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

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
    val (pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8) =
      parallel(mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(floor(iter/8).toInt),
                mcCount(iter - 7*floor(iter/8).toInt))
    4.0*(pi1 + pi2 + pi3 + pi4 + pi5 + pi6 + pi7 + pi8)/iter
  }

  val seqtime = standardConfig measure {
    monteCarloPiSeq(10000000)
  }
  println(s"sequential time: $seqtime ms")

  val partime = standardConfig measure {
    monteCarloPiPar(10000000)
  }
  println(s"parallel time: $partime ms")

  println(s"speedup: ${seqtime / partime}")

}
