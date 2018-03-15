/**
  *   PatternMatching
  */
import PatternMatching._

// Pattern matching version
Sum(Sum(Number(7), Number(4)), Number(9)).eval

// This work on console but not in worksheet
Number(7).show
Sum(Sum(Number(7), Number(4)), Number(9)).show


/**
  *   List
  */

// Complexity of the insertion sort method: n x n
def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => {
    if (x > y) y :: insert(x, ys)
    else x :: y :: ys
  }
}

def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y, isort(ys))
}

val list = List(2,8,5,6,9,3,1,1,7)

val sortedList = isort(list)
