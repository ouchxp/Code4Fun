

// Simply speaking
// 1. A typleclass is represented by a parameterized trait e.g. Expression[A],
// defining operations on member types
// 2. A type T is a member of typeclass TC[_] if there is a value of type TC[T]
// available in implicit scope
// 3. A context bound [T: TC] in the type parameter list for a class or method
// asserts that T is a member of TC[_] (have implicit converter that could
// convert T to TC[T])

trait Expression[A] {
  def value(expr: A): Int
}

object Expression {
  implicit val intExpression = new Expression[Int] {
    def value(expr: Int): Int = expr
  }

  implicit val stringExpression = new Expression[String] {
    def value(expr: String): Int = expr.toInt
  }

  implicit def plusExpression[A: Expression, B: Expression] = new Expression[Plus[A, B]] {
    def value(expr: Plus[A, B]): Int =
      implicitly[Expression[A]].value(expr.a) + implicitly[Expression[B]].value(expr.b)
  }

  implicit def minusExpression[A: Expression, B: Expression] = new Expression[Minus[A, B]] {
    def value(expr: Minus[A, B]): Int =
      implicitly[Expression[A]].value(expr.a) - implicitly[Expression[B]].value(expr.b)
  }
}

/** Next we add a helper function to help simplify code */

trait Show[A] {
  def show(x: A): String
}

object Show {
  /** creates an instance of [[Show]] using the provided function */
  def show[A](f: A => String): Show[A] = new Show[A] {
    def show(a: A): String = f(a)
  }

  implicit val intShow = show[Int](_.toString)
  implicit val stringShow = show[String](_)
  implicit def plusShow[A: Show, B: Show] = show[Plus[A, B]](x => {
    s"(${implicitly[Show[A]].show(x.a)} + ${implicitly[Show[B]].show(x.b)})"
  })
  implicit def minusShow[A: Show, B: Show] = show[Minus[A, B]](x => {
    s"(${implicitly[Show[A]].show(x.a)} - ${implicitly[Show[B]].show(x.b)})"
  })
}

case class Plus[A: Expression, B: Expression](a: A, b: B)

case class Minus[A: Expression, B: Expression](a: A, b: B)

object ExpressionEvaluator {
  def evaluate[A: Expression](expr: A): Int = implicitly[Expression[A]].value(expr)
}

object StringPrinter {
  def print[A: Show](x: A): String = implicitly[Show[A]].show(x)
}

ExpressionEvaluator.evaluate(1)
ExpressionEvaluator.evaluate(Plus(3, 2))
ExpressionEvaluator.evaluate(Minus(3, Plus(2, 1)))

StringPrinter.print(1)
StringPrinter.print(Plus(3, 2))
StringPrinter.print(Minus(3, Plus(2, 1)))

