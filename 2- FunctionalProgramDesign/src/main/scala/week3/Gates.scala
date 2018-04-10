package DiscreteEventSimulation

// Second class

abstract class Gates extends Simulation {

  def inverterDelay: Int
  def andGateDelay: Int
  def orGateDelay: Int

  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()
    def getSignal: Boolean = sigVal
    def setSignal(s: Boolean): Unit = {
      if (s != sigVal) {
        sigVal = s
        actions foreach (_())
      }
    }
    def addAction(a: Action): Unit = {
      actions = a :: actions
      a()
    }
  }

  def inverter(input: Wire, output: Wire): Unit = {
    def inverterAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(inverterDelay) {output setSignal !inputSig}
    }
    input addAction inverterAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(andGateDelay) {output setSignal (a1Sig & a2Sig)}
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  def orGate(o1: Wire, o2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      afterDelay(orGateDelay) {output setSignal (o1Sig | o2Sig)}
    }
    o1 addAction orAction
    o2 addAction orAction
  }


  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name $currentTime value = ${wire.getSignal}")
    }
    wire addAction probeAction
  }
}