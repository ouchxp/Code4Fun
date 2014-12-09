trait A {
  def m1 = println("m1 called")
}

trait B {
  def m2 = println("m2 from B called")
}

trait C extends A {
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

class H {}

object E extends App {
  val e = new E(1)
  e.m1
  e.m2
  e.m3
  e.m4

  def callM1(x: H) = x match {
    case x: C => x.m1
    case _ => println("No m1 found")
  }
  
  val h = new H with C
  // Error
  // val h = new H with F
  h.m1
  h.m2
  
  val hWithoutMixin = new H
  
  callM1(hWithoutMixin)
  callM1(h)

}

// Need have a look this 
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