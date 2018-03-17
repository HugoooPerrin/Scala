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