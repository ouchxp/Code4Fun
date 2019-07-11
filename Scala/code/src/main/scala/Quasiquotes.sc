import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
import scala.reflect.runtime.currentMirror
val toolbox = currentMirror.mkToolBox()
val a = 10
val c:Tree = q"case class C(i : Int = ${a})"

showCode(c)
showRaw(c)
val tree = q"i am { a quasiquote }"
showRaw(tree)

// JIT compiler
val code = q"""println("compiled and run at runtime!")"""
val compiledCode = toolbox.compile(code)
compiledCode()
toolbox.compile(c)()

// Class name
case class C(i : Int = 10)
val cname = classOf[C].getName

println(cname)
val str = "$line0.$read$string"
