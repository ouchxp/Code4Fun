class RubberDuck {
  def quack(): Unit = println("quack!")
}

class RealDuck {
  def quack(): Unit = println("quack!quack!")
  def fly(): Unit = println("flying...")
}

class WoodDuck {
  def float(): Unit = println("floating...")
}

object DuckTyping extends App {
  type Duck = { def quack(): Unit }
  def makeDuckDo[D <: Duck](duck: D)(f: D => Unit): Unit = f(duck)

  makeDuckDo(new RubberDuck())(_.quack())
  makeDuckDo(new RealDuck())(_.quack())
  makeDuckDo(new RealDuck())(_.fly())

  // this does not work since WoodDuck does not conform with the type Duck
  // because it cannot quack
  // makeDuckDo(new WoodDuck())

  // how can I apply type bound to a lambda function?
  // see https://milessabin.com/blog/2012/04/27/shapeless-polymorphic-function-values-1/

}
