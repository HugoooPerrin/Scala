package reductions

import scala.annotation._
import org.scalameter._
import common._
import math.{max, min}

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 1000000
    val chars = new Array[Char](length)
    val threshold = 600000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {

    def balanced(chars: List[Char], OpenedParenthesis: Int): Boolean =
      if (OpenedParenthesis < 0) false
      else if (chars.isEmpty) OpenedParenthesis == 0
      else if (chars.head == '(') balanced(chars.tail, OpenedParenthesis+1)
      else if (chars.head == ')') balanced(chars.tail, OpenedParenthesis-1)
      else balanced(chars.tail, OpenedParenthesis)

    balanced(chars.toList,0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(from: Int, until: Int, a1: Int): (Int, Int) = {

      var opened = a1

      for (i <- from until until) {
        if (chars(i) == '(') opened += 1
        if (chars(i) == ')') opened -= 1
      }

      (max(0, opened), -min(0, opened)) // (opened, closed)
    }

    def reduce(from: Int, until: Int, threshold: Int): (Int, Int) = {
      if (until - from < threshold) traverse(from, until, 0)
      else {
        val mid = from + until / 2
        val (pair1, pair2) = parallel(reduce(from, mid, threshold),
                                      reduce(mid, until, threshold))

        val (opened1, closed1) = pair1
        val (opened2, closed2) = pair2

        val opened = opened1 + opened2 - closed2
        val closed = closed2 + closed1 - opened1
        (max(0, opened), max(0, closed)) // (opened, closed)
      }
    }

    reduce(0, chars.length, threshold) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
