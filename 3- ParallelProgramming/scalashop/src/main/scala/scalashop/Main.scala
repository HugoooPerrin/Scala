package scalashop

import java.awt._
import java.awt.event._
import javax.swing._
import javax.swing.event._
import scala.collection.parallel._
import scala.collection.par._
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import org.scalameter._
import common._

object TestPerso extends App {

  val computation = task {
    val result = 1 + 1
    println("Done1!")
    result
  }

  val computation2 = {
    val result = 1 + 3
    println("Done2!")
    result
  }

  println("About to wait for some heavy calculation...")

  computation.join()

  println(computation)
  println(computation2)

}
