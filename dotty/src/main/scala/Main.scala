def topLevelFunc(x: String) = {
  println(x)
}

// @main annotation makes a function main
@main def someFunction() = {
  def msg = "I was compiled by dotty :)"
  println("Hello world!")
  topLevelFunc("Hello World from Top level")
}
