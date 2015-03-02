import scala.reflect.runtime.universe._
object TestReflection extends App {

  val list = List(1, 2, 3)
  def getTypeTag[T: TypeTag](obj: T) = typeTag[T]
  def getGenericTypeTag[T: TypeTag](obj: List[T]) = typeTag[T]
  val tpe = getTypeTag(list).tpe
  println(tpe.decls.tail.tail.tail.tail.head)
  
  println(getGenericTypeTag(list))

  case class Person(name: String)
  val m = runtimeMirror(getClass.getClassLoader)
  println(m)
}