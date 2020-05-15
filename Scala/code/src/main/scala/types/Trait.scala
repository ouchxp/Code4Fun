package types

object TestTrait extends App {
  trait A {
    def m1 = println("m1 called")
  }

  trait B {
    def m2 = println("m2 from B called")
  }

  trait C extends A {
    // abstract method
    /**
      * virtual field pattern
      * @see http://www.oschina.net/question/12_65077
      * @see http://mail.openjdk.java.net/pipermail/lambda-dev/2012-July/005171.html
      * Scala's trait is not "real" mixin and not "real" trait
      * @see http://stackoverflow.com/questions/5241219/how-are-scalas-traits-not-really-traits
      * A real mixin can access field, but scala trait does not support it(can be acchive by virtual field pattern)
      * A key feature of real traits is that methods can be renamed when you import them.
      * But Scala trait does not support this due to the JVM implementation.
      */
    def getValue: Int
    // call this abstract method to get inner status of a object
    def printValue = println(getValue)

    def m2 = println("m2 from C called")
  }

  // Solve mixin methods conflict
  abstract class D(i: Int) extends B with C {
    override def m2 = {
      super[B].m2 // call m2 in B
      super[C].m2 // call m2 in B
    }

    def m3 = println("m3 called, i = " + i)
  }

  class E(i: Int) extends D(i) {
    def getValue: Int = i
    def m4 = println("m4 called");
  }

  /**
    *  A trait extends a class
    *  this only means all classes that mix-in F trait must extend E class.
    *  @see http://stackoverflow.com/questions/12854941/why-can-a-scala-trait-extend-a-class
    */
  trait F extends E {}

  class G(i: Int) extends E(i) with F {}
  // Error
  // class G(i: Int) with F {}

  class H {
    def getValue = 1000
  }

  class I extends H {}

  // E mixin C in the class definition.
  val e = new E(1)
  e.m1
  e.m2
  e.m3
  e.m4
  e.printValue

  def callM1(x: H) =
    x match {
      case x: C => x.m1
      case _    => println("No m1 found")
    }

  // Mixin when creating object
  val h = new H with C
  // After mixin trait C , h can print value
  h.printValue
  // Error
  // val h = new H with F
  h.m1
  h.m2

  val hWithoutMixin = new H

  callM1(hWithoutMixin)
  callM1(h)

  val i = new I with C
  callM1(i)
  // After mixin trait C , I can print value
  i.printValue
}

//TODO: check out how AOP can be achieved by trait
//TODO: Have deeper understanding on mixin by reading this http://blog.csdn.net/gzlaiyonghao/article/details/1656969
//TODO: http://stackoverflow.com/questions/10373318/mixing-in-a-trait-dynamically
//TODO: Need have a look this (scala self type and what is the difference between self type and trait extend class constraint )
// http://stackoverflow.com/questions/10291176/how-to-use-scala-trait-with-self-reference
//trait SelfAware { self: Self =>
// ....
//}
//
//class Self
//val s = new Self with SelfAware // this is ok
//println(s.self) // error happened
//
//class X
//new X with SelfAware // error happened here
