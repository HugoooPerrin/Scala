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
  * Solution: Call-by-name evaluation (Stream)
  * (Avoid computing the tail of a sequence until it is needed for the
  * evaluation result)
  */

def time[R](block: => R): R = {
  val t0 = System.nanoTime()
  val result = block    // call-by-name
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0)/1000 + "ms")
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

// AWESOME

/**
  * One last performance issue in Call-by-value is that if tail is called
  * several times, the corresponding stream will be recomputed each time.
  *
  * Solution: Lazy evaluation
  * (store the results at each computation step !)
  */
def expr = {
  val x = {println("x"); 1}
  lazy val y = {println("y"); 2}
  def z = {println("z"); 3}
  z + y + x + z + y + x
}

expr

/**
  * Laziness allows to compute operation on infinite collections
  */

// All integers starting from n
// If not lazy would return an infinite loop
def from(n: Int): Stream[Int] = n #:: from(n+1)

val naturals = from(0)
val fourMultiple = naturals map (_ * 4)

naturals(5)
fourMultiple(5)

/**
  * The sieve of Eratosthenes
  */
def sieve(s: Stream[Int]): Stream[Int] = s match {
  case head #:: Stream() => Stream(head)
  case head #:: tail => head #:: sieve(tail filter (_ % head != 0))
}


val primeNumbers = sieve(from(2))

primeNumbers.take(1000).toList
primeNumbers(1000)


/**
  * With streams we can now express the concept of
  * sequence without having to worry about when to
  * terminate it
  */
def sqrtStream(x: Double): Stream[Double] = {
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
  guesses
}

sqrtStream(4).take(10).toList

val N = 9
time{(from(1) map (_ * N)).take(1000).toList}        // Much faster
time{(from(1) filter (_ % N == 0)).take(1000).toList}