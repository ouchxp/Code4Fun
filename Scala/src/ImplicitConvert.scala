
/**
 * @see http://stackoverflow.com/questions/16555322/how-can-i-eliminate-this-dot-i-was-testing-implicit-conversion
 */
object TestImplicitConvertBoolean extends App {
  // why import these? see the link above
  import scala.language.implicitConversions
  import scala.language.reflectiveCalls

  case class Bool(b: Boolean) {
    def ?[X](t: => X) = new {
      def |(f: => X) = if (b) t else f
    }
  }

  object Bool {
    implicit def BooleanBool(b: Boolean) = Bool(b)
  }
  import Bool._

  println("Good".isEmpty() ? "Empty" | "Not Empty")

}

object TestImplicitConvertInt extends App {
  // why import these? see the link above
  import scala.language.implicitConversions
  import scala.language.postfixOps

  class Test(val a: Int) {
    def threetimes = a * 3
  }

  implicit def IntToTest(e: Int) = new Test(e)

  println(5 threetimes)
  println(10 threetimes)
  println(11 threetimes)
}