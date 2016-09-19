import cats.Apply
import cats.implicits._

// Option as cartesian
(3.some |@| 5.some) map { _ - _ }
(none[Int] |@| 5.some) map { _ - _ }
(3.some |@| none[Int]) map { _ - _ }

// List as cartesian
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
Apply[Option].ap(Some(double))(Some(1))
Apply[Option].ap2(Some(plus))(Some(1), Some(2))
Apply[Option].ap2(Some(concat))(Some(42), Some("Good"))
Apply[List].ap2(List(concat, repeat))(List(3, 4), List("A", "B"))

// List can use implicit
List(concat, repeat) ap2 (List(3, 4), List("A", "B"))
