package reflection

object TestReflection extends App {
  import scala.reflect._
  import scala.reflect.runtime.universe._

  val list = List(1, 2, 3)
  def getTypeTag[T: TypeTag](obj: T) = typeTag[T]
  def getGenericTypeTag[T: TypeTag](obj: List[T]) = typeTag[T]
  val tpe = getTypeTag(list).tpe
  tpe.ensuring(_ =:= typeOf[List[Int]]) // Type equal
  println(
    "The 4th symbol of type " + tpe + " is " + tpe.decls.toList(4)
  ) // 4th symbol is tail method
  println(getGenericTypeTag(list))

  // Reflection on constructor
  case class Person(name: String)
  // ClassLoader mirror
  val m = runtimeMirror(getClass.getClassLoader)
  println(m)

  val classPerson = typeOf[Person].typeSymbol.asClass
  println(classPerson)
  val cm = m.reflectClass(classPerson)
  println(cm)
  val ctor = typeOf[Person].decl(termNames.CONSTRUCTOR).asMethod
  println(ctor)
  val ctorm = cm.reflectConstructor(ctor)
  val obj = ctorm.apply("Good")
  println(obj)

  // Should not do this in Traditional Java (get class etc)
  // in Scala, we instead obtain runtime types. Using Java reflection on Scala classes might return surprising or incorrect results.
  // val ps = Class.forName("TestReflection$Person").newInstance()
  // println(ps)

  // Reflection on field
  case class Purchase(name: String, orderNumber: Int, var shipped: Boolean)
  val purchase = Purchase("good", 10, false)

  //println(typeOf[Purchase].decls)
  val shippingTerm = typeOf[Purchase].decl(TermName("shipped")).asTerm
  val im = m.reflect(purchase)
  println(im)
  val fm = im.reflectField(shippingTerm)
  println(fm.get)
  fm.set(true)
  println(purchase)

  /** Java Class VS Scala Type */
  println("----------------Java Class VS Scala Type---------------")
  class E {
    type T
    def x = 2
  }
  class C extends E
  class D extends C
  val c = new C { type T = String }
  val d = new D { type T = String }
  println(c, d)

  // This will return false because c is actually a anonymous class created based on C
  // And D is extends from C not this anonymous class. So isAssignableFrom will return false
  println(c.getClass.isAssignableFrom(d.getClass))

  def isSubTypeOf[T: TypeTag, S: TypeTag](x: T, y: S): Boolean = {
    val leftTag = typeTag[T]
    val rightTag = typeTag[S]
    println(leftTag)
    println(rightTag)
    leftTag.tpe <:< rightTag.tpe
  }
  println(isSubTypeOf(d, c)) // return true

  // In this case, dd is directly extend from C, so the isAssignableFrom method return true
  val cc = new C
  val dd = new D { type T = String }
  println(cc.getClass.isAssignableFrom(dd.getClass)) // return true

  /** Invoke method in Scala way */
  val e = new E
  // Create an instance mirror
  val eim = m.reflect(e)
  // Get the method signature
  val ms = typeOf[E].decl(TermName("x")).asMethod
  // Bind this method with object
  val mm = eim.reflectMethod(ms)
  // Invoke
  val x = mm()
  println(x)
  println(invoke(e, "x"))

  case class F(name: String) {
    private var x = 10
    private val y = 5
    def greet(msg: String): Unit = {
      println(msg + " " + name + " " + x + ", " + y)
    }
  }

  def invoke[T: TypeTag: ClassTag](
      obj: T,
      methodName: String,
      args: Any*
  ): Any = {
    val m = runtimeMirror(obj.getClass.getClassLoader)
    val oim = m reflect obj;
    val ms = typeOf[T] decl TermName(methodName) asMethod
    val mm = oim reflectMethod ms
    mm(args: _*)
  }
  val f = new F("world")
  invoke(f, "greet", "hello")

  /** Access private var field */
  println(typeOf[F].decls)
  val xField = typeOf[F].decl(TermName("x")).asMethod.accessed.asTerm
  val fim = m.reflect(f)
  val xVar = fim.reflectField(xField)
  println(xVar.get)
  xVar.set(20)
  f.greet("changed")
  //println(xField)

  /** Access private val filed */
  // Able to change value of val field (even val is considered final)
  val yField = typeOf[F].decl(TermName("y")).asTerm
  println(yField)
  val yVal = fim.reflectField(yField)
  println(yVal.get)
  yVal.set(6)
  f.greet("changed")
}
