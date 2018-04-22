/**
  * Parallel programming assumption:
  *
  * => multicore or multiprocessor systems with shared memory.
  *
  * Important ideas:
  *
  * => Multitasking:
  *       The OS can launch several processes running at the same time.
  *       But a process cannot access the memory of another, they are
  *       isolated.
  *
  * => Thread (!= CPU):
  *       More easy to manipulate than processes, and the communication
  *       between different thread is easier since they share memory.
  *
  * => Atomicity:
  *       Property qualifying anf operation if that one occurs instantaneously
  *       from the point of view of other threads.
  *
  * => Deadlock:
  *       Scenario in which two or more threads compete for resources and
  *       wait for each to finish without releasing the already
  *       acquired resources.
  *
  *       Happen when no order is set between thread locking
  *
  * => Benchmarking:
  *       Compare and measure parallelization performance
  */