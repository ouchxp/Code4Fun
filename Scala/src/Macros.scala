import language.experimental.macros
import scala.reflect.macros.whitebox.Context
object Macros {
  def impl(c: Context) = c.universe.reify(println("hello world!"))
  def hello: Unit = macro impl
}


object TestMacros extends App {
  /**
   * @see http://docs.scala-lang.org/overviews/macros/overview.html
   */

  //TODO: check out macros

  import Macros._
  // cannot use this check out why
  //hello  
}