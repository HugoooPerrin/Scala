abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int) : Boolean
  def union(other: IntSet): IntSet
}

object Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  def union(other: IntSet): IntSet = other

  override def toString: String = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this

  def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem

  override def toString: String = "{" + left + elem + right + "}"
}

val t1 = new NonEmpty(5, Empty, Empty)
val t2 = t1 incl 3 incl 4 incl 7 incl 2

t1 contains 4
t2 contains 4