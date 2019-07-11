import cats.Show
import cats.implicits._
import cats.Semigroup

{ // cats provides some predefined implicits for show types
  // usually use import cats.implicits._ to import them all
  import cats.implicits.catsStdShowForInt
  3.show
}

// Show is a type safe version of toString (Really?). since toString is everywhere... we lose the type safety form it
// Show requires the object to have a Show[T] type class instance
// implicit conversions for common types are defined in cats.std.all._
// (Show is a type class here)

// new Object().show // won't pass type checking because there are no implicitly conversion defined for Object

case class Car(model: String)
// Use implicit to add show function to type
// there are two ways of adding show, use Show.fromToString or Show.show
// implicit val carShow = Show.fromToString[Car]
implicit val carShow: Show[Car] = Show.show[Car](_.model)

Car("CR-V").show


Semigroup[Int].combine(1, 2)
Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6))
Semigroup[Option[Int]].combine(Option(1), Option(2))
Semigroup[Option[Int]].combine(Option(1), None)
Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6)

val aMap = Map("foo" -> Map("bar" -> 5))
val anotherMap = Map("foo" -> Map("bar" -> 6))
val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap).get("foo")