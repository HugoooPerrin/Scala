/**
 * flatMap reminder
 */
val list = List(1,2,3,4)
def f(x: Int): Int = x*2

list map f
list flatMap (x => List(f(x)))
list map (x => List(x*2)) flatten
git

/**
  * A monad M is a parametric type M[T] with two operations:
  *
  *     => flatMap and unit
  */
trait M[T] {
  def flatMap[U](f: T => M[U]): M[U]

  def unit[T](x: T): M[T]
}
// List is a model where unit(x) = List(x)
// Set is a monad where unit(x) = Set(x)

/*
A monad is a type that satisfy three laws:
- Associativity
- Left unit
- Right unit

Since for-Expressions have strong links with flatMap
the three laws still apply in them !
 */

abstract class Try[+T]

case class Success[T](x: T)        extends Try[T]
case class Failure(ex: Exception) extends Try[Nothing]

// Use: Try(expression)

