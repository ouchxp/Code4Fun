def topLevelFunc(x: String) = {
  println(x)
}

object Main extends App {
  def msg = "I was compiled by dotty :)"
  println("Hello world!")
  topLevelFunc("Hello World from Top level")
}
