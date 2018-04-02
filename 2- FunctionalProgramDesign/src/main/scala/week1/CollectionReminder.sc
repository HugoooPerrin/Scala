/**
Subclassing functions
 */
trait Map[Key, Value] extends (Key => Value)

/**
Partial functions
  */
val f: PartialFunction[List[Int], String] = {
  case Nil => "One"
  case x :: y :: tail => "Two"
}

f.isDefinedAt(List(3))

//val g: PartialFunction[List[Int], String] = {
//  case Nil => "One"
//  case x :: tail =>
//    tail match {
//      case Nil => "two"
//    }
//}
//
//g.isDefinedAt(List(1,2,3))

// But g(List(1,2,3)) gives a match error
/*
isDefinedAt only looks at the "highest" pattern matching
 */

/**
  For-tye queries
  */
//for {
//  i <- 1 until n    // FlatMap
//  j <- 1 until j
//  if isPrime(i+j)   // Filter
//} yield (i,j)       // Map

/*
withFilter is kind of a smarter version of filter that doesn't
apply directly the filter but keep in mind to do it when the
next function will be called (map, flatMap, etc...)
 */

/**
  Random value generators
  */
import java.util.Random
val rand = new Random

rand.nextInt()

trait Generator[+T] {
  self =>                // alias for this

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  def generate = rand.nextInt()
}

//val pairs = new Generator[(Int, Int)] {
//  def generate = (integers.generate, integers.generate)
//}
//
//val booleans = new Generator[Boolean] {
//  def generate = integers.generate > 0
//}
//
//booleans.generate

// With for
val booleansFor = for (x <- integers) yield x > 0

def pairsFor[T, U](t: Generator[T], u: Generator[U]) = for {
  x <- t
  y <- u
} yield (x,y)