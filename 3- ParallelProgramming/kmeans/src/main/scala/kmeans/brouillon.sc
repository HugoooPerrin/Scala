import scala.collection._
import math.floor

val points = GenSeq(12, 45, 44, 59, 68, 16)
def findClosest(i: Int): Int = {
  (floor(i / 10)*10).toInt
}

val means = GenSeq(10, 20, 30, 40, 50,60)

val clusters = points.groupBy(findClosest(_))

means.map(mean => mean -> clusters.getOrElse(mean, GenSeq())).toMap


val list = List(1, 5, 9, 8 , 10, 12)

list.forall(_ > 0)

val list2 = List(1, 5, 9, 8 , 10, 12)

list == list2