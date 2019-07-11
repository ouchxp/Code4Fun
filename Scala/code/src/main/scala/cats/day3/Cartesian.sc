import cats.Apply
import cats.implicits._

// Option as cartesian
(3.some, 5.some) mapN { _ - _ }
(none[Int], 5.some) mapN { _ - _ }
(3.some, none[Int]) mapN { _ - _ }
// 3 | -2
//   |_____
//      5

// List as cartesian (Cartesian product)
(List("ha", "heh", "hmm"), List("?", "!", ".")) mapN {_ + _}
// ha  | ha?  ha!  ha.
// heh | heh? heh! heh.
// hmm | hmm? hmm! hmm.
//     |_______________
//        ?    !    .


// *> and <* operators, pick the one arrow pointing to
// to populate the matirx values
3.some <* 2.some
none[Int] <* 2.some
1.some *> 2.some
none[Int] *> 2.some

List("ha", "heh", "hmm") <* List("?", "!", ".")
// ha  | ha   ha   ha
// heh | heh  heh  heh
// hmm | hmm  hmm  hmm
//     |_______________
//        ?    !    .

// see http://typelevel.org/cats/tut/apply.html
val double: Int => Int = _ * 2
val plus: (Int, Int) => Int = _ + _
val concat: (Int, String) => String = _ + _
val repeat: (Int, String) => String = (n, s) => (0 until n).map(_ => s).mkString("")

// Apply Option[Function[T, X]] to Option[T]
Apply[Option].ap(double.some)(1.some)
double.some ap 1.some

Apply[Option].ap2(plus.some)(1.some, 2.some)
plus.some ap2 (1.some, 2.some)

// Syntax Equvalent
Apply[Option].ap2(concat.some)(42.some, "Good".some)
concat.some ap2 (42.some, "Good".some)
Option(concat) ap2 (42.some, "Good".some)

// But Some(concat) does not work, because implicit conversion only take effects on Option type
// Some(concat) ap2 (42.some, "Good".some)

Apply[List].ap2(List(concat, repeat))(List(3, 4), List("A", "B"))

// List can use implicit
List(concat, repeat) ap2 (List(3, 4), List("A", "B"))

//// Cats Tuple is right biased ( no loger works now )
//val tuple = (1, 2)
//tuple map (_ + 1)
//
