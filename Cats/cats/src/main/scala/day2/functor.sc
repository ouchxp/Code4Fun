import cats.Functor
import cats.implicits._
import me.ouchxp.tool.PrintExpr._

// Cats' Functor can turn something not a functor to functor
// By adding map function

// List already has map function, so use Functor[List].map instead
Functor[List].map(List(1, 2, 3)) { _ + 1 }

// Conventionally we treat Left projection as abnormal value
// Right projection as `Right` value
// In this case map only works on right
val left: Either[String, Int] = Left("boom!")
// Either does not have map function by default (that has changed since scala 2.12).
// Here the map function is introduced by cats
left map { _ + 1 }
val right: Either[String, Int] = Right(1)
right map { _ + 1 }

// Lift
val lifted = Functor[List].lift {(_: Int) * 2}
lifted(List(1, 2, 3))

// Some other functions from Functor
List(1, 2, 3).void
List(1, 2, 3) fproduct {(_: Int) * 2}
printExpr(List(1, 2, 3) as "x")

