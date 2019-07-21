// can have parameters
// can define fields and methods
// can interop with Java

enum Color(val rgb: Int) extends java.lang.Enum[Color] {
  // toString will not work, since Red object have it's own toString implementation
  override def toString(): String = s"${this.name} = $rgb"
  def mkString(): String = s"${this.name} = $rgb"
  case Red   extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue  extends Color(0x0000FF)
}

object Enumerations extends App {
  // ordinal start from 0
  println(Color.Red.ordinal)
  println(Color.Green.ordinal)
  println(Color.Blue.ordinal)
  // can parse from string directly, always return the same object
  println(Color.valueOf("Blue").eq(Color.Blue))

  println(Color.Red.toString())
  println(Color.Red.mkString())

}