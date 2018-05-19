//https://www.scala-lang.org/documentation/your-first-lines-of-scala.html
//$ scalac PrimeFinder.scala 
//$ scala PrimeFinder 1
//1
//number is not prime
//$ scala PrimeFinder 2
//2
//number is prime
//$ scala PrimeFinder 0
//0
//number is not prime
//$ scala PrimeFinder 3
//3
//number is prime
//$ scala PrimeFinder 4
//4
//number is not prime

object PrimeFinder {
    def main(args: Array[String]): Unit = {
        args.foreach(println)
        if (isPrime(args(0).toInt)) println("number is prime")
        else println("number is not prime")
    }

    def isPrime(n: Int): Boolean = {
        if (n <= 1) false
        else if (n == 2) true
        else !(2 to (n - 1)).exists(n % _ == 0)
    }
}
