import scala.collection.GenSeq

/**
  * => Task-parallel programming:
  *
  *       A form of parallelization that distributes execution processes across
  *       computing nodes.
  *       (we used "task" and "parallel" constructs)
  *
  * => Data-parallel programming:
  *
  *       A form of parallelization that distributes data across computing nodes.
  *       (parallel for-loop)
  *
  *
  * => Workload:
  *
  *       Function that maps each input element to the amount of work required
  *       to process it.
  *
  *
  * => Data-parallel scheduler:
  *
  *       Efficiently balance the workload across processors without any knowledge
  *       about it.
  *
  *
  *
  * => Comparison:
  *
  *     Data-parallelization is generally faster than task-parallelization because there is
  *     a better handling of workload.
  */

/*The parallel for-loop doesn't give a result, it only interacts through side-effects
Be careful of deadlock then !
(Not very functional)*/

def initializeArray(xs: Array[Int])(v: Int): Unit = {
  for (i <- (0 until xs.length).par) {
    xs(i) = v
  }
}

// Mandlebrot Set: non diverging sequence with Zn+1 = Zn**2 + C

/*def color(i: Int) = ???
def coordinatesFor(i: Int) = ???

val image = new Array[Int](1000)
val maxIterations = 100

private def computePixel(xc: Double, yc: Double, maxIterations: Int): Int = {
  var i = 0
  var x, y = 0.0
  while (x * x + y * y < 4 && i < maxIterations) {
    val xt = x * x - y * y + xc
    val yt = 2 * x * y + yc
    x = xt
    y = yt
    i += 1
  }
  color(i) // Turn a Int into a 32bytes color
}

def parRender(): Unit = {
  for (idx <- (0 until image.length).par) {                     // Parallel for-loop
    val (xc, yc) = coordinatesFor(idx)
    image(idx) = computePixel(xc, yc, maxIterations)
  }
}

// Parallel collections
(1 until 1000).par
      .filter(n => n % 3 == 0)
      .count(n => n.toString == n.toString.reverse)

// Non-parallelizable operations
def sum(xs: Array[Int]): Int = {
  xs.par.foldLeft(0)(_ + _)         // Doesn't run in parallel
}*/

/*
Operation foldRight, foldLeft, reduceRight, reduceLeft, scanRight and scanLeft can process
only sequentially !

They are of type (A, B) => A

Whereas fold method is of type (A, A) => A so we can use the reduction tree trick
we saw in previous class (if reduction function is associative)
*/

// Use cases: fold and similar operations
def sumPar(xs: Array[Int]): Int = {
  xs.par.fold(0)(_ + _)
}

def max(xs: Array[Int]): Int = {
  xs.par.fold(Int.MinValue)(math.max)
}

// The aggregate operation: combination of fold and foldLeft
/*
def isVowel(c: Char) = ???

Array('e', 'p', 'f', 'i', 'l').par.aggregate(0)((count, c) => if (isVowel(c)) count + 1 else count, _+_)
*/

// Computing set intersection
import scala.collection._

def intersection(a: GenSet[Int], b: GenSet[Int]): mutable.Set[Int] = {
  val result = mutable.Set[Int]()
  for (x <- a) if (b contains x) result += x
  result
}

intersection((0 until 1000).toSet, (0 until 1000 by 4).toSet).size
intersection((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet).size // Not deterministic, there can be corruption of internal state because of thread concurrent access

/**
  * Avoid modify to the same memory locations without proper synchronization
  */
import java.util.concurrent._

/*def intersectionConcurrent(a: GenSet[Int], b: GenSet[Int]): mutable.Set[Int] = {
  val result = new ConcurrentSkipListSet[Int]()
  for (x <- a) if (b contains x) result += x // ?
  result
}*/

// Assumed to work correctly

/*
Using Splitters to implement fold method
 */
val remaining = ???
val threshold = ???

def fold[A](z: A)(f: (A, A) => A): A = {
  if (remaining < threshold) foldLeft(z)(f)  // Sequential alternative
  else {
    val children = for (child <- split) yield task { child.fold(z)(f) }
    children.map(_.join()).foldLeft(z)(f)
  }
}

/*
Using builder to implement filter method
 */
def filter[T](p: T => Boolean): Repr = {
  val b = newBuilder
  for (x <- this) if (p(x)) b += x
  b.result
}

// Combiner is the parallel version of Builder

// Split and combine are both efficient method (of complexity o(log(n)) or better