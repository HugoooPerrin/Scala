package week2

class Rational(x: Int, y: Int) {

  // Necessary condition
  require(y != 0, "Denominator must be non zero")

  // Second constructor for special case
  def this(x: Int) = this(x, 1)

  // Exists only inside the class
  private def gcd(a: Int, b: Int): Int= if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)

  def numerator = x/g
  def denominator = y/g

  def unary_- = new Rational(-numerator, denominator)

  def + (that: Rational) =
    new Rational(
      this.numerator * that.denominator + that.numerator * this.denominator,
      this.denominator * that.denominator)

  def - (that: Rational) = this + -that

  def < (that: Rational) = this.numerator * that.denominator < that.numerator * this.denominator

  def max(that: Rational) = if (this < that) that else this

  override def toString = numerator + "/" + denominator
}
