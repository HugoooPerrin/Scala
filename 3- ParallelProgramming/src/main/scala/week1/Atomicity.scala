/**
  * Atomicity importance
  */

object NoAtomicity extends App {

  // We can see overlapping

  private var uniqueID = 0
  def getUniqueID(): Long = {
    uniqueID = uniqueID + 1
    uniqueID
  }

  def startThread() = {
    val t = new Thread {
      override def run(): Unit = {
        val uIDs = for (i <- 1 until 10) yield getUniqueID()
        println(uIDs)
      }
    }
    t.start()
    t
  }

  startThread()
  startThread()
}

object Atomicity extends App {

  private var x = new AnyRef {}

  private var uniqueID = 0
  def getUniqueID(): Long = x.synchronized {
    uniqueID = uniqueID + 1
    uniqueID
  }

  def startThread() = {
    val t = new Thread {
      override def run(): Unit = {
        val uIDs = for (i <- 1 until 10) yield getUniqueID()
        println(uIDs)
      }
    }
    t.start()
    t
  }

  startThread()
  startThread()
}


