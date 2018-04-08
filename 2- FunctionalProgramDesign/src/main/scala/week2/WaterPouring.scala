package waterpouring

class Pouring(capacity: Vector[Int]) {

// States

  type State = Vector[Int]
  val initialState = capacity map (x => 0)

// Moves

  trait Move {
    def change(state: State): State
  }

  case class Empty(glass: Int)        extends Move {
    def change(state: State) = state updated (glass, 0)
  }

  case class Fill(glass: Int)         extends Move {
    def change(state: State) = state updated (glass, capacity(glass))
  }

  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State) = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to) + amount)
    }
  }

  val glasses = 0 until capacity.length

  val moves =
    (for (g <- glasses) yield Empty(g)) ++
    (for (g <- glasses) yield Fill(g)) ++
    (for {from <- glasses
          to <- glasses
          if from != to} yield Pour(from, to))

// Paths

  type History = List[Move] // Last move (more recent) come first in history

  class Path(history: History, val endState: State) {

    // def endState: State = (history foldRight initialState) (_ change _)

    //
    // Recursive alternative:
    //
    //  def endState: State = trackState(history)
    //
    //  private def trackState(xs: History): State = xs match {
    //    case Nil => initialState
    //    case move :: tail => move change trackState(tail)
    //  }
    //

    def extend(move: Move) = new Path(move :: history, move change endState)

    override def toString: String = (history.reverse mkString " ") + " => " + endState
  }

  val initialPath = new Path(Nil, initialState)

// Generation of all possible paths

  type Possibilities = Set[Path]

  def from(paths: Possibilities, explored: Set[State]): Stream[Possibilities] = {
    if (paths.isEmpty) Stream()
    else {
      val more = for {
        path <- paths
        next <- moves map path.extend // Moves is the same at each level of paths
        if !(explored contains next.endState)
      } yield next
      paths #:: from(more, explored ++ (more map (_.endState)))
    }
  }

  val pathSets = from(Set(initialPath), Set(initialState)) // All possible paths -> infinite sequence -> Stream

// Solution

  def solution(target: Int): Stream[Path] = {
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
  }

}

