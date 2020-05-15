/**
  * @see http://stackoverflow.com/questions/16555322/how-can-i-eliminate-this-dot-i-was-testing-implicit-conversion
  */
object TestImplicitConvertBoolean extends App {
  // why import these? see the link above

  case class Bool(b: Boolean) {
    def ?[X](t: => X) =
      new {
        def |(f: => X): X = if (b) t else f
      }
  }

  object Bool {
    implicit def BooleanBool(b: Boolean): Bool = Bool(b)
  }
  import Bool._

  println("Good".isEmpty ? "Empty" | "Not Empty")

}

object TestImplicitConvertInt extends App {
  // why import these? see the link above

  class Test(val a: Int) {
    def threeTimes: Int = a * 3
  }

  implicit def IntToTest(e: Int): Test = new Test(e)

  println(5 threeTimes)
  println(10 threeTimes)
  println(11 threeTimes)
}
