import scala.math.Ordering
import scala.concurrent.ExecutionContext
case class Monoid[T]()
case class TC(x: Int)

object A {
  given tc as TC = TC(3)
  def f()(using tc: TC) = println(tc)
}

object Instances {
  given intOrd as Ordering[Int] = null;
  given [T: Ordering] as Ordering[List[T]] = null;
  given ec as ExecutionContext = null
  given im as Monoid[Int]
}

object GivenImports extends App {
  {
    // import only import non-implied members
    import A.{given _, _} // without this, f and f1 will not work
    def f1()(using tc: TC) = println(tc)
    f()
    f1()
  }

  {
    import A._
    def f1()(using tc: TC) = println(tc)
    // does not compile
    // f()
    // f1()
  }
  {
    // Anonymous given instance can be imported using this syntax
    import A.{given TC}
    def f1()(using tc: TC) = println(tc)
    f1()
  }
  {
    // Importing givens of several types T1,...,Tn is expressed by bounding with a union type.
    import Instances.{given Ordering[?], given ExecutionContext}
    // or
    import Instances.{im, given Ordering[?]}
  }
}