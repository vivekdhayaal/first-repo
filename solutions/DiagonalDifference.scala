import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._
import scala.collection.immutable._
import scala.collection.mutable._
import scala.collection.concurrent._
import scala.concurrent._
import scala.io._
import scala.math._
import scala.sys._
import scala.util.matching._
import scala.reflect._

object Result {

    /*
     * Complete the 'diagonalDifference' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY arr as parameter.
     */

    def diagonalDifference(arr: Array[Array[Int]]): Int = {
    // Write your code here
      var sum1 = 0
      var sum2 = 0
      val size = arr.size
      val sizeMinus1 = size - 1
      for (i <- 0 until size) {
        for (j <- 0 until size) {
          if (i == j) sum1 += arr(i)(j)
          if ((i + j) == sizeMinus1) sum2 += arr(i)(j)
        }
      }
      Math.abs(sum1 - sum2)
    }

}

object DiagonalDifference {
    def main(args: Array[String]) {
        val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

        val n = StdIn.readLine.trim.toInt

        val arr = Array.ofDim[Int](n, n)

        for (i <- 0 until n) {
            arr(i) = StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
        }

        val result = Result.diagonalDifference(arr)

        printWriter.println(result)

        printWriter.close()
    }
}
