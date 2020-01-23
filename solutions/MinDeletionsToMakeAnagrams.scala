import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._
import scala.collection.immutable.Set

object MinDeletionsToMakeAnagrams {

    // Complete the makingAnagrams function below.
    def makingAnagrams(s1: String, s2: String): Int = {
      val s1Size = s1.size
      val s2Size = s2.size
      val commonChars = s1.intersect(s2)
      val commonCharsSize = commonChars.size
      val minDeletions = (s1Size - commonCharsSize) + (s2Size - commonCharsSize)
      minDeletions
    }

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

        val s1 = stdin.readLine

        val s2 = stdin.readLine

        val result = makingAnagrams(s1, s2)

        printWriter.println(result)

        printWriter.close()
    }
}

