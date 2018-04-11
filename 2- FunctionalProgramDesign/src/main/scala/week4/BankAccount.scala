package frp

class BankAccount extends Publisher {

  private var balance = 0

  def currentBalance = balance

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
      publish()
//    println(this)
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()
//    println(this)
    } else throw new Error("Insufficient funds")
  }

  override def toString: String = balance + "euros"
}
