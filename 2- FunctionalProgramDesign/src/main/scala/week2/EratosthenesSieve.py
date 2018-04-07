import time 
import math

def get_primes(n):
  m = n+1
  numbers = [True for i in range(m)]
  for i in range(2, int(math.sqrt(n))):
    if numbers[i]:
      for j in range(i*i, m, i):
        numbers[j] = False
  primes = []
  for i in range(2, m):
    if numbers[i]:
      primes.append(i)
  return primes

start = time.time()
primes = get_primes(10000)
print((time.time() - start)*1000000)

print(len(primes))