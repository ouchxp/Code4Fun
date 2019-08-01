case class Point(x: Double, y: Double)

object ExtensionMethods extends App {
  def (p: Point) distance(d: Point): Double =
    Math.sqrt((p.x - d.x)*(p.x - d.x) + (p.y - d.y)*(p.y - d.y))
  val a = Point(1, 1)
  val b = Point(2, 1)
  val c = Point(2, 2)
  println(a distance b)
  println(b distance c)
  println(a distance c)
}