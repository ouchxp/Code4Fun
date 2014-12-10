object TestTypeChecking extends App {

  def macthGeneric[A](lst: List[A]) = lst match {
    // if use l(0).toUpperCase() will throw an exception here.
    // because match cannot identify the generic type of List
    case l: List[String @unchecked] => println("A list of strings?! ")
    case _ => println("Ok")
  }
  macthGeneric(List(1, 2, 3))

  // This is how to identify the generic type
  import scala.reflect.runtime.universe._
  def matchGenericWithTypeTag[A: TypeTag](list: List[A]) = list match {
    case strlist: List[String @unchecked] if typeOf[A] =:= typeOf[String] => println("A list of strings! " + strlist(0))
    case intlist: List[Int @unchecked] if typeOf[A] =:= typeOf[Int] => println("A list of ints! " + intlist(0).toString)
  }
  matchGenericWithTypeTag(List(1, 2, 3))
  matchGenericWithTypeTag(List("a", "b", "c"))

  /**
   * @see https://stackoverflow.com/questions/1094173/how-do-i-get-around-type-erasure-on-scala-or-why-cant-i-get-the-type-paramete
   * You can use the Typeable type class from shapeless to get the result you're after,
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
}