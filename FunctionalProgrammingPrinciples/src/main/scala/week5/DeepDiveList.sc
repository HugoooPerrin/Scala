// Sublists and element access
val xs = List(1,2,3,4,5,6,7,8,9,10)

xs.head
xs.tail
xs.length
xs.last
xs.init
xs take 15
xs take 7
xs drop 3
xs drop 11
xs(8)
// xs(17)

// Creating new lists
val ys = List(11,12,13,14,15)

xs ++ ys
val zs = xs ::: ys
0 :: zs
xs.reverse
xs updated (5, 5)
val (first, second) = xs splitAt 7

// Finding elements
xs indexOf 5 // first element to appear
xs indexOf 45
xs contains 18

// Remove
def removeAt[T](n: Int, xs: List[T]): List[T] =
  (xs take n) ++ (xs drop n+1)

removeAt(2, xs)

def flatten(xs: List[Any]): List[Any] = xs match {
  case Nil => Nil
  case (head: List[Any]) :: tail => flatten(head) ++ flatten(tail)
  case head :: tail => head :: flatten(tail)
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))

// Merge sort (more efficient than insertion sort)
import math.Ordering

def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (ord.lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
    val (first, second) = xs splitAt n
    merge(msort(first), msort(second))
  }
}

val num = List(2,5,3,1,9,8,7,2,6,4,1,3,5)
val string = List("bonjour", "sibylle", "septmois", "afond")

msort(num) // msort(num)(Ordering.Int)
msort(string) // msort(string)(Ordering.String)

// High order list functions
def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
  case Nil => Nil
  case y :: ys => y * factor :: scaleList(ys, factor)
}

def mapScaleList(xs: List[Double], factor: Double): List[Double] = {
  xs map (x => x * factor)
}

def squareList(xs: List[Int]): List[Int] = xs match {
  case Nil => Nil
  case head :: tail => head * head :: squareList(tail)
}

def mapSquareList(xs: List[Int]): List[Int] = {
  xs map (x => x*x)
}

def posElem(xs: List[Int]): List[Int] = xs match {
  case Nil => Nil
  case head :: tail =>
    if (head > 0) head :: posElem(tail)
    else posElem(tail)
}

def filterPosElem(xs: List[Int]): List[Int] = {
  xs filter (x => x > 0)
}

xs filter (x => x > 3)
xs partition (x => x > 3)

xs takeWhile (x => x < 5)
xs dropWhile (x => x < 5)
xs span (x => x < 5)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case head :: tail =>
    val (first, rest) = xs span (x => x == head)
    first :: pack(rest)
}

pack(List("a", "a", "a", "b", "c", "c", "a"))

def encode[T](xs: List[T]): List[(T, Int)] = {
  val packed = pack(xs)

  def encodeInter[T](ys: List[List[T]]): List[(T, Int)] = ys match {
    case Nil => Nil
    case head :: tail =>
      (head.head, head.length) :: encodeInter(tail)
  }

  encodeInter(packed)
}

def mapEncode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (x => (x.head, x.length))
}

encode(List("a", "a", "a", "b", "c", "c", "a"))

mapEncode(List("a", "a", "a", "b", "c", "c", "a"))

// Fold operations
xs.product
xs reduceLeft ((x, y) => x * y)
(xs foldLeft 1) (_ * _) // 1 is the accumulator

def concat[T](xs: List[T], ys: List[T]): List[T] =
  (xs foldRight ys) (_ :: _)

1 :: 2 :: 3 :: List(4,5,6) // Step by step in concat

concat(List(1,2,3), List(4,5,6))

import math.pow
pow(2,4)

// Structural induction
// Show that Property P(Nil) hold and that for all list xs
// and element x then P(xs) => P(x :: xs)
// RECURRENCE

