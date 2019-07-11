package cats.chapter1

import me.ouchxp.tool.PrintExpr._

object Typeclass extends App {

  // Define a very simple JSON AST
  sealed trait Json
  final case class JsObject(get: Map[String, Json]) extends Json
  final case class JsString(get: String) extends Json
  final case class JsNumber(get: Double) extends Json
  case object JsNull extends Json
  final case class Person(name: String, email: String)

  // The "serialize to JSON" behaviour is encoded in this trait
  trait JsonWriter[A] {
    def write(value: A): Json
  }
  implicit val stringWriter: JsonWriter[String] = (value: String) => JsString(value)
  implicit val personWriter: JsonWriter[Person] = (value: Person) => JsObject(Map(
    "name" -> JsString(value.name),
    "email" -> JsString(value.email)
  ))


  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)

  // -------------------
  println(toJson(Person("foo", "foo@bar.com")))
  println(toJson("123"))

  printExpr(toJson(Person("foo", "foo@bar.com")))



}
