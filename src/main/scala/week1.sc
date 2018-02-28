/*
Absolute number
 */
def abs(x: Double) = if (x < 0) -x else x
abs(-5)

/*
Compute a recursive square root function
=> Nested version
 */
def sqrt(x: Double) = {

  def sqrtIter(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  def isGoodEnough(guess: Double, x: Double) =
    abs(guess * guess - x) < 0.0001 * x

  def improve(guess: Double, x: Double) =
    (guess + x / guess) / 2

  sqrtIter(1.0, x)
}

sqrt(36)

/*
Blocks comprehension:
definitions inside a block shadow
definitions of the same names
outside the block
 */
val x = 0

def f(y: Int) = y + 1

val result = {
  val x = f(3)
  x * x
}

x
/*
Cleaner version of square roots using
blocks comprehension
 */
def sqrt_clean(x: Double) = {

  def sqrtIter(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else sqrtIter(improve(guess))

  def isGoodEnough(guess: Double) =
    abs(guess * guess - x) < 0.0001 * x

  def improve(guess: Double) =
    (guess + x / guess) / 2

  sqrtIter(1.0)
}

sqrt(49)

/*
If a function's last action is to call it self
then it is a TAIL RECURSIVE function
 */

// Greatest common divisor of two numbers
def gcd(a: Int, b: Int): Int =
  if (b == 0) a else gcd(b, a % b)

gcd(110, 33)

// Factorial (not tail recursive version)
def factorial(n: Int): Int =
  if (n == 0) 1 else n * factorial(n - 1)

factorial(4)

// Factorial (tail recursive version)
def rec_factorial(n: Int): Int = {

  def loop(acc: Int, k: Int): Int =
    if (k == 0) acc
    else loop(acc * k, k - 1)

  loop(1, n)
}

rec_factorial(4)
