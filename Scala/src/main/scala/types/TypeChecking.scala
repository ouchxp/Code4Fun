import scala.reflect._
import scala.reflect.runtime.universe._
import scala.language.postfixOps

object TestTypeChecking extends App {

  def macthGeneric[A](lst: List[A]) = lst match {
    // if use l(0).toUpperCase() will throw an exception here.
    // because match cannot identify the generic type of List
    case l: List[String @unchecked] => println("A list of strings?! ")
    case _ => println("Ok")
  }
  macthGeneric(List(1, 2, 3))

  // This is how to identify the generic type
  def matchGenericWithTypeTag[A: TypeTag](list: List[A]) = list match {
    case strlist: List[A] if typeOf[A] =:= typeOf[String] => println("A list of strings! " + strlist.head)
    case intlist: List[A] if typeOf[A] =:= typeOf[Int] => println("A list of ints! " + intlist.head)
  }
  matchGenericWithTypeTag(List(1, 2, 3))
  matchGenericWithTypeTag(List("a", "b", "c"))
  
  // Actually this is achieved by introducing one extra argument to the function,
  // The extra argument is the generic type of current invocation
  // This line below shows the actual method signature
  println(this.getClass.getDeclaredMethods.toList.filter(_.getName() == "matchGenericWithTypeTag").head)
  
  val c = this ## //HashCode
  val d = this -> "a" // Wrap this and other in to a tuple, like an entry of the map
  /**
   * @see https://stackoverflow.com/questions/1094173/how-do-i-get-around-type-erasure-on-scala-or-why-cant-i-get-the-type-paramete
   * You can use the Typeable type class from shapeless <@link https://github.com/milessabin/shapeless> to get the result you're after,
   * Sample REPL session,
   *
   * scala> import shapeless.Typeable._
   * import shapeless.Typeable._
   *
   * scala> val l1 : Any = List(1,2,3)
   * l1: Any = List(1, 2, 3)
   *
   * scala> l1.cast[List[String]]
   * res0: Option[List[String]] = None
   *
   * scala> l1.cast[List[Int]]
   * res1: Option[List[Int]] = Some(List(1, 2, 3))
   * The cast operation will be as precise wrt erasure as possible given the in-scope Typeable instances available.
   */

  val anyObj: Any = new Object
  val anyInt: Any = 1
  // The line below will not type check because Int is not a subtype of Object but a subtype of Any (Int < AnyVal < Any)
  // val anyObj:Object = 1
  
  // Scala did a little trick on the type system, we knew that scala think Object is subtype of Any, 
  // but we cannot get Any from obj.getClass.getSuperclass method (return null)
  val obj: Object = new Object
  println(obj.getClass.getSuperclass)
  // getClass.getSuperclass method for Int is also null, that's because Int is considered a primitive type
  println(1.getClass.getSuperclass)
  println(1.getClass.isPrimitive)
  // Unlike Object and Int, String type can get a valid result of superclass (class java.lang.Object)
  println("".getClass.getSuperclass)
  
  

}
