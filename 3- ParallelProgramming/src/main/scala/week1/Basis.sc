/**
  * Synchronization issue
  */

package parallel
import parallel.Parallel._

class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello")
    println("world!")
  }
}

def main(): Unit = {
  val thread1 = new HelloThread
  val thread2 = new HelloThread

  thread1.start()
  thread2.start()

  thread1.join()
  thread2.join()
}

// The two threads can arbitrarily overlap !

main()
main()
main()
main()

/**
  * Running in parallel
  */

//Pattern matching and recursive method
//
//import scala.math.pow
//
//def sumSegment(a: List[Int], p: Double, s: Int, t: Int): Double =  {
//
//  def sumSegmentInter(a: List[Int], p: Double, s: Int, t: Int, acc: Double): Double = a match {
//    case Nil => pow(acc, 1/p)
//    case head :: tail =>
//      if (s <= t-1) sumSegmentInter(tail, p, s+1, t, acc + pow(head,p))
//      else pow(acc, 1/p)
//  }
//
//  if (s >= t) throw new NoSuchElementException
//  else sumSegmentInter(a drop s, p, s, t, 0)
//
//}
//
//val list = List(1,2,3,4,5,6,7,8,9,10)
//sumSegment(list, 1, 0, 7)

// While loop alternative

import scala.math.{exp,log,floor}

def abs(x: Int): Int = if (x>0) x else -x

def power(x: Double, p: Double): Int = exp(p * log(abs(x))).toInt

def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  var i = s
  var sum: Int = 0
  while (i < t) {
    sum += power(a(i), p)
    i += 1
  }
  sum
}

def pNorm(a: Array[Int], p: Double): Int =
  power(sumSegment(a, p, 0, a.length), 1/p)

pNorm(Array(1,5,-2,8,9), 2)

// For two-threads parallelization

def parallelpNorm(a: Array[Int], p: Double): Int = {

  val m = floor(a.length/2).toInt

  val (sum1, sum2) = parallel(sumSegment(a, p, 0, m),
                              sumSegment(a, p, m, a.length))

  power(sum1 + sum2, 1/p)
}

// Recursive version for an unbounded number of threads
def pNormRec(a: Array[Int], p: Double): Double =
  power(segmentRec(a, p, 0, a.length, 10), 1/p)

def segmentRec(a: Array[Int], p: Double, s: Int, t: Int, threshold: Int): Double = {
  // Small segment, do it sequentially
  if (t-s < threshold) sumSegment(a, p, s, t)
  else {
    val m = s + (t-s)/2
    val (sum1, sum2) = parallel(segmentRec(a, p, s, m, threshold),
                                segmentRec(a, p, m, t, threshold))
    sum1 + sum2
  }
}


import org.scalameter._

val time = measure {
(0 until 1000000).toArray
}

println(s"Array initialization time : $time ms")