// TODO: TBD, don't understand it right now
import scala.language.existentials


//http://milessabin.com/blog/2011/06/09/scala-union-types-curry-howard/
//Miles Sabin describes a very nice way to get union type in his recent blog post Unboxed union types in Scala via the Curry-Howard isomorphism:
//He first defines negation of types as
type ¬[A] = A => Nothing
//val a: ¬[String]  = (x:String) => ???
//using De Morgan's law this allows him to define union types
type ∨[T, U] = ¬[¬[T] with ¬[U]]
//With the following auxiliary constructs
type ¬¬[A] = ¬[¬[A]]
type |∨|[T, U] = {type λ[X] = ¬¬[X] <:< (T ∨ U)}


//you can write union types as follows:
def size[T](t: T)(implicit ev$1: (Int |∨| String) # λ[T]): Int = t match {
  case i: Int => i
  case s: String => s.length
}
def size1[T: (Int |∨| String) # λ](t: T): Int = t match {
  case i: Int => i
  case s: String => s.length
}

size("1")
size(33)
// size(3L) does not pass the type check


class StringOrInt[T]
object StringOrInt {
  implicit object IntWitness extends StringOrInt[Int]
  implicit object StringWitness extends StringOrInt[String]
}
//Next, declare foo like this:

object Bar {
  def foo[T: StringOrInt](x: T) = x match {
    case _: String => println("str")
    case _: Int => println("int")
  }
}
Bar.foo(3)

def count(l: List[_]) = l.size
// count type Shorthand for count1 type
def count1(l: List[T forSome { type T }]): Int = l.size

def f[A](a: A)(implicit ev: (Int with String) <:< A): Int = a match {
  case i: Int => i
  case s: String => s.length
}

f(3)
f("33")
// https://twitter.github.io/scala_school/zh_cn/advanced-types.html
// This is how to make the function type check works only generic type of class
// when it meets some condition

case class Container1[A](value: A) {
  def addIt(implicit ev: A =:= Int) = 123 + value
  // only pass type check for Container1[CharSequence]
  def appendIt(implicit ev: A =:= CharSequence) = value.toString + "|||"
  // only pass type check for Container1[xType extends CharSequence]
  def appendItCS(implicit ev: A <:< CharSequence) = value.toString + "|||str"


  def min[B >: A](anotherValue: A)(implicit cmp: Ordering[B]): A = {
     if (cmp.lteq(value, anotherValue)) value else anotherValue
  }

}

//TODO: This one only works for ordered types, but where is that defined? Implicits?
//def foo1[X](anotherValue: X)(implicit x: Ordered[A]): X = {
//  anotherValue
//}
// Short hand
def foo2[X : Ordered](anotherValue: X): X = {
  anotherValue
}

implicitly[Ordering[Int]]

val cn = Container1(1)
cn.addIt
cn.min(2)
// won't pass type check
//cn.appendIt
val cs = Container1("cs")
cs.appendItCS
cs.min("a")
// won't pass type check)
//cs.appendIt

val cb = Container1(Bar)
//Won't pass type check (No implicit ordering defined for type)
//cb.min(Bar)


trait Container[A <: Container[A]] extends Ordered[A]
class MyContainer extends Container[MyContainer] {
  def compare(that: MyContainer) = 0
}
