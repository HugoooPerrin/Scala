
val terrain = Vector(Vector('S', 'T'), Vector('o', 'c'), Vector('o', 'o'))

val row = terrain indexWhere (_ contains 'c')
val col = terrain(row) indexOf 'c'

val list = (for (x <- 1 to 5; y <- 6 to 10) yield (x, List(y))).toStream
val explored = 4

list filter (_._1 > 6)

val s1 = Stream(1,2,5)
val s2 = Stream(4,8,9)

(s1 ++ s2).toList