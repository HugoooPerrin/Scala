import week3._

// Set of integers
val t1 = new NonEmpty(5, Empty, Empty)
val t2 = t1 incl 3 incl 4 incl 7 incl 2

t1 contains 4
t2 contains 4

// Polymorphism: subtyping and generic
def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])

singleton[Int](5)
singleton(5)

singleton[Boolean](true)

def index[T](n: Int, list: List[T]): T = {
  if (list.isEmpty) throw new IndexOutOfBoundsException
  else if (n == 0) list.head
  else index[T](n-1, list.tail)
}


val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))
index(2, list)