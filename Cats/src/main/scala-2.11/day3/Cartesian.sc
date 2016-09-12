import cats.{Cartesian, Functor}
import cats.implicits._
import cats.syntax.cartesian._


// Option as cartesian
(3.some |@| 5.some) map { _ - _ }
(none[Int] |@| 5.some) map { _ - _ }
(3.some |@| none[Int]) map { _ - _ }

// List as cartesian
(List("ha", "heh", "hmm") |@| List("?", "!", ".")) map {_ + _}

// > and < operators
//abstract class CartesianOps[F[_], A] extends Cartesian.Ops[F, A] {
//  def |@|[B](fb: F[B]): CartesianBuilder[F]#CartesianBuilder2[A, B] =
//    new CartesianBuilder[F] |@| self |@| fb
//
//  def *>[B](fb: F[B])(implicit F: Functor[F]): F[B] = F.map(typeClassInstance.product(self, fb)) { case (a, b) => b }
//
//  def <*[B](fb: F[B])(implicit F: Functor[F]): F[A] = F.map(typeClassInstance.product(self, fb)) { case (a, b) => a }
//}
