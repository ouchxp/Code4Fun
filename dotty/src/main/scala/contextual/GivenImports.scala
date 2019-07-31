case class TC(x: Int)

object A {
  given tc as TC = TC(3)
  def f() given TC = println(the[TC])
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
}