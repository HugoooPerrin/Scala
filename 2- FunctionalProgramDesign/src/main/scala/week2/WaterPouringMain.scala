object WaterPouringSolver extends App {

  import waterpouring._

  val problem = new Pouring(Vector(4,9))

  println(problem.solution(6))
}
