import cats.implicits._

// implicit conversions for common types are defined in cats.std.all._

// Only compare same types
1 === 1
1 =!= 2

val a = Long.box(1L)
val b = Int.box(1)
a == b
// a equals b but not equivalent to b
// a === b // won't pass type checking
// a =!= b // won't pass type checking as well

//Implementation Eq.eqv() Eq.neqv()
