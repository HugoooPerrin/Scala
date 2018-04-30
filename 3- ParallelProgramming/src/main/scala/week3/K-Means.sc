/**
  *
  * K-means is an example of a bulk synchronous parallel algorithm (BSP). BSP algorithms are composed from a sequence of supersteps, each of which contains:

        - parallel computation, in which processes independently perform local computations and produce some values
        - communication, in which processes exchange data
        - barrier synchronisation, during which processes wait until every process finishes

      => Data-parallel programming models are typically a good fit for BSP algorithms, as each bulk synchronous phase can correspond to some number of data-parallel operations.
  */