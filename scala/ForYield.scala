//$ scalac ForYield.scala
//$ scala Main test
//Arguments: TEST

object Main {
  def main(args: Array[String]) {
    val res = for (a <- args) yield a.toUpperCase
    println("Arguments: " + res(0))
  }
}
