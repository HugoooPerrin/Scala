val w = "Sibylle"

((w.toLowerCase() groupBy (c => c)) map {case (c, e) => (c, e.size)}).toList

// .toList.groupBy(c => c).mapValues(_.size).toList.sorted

val test = Map(1 -> 3, 2 -> 1)

test updated (3, 14)

//val op = test(3)

(1 to 5).toList

List(1,2,3) ::: List(7)

(for {
  num <- 1 to 4
  fruit <- List("apple", "orange")
} yield (num, fruit)).toList