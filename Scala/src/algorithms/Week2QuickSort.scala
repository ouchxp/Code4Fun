
import scala.io.Source

object Week2QuickSort extends App {
  val srcArr = Source.fromFile("QuickSort.txt", "UTF-8").getLines().map { x => x.toInt}.toArray
  var compareCount: Long = 0

  def qsort(partition: (Array[Int], Int, Int) => Int)(arr: Array[Int]): Unit = {
    def sort(lo: Int, hi: Int): Unit = {
      if (hi > lo) {
        compareCount += (hi - lo)
        val pi = partition(arr, lo, hi)
        sort(lo, pi - 1)
        sort(pi + 1, hi)
      }
    }
    sort(0, arr.length - 1)
  }

  def partition(arr: Array[Int], lo: Int, hi: Int): Int = {

    //val pi = lo + 0 // Chosen pivot index (always first elem)
    //val pi = hi // Chosen pivot index (always last elem)
    val pi = medianOfThree(arr, lo, hi)

    swap(arr, lo, pi) // Move pivot to the first of array
    val p = arr(lo) // Get the value of pivot

    var i = lo + 1
    for (j <- lo + 1 to hi) {
      if (arr(j) < p) {
        swap(arr, i, j)
        i += 1
      }
    }
    swap(arr, lo, i - 1)
    i - 1
  }

  def swap(arr: Array[Int], x: Int, y: Int) = {
    val tmp = arr(x)
    arr.update(x, arr(y))
    arr.update(y, tmp)
  }

  def middle(lo: Int, hi: Int) = {
    val len = hi - lo + 1
    lo + (len / 2) - (if(len % 2 == 0) 1 else 0)
  }

  def medianOfThree(arr: Array[Int], lo: Int, hi: Int): Int = {
    val mid = middle(lo, hi)
    (arr(lo), arr(mid), arr(hi)) match {
      case (l, m, h) if (h >= l && l >= m) || (m >= l && l >= h) => lo
      case (l, m, h) if (h >= m && m >= l) || (l >= m && m >= h) => mid
      case (l, m, h) if (l >= h && h >= m) || (m >= h && h >= l) => hi
    }
  }

  qsort(partition)(srcArr)
  println(compareCount)
}
