/**
  * Exercise 3
  */
def countChange(money: Int, coins: List[Int]): Int = {

  def loop(money: Int, coins: List[Int]): Int =
    if (money == 0) 0
    else {
      val bool = coins.filter(x => (money >= x))
      val nextGen = bool.map{x => loop(money-x, coins)}
      nextGen.size
    }

  if (coins.isEmpty || money == 0) 0
  else loop(money, coins)
}

countChange(4, List(1, 2))


val bool = List(1, 2).filter(x => (3 >= x))
val nextGen = bool.map{x => 3-x}


countChange(10, List(1, 2))
