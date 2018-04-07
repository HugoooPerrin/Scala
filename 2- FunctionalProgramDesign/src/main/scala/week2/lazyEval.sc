/**
  * Immutable collections provide powerful operations, in particular
  * for combinatorial search.
  *
  * Example: find the 2nd prime number between 1000 and 10000
  *
  *       ((1000 to 10000) filter isPrime)(1)
  *
  * This is much shorter than the recursive alternative but there is
  * an efficiency issue since we compute all the prime numbers when
  * we only want to keep the second !
  *
  * Solution: Lazy evaluation (Stream)
  * (Avoid computing the tail of a sequence until it is needed for the
  * evaluation result)
  */

def time[R](block: => R): R = {
  val t0 = System.nanoTime()
  val result = block    // call-by-name
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
  result
}
def isPrime(n: Int): Boolean = {
  (2 until n) forall (d => n % d != 0)
}

val s1 = Stream(1,2,3,4)
val s2 = (1 to 1000).toStream

// Comparison with list
def streamRange(lo: Int, hi: Int): Stream[Int] = {
  println(lo + "\n")
  if (lo >= hi) Stream()
  else lo #:: streamRange(lo+1, hi)
}

def listRange(lo: Int, hi: Int): List[Int] = {
  println(lo + "\n")
  if (lo >= hi) Nil
  else lo :: listRange(lo+1, hi)
}

listRange(1,10).take(3)
streamRange(1, 10).take(3).toList

// Streams support almost all methods of List, so:
time{((1000 to 10000) filter isPrime)(1)}
// becomes:
time{((1000 to 10000).toStream filter isPrime)(1)}


