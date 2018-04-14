
object SynchronizedBlock extends App {

  class Account(private var amount: Int = 0) {

    private var x = new AnyRef {}

    private var uniqueID = 0
    def getUniqueID(): Long = x.synchronized {
      uniqueID = uniqueID + 1
      uniqueID
    }

    val id = getUniqueID()

    private def lockAndTransfert(target: Account, n: Int) = {
      this.synchronized {
        target.synchronized {
          this.amount -= n
          target.amount += n
        }
      }
    }

    def transfer(target: Account, n: Int) = {
      if (this.id < target.id) this.lockAndTransfert(target, n)
      else target.lockAndTransfert(this, -n)
    }
  }

  def startThread(a: Account, b: Account, n: Int) = {
    val t = new Thread {
      override def run(): Unit = {
        for (i <- 0 until n) {a.transfer(b, 1)}  // For-loop
      }
    }
    t.start()
    t
  }

  val a1 = new Account(500000)
  val a2 = new Account(700000)

  val t = startThread(a1, a2, 150000)
  val s = startThread(a2, a1, 150000)

  t.join()
  s.join()
}


