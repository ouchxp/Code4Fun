package day2

import me.ouchxp.tool.PrintExpr._
import simulacrum.{op, typeclass}

import scala.language.postfixOps

object Simulacrum extends App {

  import simulacrum.{op, typeclass}

  // Entering paste mode (ctrl-D to finish)
  @typeclass trait CanTruthy[A] {
    self =>
    // Return true, if `a` is truthy.
    // We can use @op annotation to specify a symbolic operator
    def truthy(a: A): Boolean
  }

  object CanTruthy {
    def fromTruthy[A](f: A => Boolean): CanTruthy[A] = (a: A) => f(a)
  }

  // According to the README https://github.com/mpilquist/simulacrum
  // the macro will generate all the operator enrichment stuff like comment below
  /**
    * // This is the supposed generated code. You don't have to write it! ??????Really??????
    * object CanTruthy {
    * def fromTruthy[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    * def truthy(a: A): Boolean = f(a)
    * }
    * *
    * def apply[A](implicit instance: CanTruthy[A]): CanTruthy[A] = instance
    * *
    * trait Ops[A] {
    * def typeClassInstance: CanTruthy[A]
    * def self: A
    * def truthy: A = typeClassInstance.truthy(self)
    * }
    * *
    * trait ToCanTruthyOps {
    * implicit def toCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): Ops[A] = new Ops[A] {
    * val self = target
    * val typeClassInstance = tc
    * }
    * }
    * *
    * trait AllOps[A] extends Ops[A] {
    * def typeClassInstance: CanTruthy[A]
    * }
    * *
    * object ops {
    * implicit def toAllCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): AllOps[A] = new AllOps[A] {
    * val self = target
    * val typeClassInstance = tc
    * }
    * }
    * }
    */

  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.fromTruthy(_ != 0)
  implicit val stringCanTruthy: CanTruthy[String] = CanTruthy.fromTruthy(Option(_).exists(_ != ""))

  import CanTruthy.ops._

  val a = 10 truthy
  val b = 0 truthy

  println(Map(
    "'10 truthy'" -> (10 truthy),
    "'0 truthy'" -> (0 truthy)
  ))

  printExpr(10 truthy)
  printExpr("Good" truthy)
  printExpr("" truthy)
  val n: String = null
  printExpr(n truthy)


}


object SimulacrumSymbolic extends App {

  @typeclass trait Appendable[A] {
    @op("~") def append(x: A, y: A): A
  }

  implicit val appendInt: Appendable[Int] = (x: Int, y: Int) => x + y

  import Appendable.ops._

  1 ~ 2 // 3

  printExpr(1 ~ 2)

}
