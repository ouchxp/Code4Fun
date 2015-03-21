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
    case "bad" => println("No good")
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

  /**
   * PartialFunction (是指定义域X中可能存在某些值在值域Y中没有对应的值) is a function that
   * does not handle every input (incomplete), so we can test the definition domain
   * by calling isDefinedAt method
   * *************************
   * PartialFunction is not Partial application function
   * partial application is wrap a function with a default argument and _ etc.
   * *************************
   *
   */
  println(pf1.isDefinedAt("worse"))

  val pf2: PartialFunction[String, String] = {
    case "Good" => "Feels Good!"
    case "bad" => "No good"
  }
  /**
   * We can use lift method to convert the PartialFunction to a normal function
   * which return an Option type as result
   */
  println(pf2.lift("Good"));
  println(pf2.lift("worse"));

  val f5: Function2[Int, Int, Int] = (x: Int, y: Int) => x + y
  println(f5(3, 4))

  /**
   * Here comes the partial application
   * Specify the parameter type is required here
   */
  val f6 = f5(_: Int, 1)
}