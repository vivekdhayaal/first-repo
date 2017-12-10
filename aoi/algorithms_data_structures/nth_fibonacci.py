
def fibonacci(n):
  fib = [0, 1]
  i = 2
  for i in xrange(2, n+1):
    fib.append(fib[i-1] + fib[i-2])
  return fib[n]

if __name__ == "__main__":
  print fibonacci(4)
