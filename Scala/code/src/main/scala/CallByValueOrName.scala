object CallByValueOrName extends App {

  lazy val lz = { println("Initializing lz"); 10 }
  println("lz declared.")
  println("Print lz = " + lz + " The first time")
  println("Print lz = " + lz + " The second time")

  // Call by name syntax sugar
  // The actual argument is an closure x which type is() => Int
  def callByNameFun(x: => Int): Unit = println(s"callByNameFun result: $x")
  callByNameFun({ println("evaluating"); 1 })

  // Equals to (no sugar for val declaration)
  val callByNameFun1 = (x: () => Int) =>
    println(s"callByNameFun1 result: ${x()}")
  callByNameFun1(() => { println("evaluating"); 1 })

  // A while loop implemented based on call by name (essentially a Function0[A])
  @scala.annotation.tailrec
  def WHILE(condition: => Boolean)(command: => Unit): Unit = {
    // If statement will evaluate the condition every time
    if (condition) {
      command
      // Here condition pass to call by name evaluation, so it will not be evaluated.
      WHILE(condition)(command)
    } else ()
  }

  var i = 0
  WHILE({ println("Evaluating condition " + (i < 5)); i < 5 }) {
    println("Executing command the " + i + " time"); i += 1
  }
}
