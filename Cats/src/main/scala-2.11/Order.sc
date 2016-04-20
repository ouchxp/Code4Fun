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
// TODO: Don't know how it works, if use 1 compare 2 Will call scala's built in compare function

