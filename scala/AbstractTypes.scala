//$ scalac AbstractTypes.scala 
//$ scala abstractTypes
//integer buffer with one elem: value: 1
//integer buffer with three elem: length: 3

object abstractTypes extends App {

  abstract class Buffer {
    type T
    val elem: T
  }

  abstract class SeqBuffer {
    type T
    val elem: Seq[T]
    def length = elem.length
  }

  def newIntBuffer(n: Int) = new Buffer {
    type T = Int
    val elem = n
  }

  def newIntBuffer(n: Int*) = new SeqBuffer {
    type T = Int
    val elem = n
  }

  println(s"integer buffer with one elem: value: ${newIntBuffer(1).elem}")
  println(s"integer buffer with three elem: length: ${newIntBuffer(1, 2, 3).length}")
}
