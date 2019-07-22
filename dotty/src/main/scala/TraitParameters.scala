object TraitParameter extends App {

  // Trait Parameters replace early initializers with a more generally useful construct.
  // note that this does not work with top level definition right now
  trait Greeting(val name: String) {
    def msg = s"How are you, $name"
  }

  class C extends Greeting("Bob") {
    println(msg)
  }
  /////////////////////////////////

  // Arguments to a trait are evaluated immediately before the trait is initialized.
  def value() = {
    println("* evaluating value *")
    "hello"
  }

  class D extends Greeting(value()) {
    println(msg)
  }

  println("---- before initiating d1 ----")
  val d1 = D()
  println("---- after  initiating d1 ----")
  println("---- before initiating d2 ----")
  val d2 = D()
  println("---- after  initiating d2 ----")
}

// Arguments to a trait are evaluated immediately before the trait is initialized.
// def value() = {
//   println("getting value")
//   "hello"
// }
// class D extends Greeting(value()) {
//   println(msg)
// }




/*
Early initializers are part of the constructor of a subclass that is intended to run before its superclass. For example:
```
abstract class X {
    val name: String
    val size = name.size
}

class Y extends {
    val name = "class Y"
} with X
```

If the code was written instead as

```
class Z extends X {
    val name = "class Z"
}
```

then a null pointer exception would occur when Z got initialized, because size is initialized before name in the normal ordering of initialization (superclass before class).
*/