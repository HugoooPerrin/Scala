import frp._

val a = new BankAccount
val b = new BankAccount
val c = new Consolidator(List(a, b))