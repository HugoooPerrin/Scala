/**
  * A map is a data structure that associates keys of type Key
  * with values of type Values
  */

val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "France" -> "Paris")

// Maps are iterables
val countryOfCapital = capitalOfCountry map {case (x,y) => (y,x)}

// Maps are functions
capitalOfCountry("France")

// capitalOfCountry("Spain") NoSuchElementException
capitalOfCountry get "spain"
capitalOfCountry get "France"

/**
  * Option type is a case class, so it can be decomposed
  * by pattern matching
  */
def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "NA"
}

showCapital("Spain")

val cap = capitalOfCountry withDefaultValue "NA"
cap("Spain")

// Sorted and groupby
val fruit = List("apple", "pear", "orange", "pineapple")

fruit sortWith (_.length < _.length)
fruit.sorted

fruit groupBy (_.head)

// Polynomial class
class Polynomial(terms0: Map[Int, Double]) {

  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0 withDefaultValue 0.0

  private def adjust(t: (Int, Double)): (Int, Double) = {
            val (exp, coeff) = t
            exp -> (coeff + terms(exp))
          }

  def + (other: Polynomial) = new Polynomial(terms ++ (other.terms map adjust))

  def add(other: Polynomial) =
    new Polynomial((other.terms foldLeft terms)(addTerm))

  private def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
                val (exp, coeff) = term
                terms + (exp -> (coeff + terms(exp)))
              }

  override def toString: String =
    (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+exp) mkString "+"
}

val p1 = new Polynomial(1 -> 2, 3 -> 4, 5 -> 6.2)
val p2 = new Polynomial(0 -> 3, 3 -> 7)
p1 + p2
p1 add p2

Map(1 -> 2, 3 -> 4) ++ Map(1 -> 3, 2 -> 1)
Map(1 -> 2, 3 -> 4) + (1 -> 3)

// Here add is more efficient than ++ since it avoid the creation of an intermediary list

fruit.foldLeft(List("orange", "grappes"))(func1)

fruit.foldLeft(List("orange", "grappes"))(func2)

def func1(acc: List[String], fruit: String): List[String] =
  if (acc contains fruit) List(acc mkString "/")
  else List((fruit :: acc) mkString "/")

def func2(acc: List[String], fruit: String): List[String] =
  if (acc contains fruit) acc
  else fruit :: acc