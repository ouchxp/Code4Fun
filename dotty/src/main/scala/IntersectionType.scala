trait Resettable {
  def reset(): this.type
}
trait Growable[T] {
  def add(x: T): this.type
}
def f(x: Resettable & Growable[String]) = {
  x.reset()
  x.add("first")
}

class RG[T](var values: List[T] = List.empty) extends Resettable, Growable[T] {
  def reset() = {
    values = List.empty
    this
  }
  def add(x: T) = {
    values = x :: values
    this
  }
}

object IntersectionType extends App {
    val x = RG[String]()
    f(x)
    println(x.values)
}

// type List[A] & List[B] is List[A & B] because List is covariant.
val x: List[A] & List[B] = List[A & B]()
trait A {
  def children: List[A]
}
trait B {
  def children: List[B]
}
class C extends A, B {
  def children: List[A & B] = List.empty
}
