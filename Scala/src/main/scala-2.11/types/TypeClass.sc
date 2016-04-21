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

case class Plus[A: Expression, B: Expression](a: A, b: B)

case class Minus[A: Expression, B: Expression](a: A, b: B)

object ExpressionEvaluator {
  def evaluate[A: Expression](expr: A): Int =
    implicitly[Expression[A]].value(expr)
}

ExpressionEvaluator.evaluate(1)
ExpressionEvaluator.evaluate(Plus(3, 2))
ExpressionEvaluator.evaluate(Minus(3, Plus(2, 1)))

