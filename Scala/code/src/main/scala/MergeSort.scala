

object TestMergeSort extends App {
  List(1, 2, 3, 4).filter { x => x > 2 }.foreach { println }

  val v = Vector(1, 3, 3, 5, 1, 0, 9, 2, 3, 4)

  def sort(v: Vector[Int]): Vector[Int] = {
    if (v.length < 2) return v
    val (v1, v2) = v.splitAt(v.length / 2)
    return merge(sort(v1), sort(v2))
  }

  def merge(v1: Vector[Int], v2: Vector[Int]): Vector[Int] = (v1, v2) match {
    case (x +: xs, y +: ys) => if (x < y) { x +: merge(xs, v2) } else { y +: merge(v1, ys) }
    case (Vector(), x) => x
    case (x, Vector()) => x
  }

  def mSort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    @scala.annotation.tailrec
    def merge(xs: List[T], ys: List[T], acc: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, _) => ys.reverse ::: acc
        case (_, Nil) => xs.reverse ::: acc
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) merge(xs1, ys, x :: acc)
          else merge(xs, ys1, y :: acc)
      }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(mSort(less)(ys), mSort(less)(zs), Nil).reverse
    }
  }

  val intSort = mSort((_: Int) < (_: Int)) _
}
