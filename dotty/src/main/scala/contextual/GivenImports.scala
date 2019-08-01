import scala.math.Ordering
import scala.concurrent.ExecutionContext
case class Monoid[T]()
case class TC(x: Int)

object A {
  given /*tc*/ as TC = TC(3)
  def f() given TC = println(the[TC])
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
    import A._
    import given A._ // without his, f and f1 will nor work
    def f1() given (tc: TC) = println(tc)
    f()
    f1()
  }

  {
    import A._
    def f1() given (tc: TC) = println(tc)
    // does not compile
    // f()
    // f1()
  }
  {
    // Anonymous given instance can be imported using this syntax
    import given A.{_:TC}
    def f1() given (tc: TC) = println(tc)
    f1()
  }
  {
    // Importing givens of several types T1,...,Tn is expressed by bounding with a union type.
    // import given A.{_: T1 | ... | Tn}
    import given Instances.{_: Ordering[?] | ExecutionContext}
    // or
    import given Instances.{im, _: Ordering[?]}
  }
}