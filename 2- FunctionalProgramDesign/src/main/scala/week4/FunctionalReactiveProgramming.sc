/**
  * Variable Signals
  */

//val myVar1 = Signal(72)
//var sig = Var(0)
//sig.update(5)
//
//// Updating (similar to reactive values in Rshiny)
//val v1 = myVar1()
//myVar1() = v1 + 2

/*
The important with Signals is that when one is updated, all the Signals linked to it are
automatically re evaluated !
 */

val weird = List(4, "kkgg")


import scala.util.DynamicVariable


def f = new DynamicVariable()

trait Socket {
  def readFromMemory(): Array[Byte]
  def sendToEurope(packet: Array[Byte]): Array[Byte]
}

val socket = new Socket()
val packet = socket.readFromMemory()
val confirmation = socket.sendToEurope(packet)

/**
  * Future monad: handles failure and latency
  *
  * Callback: signal when the execution is done (or failed)
  */
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

trait Future[T] {
  def onComplete(callBack: Try[T] => Unit)
}

// Socket becomes
trait Socket2 {
  def readFromMemory(): Future[Array[Byte]]
  def sendToEurope(packet: Array[Byte]): Future[Array[Byte]]
}

val socket2 = new Socket2()
val packet2 = socket2.readFromMemory()
val confirmation2 = {
  packet2.onComplete match {
    case Success(p) => socket2.sendToEurope(p)
    case Failure(t) => ???
  }
}