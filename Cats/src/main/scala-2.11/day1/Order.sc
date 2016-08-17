import cats._
import cats.std.all._
import cats.syntax.order._
import algebra.std._
import algebra._
import algebra.Order._
1 > 2.0

// implicit conversions for common types are defined in cats.std.all._
compare(1, 2)
1L compare 1

val a = Long.box(1)
val b = Int.box(1)
// a compare b
// this won't pass because a and b have different type
// and do not have implicit conversion

