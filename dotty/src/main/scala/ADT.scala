// Enum can do ADT (Algebraic Data Types)
// TODO: how is ADT being down in Scala 2?
enum Option[+T] {
  case Some(x: T) // shorthand for `case Some(x: T) extends Option[T]`
  case None // shorthand for `case None extends Option[Nothing]`

  def isDefined: Boolean = this match {
    case None => false
    case some => true
  }
}
object Option {
  // accept all types T that is nullable
  def apply[T >: Null](x: T): Option[T] =
    if (x == null) None else Some(x)
}

// `T >: Null` lower bound, T must be supertype of Null
// i.e. T is a nullable type
// (Null is subtype of all nullable types)
val a: Null = null
val b: String = a
val c: List[Int] = a

