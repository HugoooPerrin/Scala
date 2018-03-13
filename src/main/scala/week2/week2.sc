/*
Function that computes the sum from a to b of f(k)
 */
def sum(f: Int => Int, a: Int, b: Int): Int =
  if (a > b) 0
  else f(a) + sum(f, a+1, b)

def cube(x: Int) = x*x*x
def sumCube(a: Int, b: Int) = sum(cube, a, b)

sum(cube, 1, 4)
sumCube(1, 4)

/*
 Using anonymous functions
 */
sum((x: Int) => x*x*x, 1, 4)

/*
Tail recursive version, because linear recursion could lead to stackoverflow
if the difference between a and b was big
 */
def efficientSum(f: Int => Int, a: Int, b: Int): Int = {

  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop(a+1, acc+f(a))
  }
  loop(a,0)
}

efficientSum((x: Int) => x*x*x, 1, 4)

/*
Add of currying for more flexibility
 */
def UltimateSum(f: Int => Int): (Int, Int) => Int = {
  def result_f(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, acc + f(a))
    }
    loop(a,0)
  }
  result_f
}

def test = UltimateSum(x => x*x*x)
test(1,4)

def UltimateSumC(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, acc + f(a))
    }
    loop(a,0)
}

UltimateSumC(x => x*x*x)(1,4)

def megacube(a: Int, b: Int) = UltimateSumC(x => x*x*x)(a,b)
megacube(1,4)

def product(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 1
  else f(a) * product(f)(a+1, b)

def factorial(n: Int): Int = product(x=>x)(1,n)
factorial(5)

/*
Generalization
 */
def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int =
    if (a > b) acc
    else combine(f(a), mapReduce(f, combine, zero)(a+1, b))
  loop(a, zero)
}

mapReduce(x => x, (x, y) => x*y, 1)(1,5)

/*
Fixed point finder
 */
def fixedPoint(f: Double => Double)(firstGuess: Double, tolerance: Double) = {

  def abs(x: Double) = if (x < 0) -x else x

  def isCloseEnough(x: Double, y: Double, tolerance: Double): Boolean =
    abs((x - y) / x) / x < tolerance

  def iterate(guess: Double): Double = {
    val next = f(guess)
    if (isCloseEnough(guess, next, tolerance)) next
    else iterate(next)
  }
  iterate(firstGuess)
}

/*
square(x) is the fixed point of the function (y => x / y)
 */
def squareRoot(a: Double) =
  fixedPoint(y => (y + a / y) / 2)(1, 0.0001)

squareRoot(2)

def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

def sqrt(x: Double) =
  fixedPoint(averageDamp(y => x / y))(1, 0.0001)

sqrt(2)

/*
Class Rational
 */
import week2.Rational

val x = new Rational(1,2)
val y = new Rational(2,3)

x - y - y
