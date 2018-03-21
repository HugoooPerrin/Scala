// Really analogous to sequences
val fruit = Set("apple", "banana", "pear")
val s = (1 to 6).toSet

// Most operations on seq also works on set
fruit filter (_.startsWith("app"))

/**
  * Main differences:
  *
  * 1- Sets are unordered
  * 2- Sets do not have duplicate
  * 3- The main operation is contains
  */

// N-queens problem:
def queens(n: Int): Set[List[Int]] = {

  def placeQueens(k: Int): Set[List[Int]] = {
    if (k == 0) Set(List())
    else
      for {
        previousQueens <- placeQueens(k-1)
        col <- 0 until n
        if isSafe(col, previousQueens)
      } yield col :: previousQueens
  }

  def isSafe(col: Int, previousQueens: List[Int]): Boolean = {
    val row = previousQueens.length
    val queensWithRows = (row - 1 to 0 by -1) zip previousQueens
    queensWithRows forall {
      case (r, c) => col != c && math.abs(col-c) != row-r
    }
  }

  placeQueens(n)
}

def show(queens: List[Int]) = {
  val lines =
    for (col <- queens.reverse)
    yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
  "\n" + (lines mkString "\n")
}

(queens(5) map show) mkString "\n"