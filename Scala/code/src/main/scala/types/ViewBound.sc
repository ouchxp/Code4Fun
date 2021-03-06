import scala.language.reflectiveCalls
import scala.reflect.runtime.universe.typeOf

/** Int is not a direct subtype of Comparable **/
typeOf[Int] <:< typeOf[Comparable[Int]]
/** Implicit conversion exist for Int => Comparable[Int], actually Int => RichInt **/
val comparableInt: Comparable[Int] = 10
comparableInt.getClass // RichInt

/** ViewBound is deprecated */
/*Normal Type bound does not allow implicit conversion*/
case class NormalBound[T <: Comparable[T]](x:T)
/*ViewBound means allow direct subtype and also allow implicit conversion*/
case class ViewBound[T <% Comparable[T]](x:T)
/*ContextBound add implicit conversion function parameter explicitly*/
case class ContextBound[T](x:T)(implicit ev$1: T => Comparable[T])
/* This not compile because Int is not extends from Comparable*/
//val n1 = NormalBound(10)
val n2 = ViewBound(10)
/*This is working because Int can convert to RichInt implicitly*/
val c1 = ContextBound(10)
/*This won't pass because Object does not have implicit conversion to Comparable*/
//val c2 = ContextBound(new Object())


/**Several ways to write a context bound*/
type CONVERTER[X] = X => Comparable[X]
// FIXME: the way using context bound is wrong here. It should be used like in TypeClass.sc
// FIXME: need refactor to make the intention clear at some day.
// FIXME: it should be used along with implicitly[T], not used as a implicit conversion function
// FIXME: otherwise it becomes very subtle

def goo1[T : CONVERTER](x: T):Comparable[T] = implicitly[CONVERTER[T]].apply(x)
// hash(#) means reference to the nested type, which means the function type we defined here (nasty)
/** @see http://stackoverflow.com/questions/9443004/what-does-the-operator-mean-in-scala */
def goo2[T :({type L[X] = X => Comparable[X]})#L](x: T):Comparable[T] = implicitly[CONVERTER[T]].apply(x)
def goo3[T](x: T)(implicit ev: T => Comparable[T]):Comparable[T] = ev.apply(x)
/* You cannot define type directly here, have to define first or, define and use # to reference to it*/
//def goo4[T : T => Comparable[T]](x: T):Comparable[T] = x
// Other implementations are working
goo1(10)
goo2(10)
goo3(10)
/**This is working because Int can convert to RichInt implicitly, and RichInt is Comparable **/

