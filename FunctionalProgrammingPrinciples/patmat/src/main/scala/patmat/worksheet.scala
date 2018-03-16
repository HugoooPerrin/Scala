object PatterMatching extends App {

  import patmat.Huffman._

  val sampleTree = makeCodeTree(
    makeCodeTree(Leaf('x', 1), Leaf('e', 1)),
    Leaf('t', 2)
  )

  println("\nWeight:" + weight(sampleTree))
  println("\nCharacter:" + chars(sampleTree))
  println("\nTimes:" + times(List('a', 'c', 'b', 'a', 'a', 'b')))
  println("\nOrdered:" + makeOrderedLeafList(times(List('a', 'c', 'b', 'a', 'a', 'b'))))

}
