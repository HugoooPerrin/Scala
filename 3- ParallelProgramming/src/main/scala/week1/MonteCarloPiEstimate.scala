package parallel

object MonteCarloPi extends App {

  import common._
  import scala.util.Random
  import scala.math.floor
  import org.scalameter._

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 50,
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

  def monteCarloPiPar(iter: Int, numTask: Int): Double = {

    val pi = (1 to numTask).map(_ => numTask)
    val tasks = pi.map(x => task { mcCount(iter/x) })

    4.0*(tasks.map(_.join()).sum)/iter
  }

  // Sequential computation
  val seqtime = standardConfig measure { monteCarloPiSeq(100000000) }
  println(s"sequential time : $seqtime")

  // Optimal value: number of available threads
  val partime = standardConfig measure { monteCarloPiPar(100000000, 4) }
  println(s"Parallel time : $partime")

//  println(s"speedup: ${seqtime / partime}")
}
