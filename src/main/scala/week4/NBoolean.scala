package week4

abstract class NBoolean {

  def ifThenElse[T](t: => T, e: => T): T

  def && (x: => NBoolean): NBoolean = ifThenElse(x, Nfalse)
  def || (x: NBoolean): NBoolean = ifThenElse(Ntrue, x)
  def unary_! : NBoolean = ifThenElse(Nfalse, Ntrue)

  def == (x: NBoolean): NBoolean = ifThenElse(x, x.unary_!)
  def != (x: NBoolean): NBoolean = ifThenElse(x.unary_!, x)

  def < (x: NBoolean): NBoolean = ifThenElse(Nfalse, x)
}

object Ntrue extends NBoolean {
  def ifThenElse[T](t: => T, e: => T): T = t
}

object Nfalse extends NBoolean {
  def ifThenElse[T](t: => T, e: => T): T = e
}
