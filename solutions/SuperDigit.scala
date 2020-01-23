import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._

object SuperDigit {

    def sumOfDigits(num: String) = BigInt(Seq(num: _*).map(_.toString.toInt).sum)

    // Complete the superDigit function below.
    def superDigit(n: String, k: Int): Int = {
      println(sumOfDigits(n))
      var num = (sumOfDigits(n) * k).toString
      println(num)
      while (num.size > 1) {
        num = sumOfDigits(num).toString
        println(num)
      }
      num.toInt
    }

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

        val nk = stdin.readLine.split(" ")

        val n = nk(0)

        val k = nk(1).trim.toInt

        val result = superDigit(n, k)

        printWriter.println(result)

        printWriter.close()
    }
}
