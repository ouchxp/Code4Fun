trait Expression[A] {
  def value(expr: A): Int
}
object Expression {
  implicit val intExpression = new Expression[Int] {
    def value(expr: Int): Int = expr
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
