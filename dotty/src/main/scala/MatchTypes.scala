type Elem[X] = X match {
  case String => Char
  case Array[t] => t // Type variables in patterns start as usual with a lower case letter.
  case Iterable[t] => t
}

object MatchTypes extends App {
  val a: Elem[String] = 'c'
  val b: Elem[Array[Int]] = 10
  val c: Elem[Iterable[String]] = "hello"


  // match type can also be recursive (currently not working with top level definition)
  // see https://github.com/lampepfl/dotty/issues/6362
  type LeafElem[X] = X match {
    case String => Char
    case Array[t] => LeafElem[t]
    case Iterable[t] => LeafElem[t]
    case AnyVal => X
  }
  val d: LeafElem[String] = 'c'
  val e: LeafElem[Array[Int]] = 10
  val f: LeafElem[Iterable[String]] = 'c'

}
