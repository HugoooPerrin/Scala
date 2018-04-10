package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    priority <- arbitrary[A]
    heap <- frequency((1, Gen.const(empty)), (9, genHeap))
  } yield insert(priority, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen") = forAll { (heap1: H, heap2: H) =>
    def heapEqual(heap1: H, heap2: H): Boolean =
      if (isEmpty(heap1) && isEmpty(heap2)) true
      else {
        val m1 = findMin(heap1)
        val m2 = findMin(heap2)
        m1 == m2 && heapEqual(deleteMin(heap1), deleteMin(heap2))
      }
    heapEqual(meld(heap1, heap2),
      meld(deleteMin(heap1), insert(findMin(heap1), heap2)))
  }
}
