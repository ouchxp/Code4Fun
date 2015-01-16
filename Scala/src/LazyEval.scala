object LazyEval extends App {

  def defstr = { // actually is a function
    println("get defstr")
    "defstr"
  }

  val valstr = { // only evaluate once when defining it
    println("get valstr")
    "valstr"
  }

  lazy val lazystr = { // only evaluate once when first used
    println("get lazystr")
    "lazystr"
  }

  def f1(arg: String) = {
    arg + arg
  }

  def f1lazy(arg: => String) = {
    arg + arg
  }

  println("-------------------")

  f1(defstr) // Eval defstr only once

  println("-------------------")

  f1lazy(defstr) // Eval defstr twice

  println("-------------------")

  f1(lazystr)

  f1lazy(lazystr)

}