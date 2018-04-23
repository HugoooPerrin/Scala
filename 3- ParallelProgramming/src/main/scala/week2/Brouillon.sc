import util.Random.nextInt

/*val list = Stream.continually(nextInt(100)).take(10000).toList
list.length*/

val list2 = List.fill(100000)(100000).map(nextInt)

/**
  * scan is recording all the intermediate result of a fold operation
  */

List(1, 3, 8).scan(1)(_ + _)
List(1, 3, 8).foldLeft(1)(_ + _)
