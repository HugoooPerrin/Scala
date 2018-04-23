package parallel

object MonteCarloPi extends App {

  import common._
  import scala.util.Random
  import scala.math.floor

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

  def monteCarloPiPar(iter: Int, numTask: Int): Double = {
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

  val seqtime = time{
    monteCarloPiSeq(10000000)
  }
  println("sequential time:" + seqtime._2)

  val partime = time{
    monteCarloPiPar(10000000)
  }

  println(s"parallel time:" + partime._2)

  println(s"speedup: ${seqtime._2.toFloat / partime._2}")

}
