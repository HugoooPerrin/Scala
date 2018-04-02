package week4

abstract class NInt {
  def + (that: Double): Double
  def + (that: Float): Float
  def + (that: Long): Long
  def + (that: Int): Int
}

/**
  * It is possible in scala to have the same method implemented several times.
  * The scala compiler will call the relevant one given the TYPE used !
  */