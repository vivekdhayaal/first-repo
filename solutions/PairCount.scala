import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._
import scala.collection.immutable.Map

object Solution {

    def sockMerchant(n: Int, ar: Array[Int]): Int = {
      var socksCountMap = Map.empty[Int, Int]
      var socksPairCount = 0
      ar.foreach { i =>
        socksCountMap.get(i) match {
          case Some(_) => {
            socksCountMap -= i
            socksPairCount += 1
          }
          case None => socksCountMap += (i -> 1)
        }
      }
      socksPairCount
    }

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

        val n = stdin.readLine.trim.toInt

        val ar = stdin.readLine.split(" ").map(_.trim.toInt)
        val result = sockMerchant(n, ar)

        printWriter.println(result)

        printWriter.close()
    }
}
//scala> Solution.sockMerchant(9, Array(10, 20, 20, 10, 10, 30, 50, 10, 20))
//res1: Int = 3

