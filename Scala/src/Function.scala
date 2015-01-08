object TestFunctions extends App {

  // Function
  def f1(x: String) = x match {
    case "Good" => println("Feels Good!")
    case _ => println("No good")
  }

  val f2 = (x: String) => x match {
    case "Good" => println("Feels Good!")
    case _ => println("No good")
  }

  //use function can omit the match keyword
  val f3: Function[String, Unit] = {
    case "Good" => println("Feels Good!")
    case _ => println("No good")
  }

  val pf1: PartialFunction[String, Unit] = {
    case "Good" => println("Feels Good!")
    case _ => println("No good")
  }
  //----------------------------------------------

  val f4: Function[Object, String] = {
    case "Good" => println("Feels Good!"); "done";
    case _ => println("No good"); "done";
  }

  def higherOrderFun(f: Function[String, Object]) {
    f("Good");
  }

  /**
   * higherOrderFun can accept Function[Object, String]
   * it means Function[Object, String] <(is subclass of) Function[String, Object]
   * So in function, argument type is contravariant, and return type is covariant 
   * The definition of Function class should be Function[-A, +B]
   */
  higherOrderFun(f4)

}