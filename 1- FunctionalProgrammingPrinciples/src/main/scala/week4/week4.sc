import week4._

// Boolean from scratch
Ntrue == Nfalse
Ntrue != Nfalse
Ntrue && Ntrue

// Natural
val t = new Succ(Zero).successor

// Decompostion
def eval(e: Expr): Int = {
  if (e.isNumber) e.numValue
  else if (e.isSum) eval(e.leftOp) + eval(e.rightOp)
  else throw new Error("Unknown expression" + e)
}

// Classification and access method
eval(new Sum(new Sum(new Number(7), new Number(4)), new Number(9)))

// OO version method
new Sum2(new Sum2(new Number2(7), new Number2(4)), new Number2(9)).eval