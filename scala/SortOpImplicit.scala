//$ scalac SortOpImplicit.scala 
//warning: there was one deprecation warning (since 2.10.0); re-run with -deprecation for details
//warning: there were two feature warnings; re-run with -feature for details
//two warnings found
//$ scala implicits
//Sorted Array(2, 3, 1, 4) x = 1 2 3 4

/* Defines a new method 'sort' for array objects */
object implicits extends App {
  implicit def arrayWrapper[A : ClassManifest](x: Array[A]) =
    new {
      def sort(p: (A, A) => Boolean) = {
        util.Sorting.stableSort(x, p); x
      }
    }
  val x = Array(2, 3, 1, 4)
  println("Sorted Array(2, 3, 1, 4) x = "+ x.sort((x: Int, y: Int) => x < y).mkString(" "))
}
