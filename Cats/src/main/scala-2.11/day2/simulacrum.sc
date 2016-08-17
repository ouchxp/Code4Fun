import simulacrum.{op, typeclass}

// Entering paste mode (ctrl-D to finish)
@typeclass trait CanTruthy[A] { self =>
  // Return true, if `a` is truthy.
  // We can use @op annotation to specify a symbolic operator
  @op("?") def truthy(a: A): Boolean
}
object CanTruthy {
  def fromTruthy[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    def truthy(a: A): Boolean = f(a)
  }
}
// According to the README https://github.com/mpilquist/simulacrum
// the macro will generate all the operator enrichment stuff like comment below

/**
// This is the supposed generated code. You don't have to write it! ??????Really??????
object CanTruthy {
  def fromTruthy[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    def truthy(a: A): Boolean = f(a)
  }

  def apply[A](implicit instance: CanTruthy[A]): CanTruthy[A] = instance

  trait Ops[A] {
    def typeClassInstance: CanTruthy[A]
    def self: A
    def truthy: A = typeClassInstance.truthy(self)
  }

  trait ToCanTruthyOps {
    implicit def toCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): Ops[A] = new Ops[A] {
      val self = target
      val typeClassInstance = tc
    }
  }

  trait AllOps[A] extends Ops[A] {
    def typeClassInstance: CanTruthy[A]
  }

  object ops {
    implicit def toAllCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): AllOps[A] = new AllOps[A] {
      val self = target
      val typeClassInstance = tc
    }
  }
}
*/

implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.fromTruthy({
  case 0 => false
  case _ => true
})

import CanTruthy.ops._

val a = 10?
val b = 0?
