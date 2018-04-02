package week4

// Classification and access method
trait Expr {
  // Classification
  def isNumber: Boolean
  def isSum: Boolean
  // Accessor
  def numValue: Int
  def leftOp: Expr
  def rightOp: Expr
}

class Number(val numValue: Int) extends Expr {
  def isNumber = true
  def isSum = false
  def leftOp = throw new Error("Number.leftOp")
  def rightOp = throw new Error("Number.rightOp")
}

class Sum(val leftOp: Expr, val rightOp: Expr) extends Expr {
  def isNumber = false
  def isSum = true
  def numValue = throw new Error("Sum.numValue")
}

// Type cast version: forbidden

// OO version
trait Expr2 {
  def eval: Int
}

class Number2(n: Int) extends Expr2 {
  def eval = n
}

class Sum2(e1: Expr2, e2: Expr2) extends Expr2 {
  def eval = e1.eval + e2.eval
}