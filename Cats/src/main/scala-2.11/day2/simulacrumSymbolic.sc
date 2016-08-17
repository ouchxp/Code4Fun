import simulacrum.{op, typeclass}
@typeclass trait Appendable[A] {
  @op("|+|") def append(x: A, y: A): A
}
object Appendable {} // This have to be defined

implicit val appendInt: Appendable[Int] = new Appendable[Int] {
  def append(x: Int, y: Int) = x + y
}
import Appendable.ops._
1 |+| 2
