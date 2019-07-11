import cats.implicits._

// implicit conversions for common types are defined in cats.std.all._
1 tryCompare 2
1.0 tryCompare Double.NaN

1 tryCompare 1

// TODO: According to algebra, it’ll return None if operands are not comparable.
// not sure when things are incomparable.
