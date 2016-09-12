import cats.Functor
import cats.implicits.catsStdInstancesForList

val curried = {(x: Int, y: Int) => x * y}.curried
// producing list of function
val hs = Functor[List].map(List(1, 2, 3, 4))(curried)
// apply functions
Functor[List].map(hs) { f => f(9)}

object Catnip {
  trait IdOp[A] {
    def some: Option[A]
  }
  implicit def toIdOp[A](a : A):IdOp[A]  = new IdOp[A] {
    override def some: Option[A] = Some(a)
  }
  def none[A]: Option[A] = None
}

import Catnip._
9.some
none[Int]
