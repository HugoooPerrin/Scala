object Comparator extends App {

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000000 + "ms")
    result
  }

  def sieve(s: Stream[Int]): Stream[Int] = s match {
    case head #:: Stream() => Stream(head)
    case head #:: tail => head #:: sieve(tail filter (_ % head != 0))
  }

  time{sieve((2 to 100000).toStream).toList}

// Python version (power of 10):
//  3 : 0.3 ms
//  4 : 2.4 ms
//  5 : 23 ms
//  6 : 0.26 s
//  7 : 3.1 s
//  8 : 33 s
}
