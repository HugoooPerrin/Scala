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

    val pi = (1 to numTask).map(_ => numTask)
    val tasks = pi.map(x => task { mcCount(iter/x) })

    4.0*(tasks.map(_.join()).sum)/iter
  }

  val seqtime = time{
    monteCarloPiSeq(100000000)
  }
  println("sequential time : " + seqtime._2)

  // Optimal value: number of available threads
  val partime = time{
    monteCarloPiPar(100000000, 4)
  }

  println(s"parallel time : " + partime._2)

  println(s"speedup: ${seqtime._2.toFloat / partime._2}")
}
