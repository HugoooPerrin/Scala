// Lists are linear, access to the first element is much faster
// than access to the middle or the end of a list

// Vectors have more evenly balanced access pattern
// Seems to be way more efficient for access but also for bulk operations (map, reduce, fold)

// List are more efficient for recursive structures models only

val xs = Vector(1,2,3,4,5)
0 +: xs

val zs = Array(1,2,3,4,5)
zs map (x => x*2)

val ys: String = "Hello World!"
ys filter (_.isUpper)

val r: Range = 1 to 5 by 2
r map (x => x*2)

// Main sequence operations
xs exists (x => x <= 5)
xs contains 5
xs forall (x => x == 5)

val pairs = List(1,2,3) zip ys
val unpair = pairs.unzip

ys flatMap (c => List('.', c))

(1 to 10) flatMap (x => (1 to 5) map (y => (x,y)))

def scalarProduct(xs : Vector[Double], ys: Vector[Double]): Double = {
  (xs zip ys).map(xy => xy._1 * xy._2).sum
}

def scalarProductLoop(xs : Vector[Double], ys: Vector[Double]): Double = {
  (for ( (x,y) <- xs zip ys) yield x*y).sum
}

def isPrime(n: Int): Boolean = {
  (2 until n) forall (d => n % d != 0)
}

isPrime(13)
isPrime(8)

val test = 2 until 10
test.toVector

// Given a n: Int, get all pairs (i,j) with j<i and i+j is prime
def getPairs(n: Int) = {
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i,j))) filter (pair =>
    isPrime(pair._2 + pair._1))
}

// xs flatMap f == xs map f .flatten
getPairs(7)

// for expression
// for (p <- persons if p.age > 20) yield p.name
// is equivalent to
// persons filter (p => p.age > 20) map (p => p.name)

def getPairLoop(n: Int) = {
  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i+j)
  } yield (i,j)
}

getPairs(7)


