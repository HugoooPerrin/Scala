/*
Function that computes the sum from a to b of f(k)
 */
def sum(f: Int => Int, a: Int, b: Int): Int =
  if (a > b) 0
  else f(a) + sum(f, a+1, b)

def cube(x: Int) = x*x*x
def sumCube(a: Int, b: Int) = sum(cube, a, b)

sum(cube, 1, 4)
sumCube(1, 4)

/*
 Using anonymous functions
 */
sum((x: Int) => x*x*x, 1, 4)
