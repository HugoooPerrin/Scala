package parallel

/**
  * Function that compute two tasks on different threads.
  *
  * Call-by-name is essential in parallel programming,
  * else all is computed sequentially
  */


object Parallel {

  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = ???

}

