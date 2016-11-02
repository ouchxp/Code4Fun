import cats.Apply
import cats.implicits._

// Option as cartesian
(3.some |@| 5.some) map { _ - _ }
(none[Int] |@| 5.some) map { _ - _ }
(3.some |@| none[Int]) map { _ - _ }

// List as cartesian (Cartesian product)
(List("ha", "heh", "hmm") |@| List("?", "!", ".")) map {_ + _}

// *> and <* operators, pick the one arrow pointing to if both not None
3.some <* 2.some
none[Int] <* 2.some
1.some *> 2.some
none[Int] *> 2.some


// Other use case
val option2 = 1.some |@| 2.some
option2.tupled
option2 map (_+_)
option2 apWith ((x: Int, y: Int) => x + y).some
option2 apWith none[(Int, Int) => Int]

// see http://typelevel.org/cats/tut/apply.html
val double: Int => Int = _ * 2
val plus: (Int, Int) => Int = _ + _
val concat: (Int, String) => String = _ + _
val repeat: (Int, String) => String = (n, s) => (0 to n).map(x => s).mkString("")

// Apply Option[Function[T, X]] to Option[T]
Apply[Option].ap(double.some)(1.some)
double.some ap 1.some

Apply[Option].ap2(plus.some)(1.some, 2.some)
plus.some ap2 (1.some, 2.some)

// Syntax Equvalent
Apply[Option].ap2(concat.some)(42.some, "Good".some)
concat.some ap2 (42.some, "Good".some)
Option(concat) ap2 (42.some, "Good".some)

// But Some(concat) does not work, because implicit convertion only take effects on Option type
// Some(concat) ap2 (42.some, "Good".some)


Apply[List].ap2(List(concat, repeat))(List(3, 4), List("A", "B"))

// List can use implicit
List(concat, repeat) ap2 (List(3, 4), List("A", "B"))

// Cats Tuple is right biased
val tuple = (1, 2)
tuple map (_ + 1)

val either: Either[Int, Int] = Right(2)
either map (_ + 1)
