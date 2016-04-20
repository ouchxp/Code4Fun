import cats._
import cats.std.all._
import cats.syntax.show._

3.show


// Show is a type safe version of toString (Really?). since toString is everywhere... we lose the type safety form it
// Show requires the object to have a Show[T] type class instance
// implicit conversions for common types are defined in cats.std.all._
// (Show is a type class here)

// new Object().show // won't pass type checking because there are no implicitly conversion defined for Object

case class Test()
// Use implicit to add show function to type
// And
implicit val testShow: Show[Test] = Show.show[Test](x => "good")

Test().show

