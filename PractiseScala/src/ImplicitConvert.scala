object ImplicitConvert extends App {
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