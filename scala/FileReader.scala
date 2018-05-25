//The argument of the scala command has to be a top-level object. If that object extends trait scala.App, then all statements contained in that object will be executed; otherwise you have to add a method main which will act as the entry point of your program.

object Control {
  def using[A <: { def close(): Unit }, B] (resource: A) (f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }
}

import Control._

object TestUsing extends App {
  using(io.Source.fromFile("PrimeFinder.scala")) { source => {
    for (line <- source.getLines) {
      println(line)
    }
  }}
}

//$ scalac FileReader.scala 
//warning: there was one feature warning; re-run with -feature for details
//one warning found
//$ scala TestUsing
////https://www.scala-lang.org/documentation/your-first-lines-of-scala.html
////$ scalac PrimeFinder.scala 
////$ scala PrimeFinder 1
////1
////number is not prime
////$ scala PrimeFinder 2
////2
////number is prime
////$ scala PrimeFinder 0
////0
////number is not prime
////$ scala PrimeFinder 3
////3
////number is prime
////$ scala PrimeFinder 4
////4
////number is not prime
//
//object PrimeFinder {
//    def main(args: Array[String]): Unit = {
//        args.foreach(println)
//        if (isPrime(args(0).toInt)) println("number is prime")
//        else println("number is not prime")
//    }
//
//    def isPrime(n: Int): Boolean = {
//        if (n <= 1) false
//        else if (n == 2) true
//        else !(2 to (n - 1)).exists(n % _ == 0)
//    }
//}
//
