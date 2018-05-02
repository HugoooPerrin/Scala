/**
  * We previously saw builders. Combiners are parallel version of builder, their signature is
  *
  *   trait Combiner[T, Repr] extends Builder[T, Repr] {
  *     def combine(that: Combiner[T, Repr]: Combiner[T, Repr]
  *   }
  *
  *   Thus, in order to be efficient, the "combine function" needs to be running in a o(log(n) + log(m)) time.
  *
  *   Nut we can see that this is a difficult task !
  *     => There is no easy or intuitive method to do that.
  *     => So it is relevant to use the "two-phase construction" method.
  *
  *
  *   Two-Phase Construction:
  *     => The idea is to ues an intermediate data structure, different from the resulting data structure:
  *        that has an efficient combine method - o(log(n) + log(m)) or better
  *        that has an efficient += method
  *        that can be converted to the resulting data structure in o(n/P) time
  *
  *        (See ArrayCombiner) for example
  *
  *
  *
  *  Trees are good for parallelism only if they are balanced. If it is not the case then the workload
  *  will be unequal between the nodes !
  *   => Conc-tree data structure
  *
  */