/**
  * Exercise 1
  */
def pascal(c: Int, r: Int): Int = {

  if (c > r) throw new IllegalArgumentException("Impossible configuration")
  else {

    if (c == 0 || c == r) 1
    else pascal(c, r -1) + pascal(c-1, r-1)
    }
  }

pascal(1,2)
pascal(11, 16)