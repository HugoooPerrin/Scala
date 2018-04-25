import util.Random.nextInt

/*val list = Stream.continually(nextInt(100)).take(10000).toList
list.length*/

val list2 = List.fill(100000)(100000).map(nextInt)

/**
  * scan is recording all the intermediate result of a fold operation
  */

List(1, 3, 8).scan(1)(_ + _)
List(1, 3, 8).foldLeft(1)(_ + _)

import math.{pow, abs}

def norm(a: Array[Float], p: Int): Double = {
  pow(a.map(x => pow(abs(x), p)).reduce(_ + _), 1/p)
}

def f(p1: Tuple2[Float, Int], p2: Tuple2[Float, Int]): Tuple2[Float, Int] = {
  (p1._1 + p2._1, p1._2 + p2._2)
}

def average(xs: List[Float]): Float = {
  val (sum, length) = xs.map(x => (x,1)).reduce(f)
  sum.toFloat/length
}

average(List(4,8,5,6,9,5,8))

def scanLeftImmutable(xs: List[Int], f: (Int, Int) => Int, a0: Int): List[Int] = xs match {
  case Nil => throw new Error("Empty list")
  case x :: Nil => List(f(a0, x))
  case head :: tail =>
    val a = f(a0, head)
    a :: scanLeftImmutable(tail, f, a)
}

def fun(x: Int, y: Int): Int = x+y
val a0 = 100
val array = Array(1, 3, 8)
val list = List(1, 3, 8)
val out = new Array[Int](array.length+1)

scanLeftImmutable(list, fun, a0)

val t1 = Array(1, 7, 8, 9)
val t2 = Array(7, 8, 6, 1)

t1 ++ t2