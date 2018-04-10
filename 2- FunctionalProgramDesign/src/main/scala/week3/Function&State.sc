def iterate(n: Int, f: Int => Int, x: Int): Int =
  if (n == 0) x else iterate(n-1, f, f(x))

def square(x: Int) = x*x

iterate(1, square, 3)

/**
  * All different orders of rewriting the previous call in the
  * substitution model end up to the same solution.
  *
  * That is an important result of lambda calculus, the theory
  * behind functional programming !
 */

// New type of mutable objects: state/value depends on history
var count = 10
count = count + 1

// Bank account example
class BankAccount {

  private var balance = 0

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
    println(this)
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      println(this)
    } else throw new Error("Insufficient funds")
  }

  override def toString: String = balance + "euros"
}

val BNP = new BankAccount
BNP deposit 1000
//println(BNP)
BNP withdraw 450
//println(BNP)

/**
  * It is important not to be confused between
  * For-expressions (for () yield)
  * For-loops (for () command)
  */

for {
  i <- 1 to 3
  j <- "abc"
} println(i+j)

var i = 0
do {
  i = i+1
  println(i)
} while (i < 20)

