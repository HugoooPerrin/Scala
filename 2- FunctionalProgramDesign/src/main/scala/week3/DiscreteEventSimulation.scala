/**
  * Simulation of digital circuits
  */

package DiscreteEventSimulation

object test extends App {

  object simulation extends Circuits with Parameters
  import simulation._

  val input1, input2, sum, carry = new Wire

  halfAdder(input1, input2, sum, carry)

  probe("sum", sum)
  probe("carry", carry)

  input1 setSignal true
  run()

  input2 setSignal true
  run()
}

