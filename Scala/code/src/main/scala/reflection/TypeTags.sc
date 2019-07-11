val a =
  """ "utcGeneratedTime": "2015-12-12T12:12:12Z" """
    .replaceFirst(
      "utcGeneratedTime\".+\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\dZ",
      "utcGeneratedTime\": \""
    )


import scala.reflect._
import scala.reflect.runtime.universe._

def getTypeTag[T: TypeTag](a: T) = typeTag[T]
def getType[T: TypeTag](a: T) = typeOf[T]

getTypeTag(List(1, 2, 3))
getType(List(1, 2, 3))
//typeOf[List[Int]].decls


def typeMatch[T: TypeTag, B](xs: List[T])(f: PartialFunction[Type, B]) = f(typeOf[T])
// T: TypeTag is short hand for implicit tag (the line below)
// def typeMatch[T, B](xs: List[T])(f: PartialFunction[Type, B])(implicit tag: TypeTag[T]) = f(typeOf[T])


typeMatch(List(1, 2, 3)) {
  case t if t =:= typeOf[String] => "list of Strings"
  case t if t <:< typeOf[Int] => "list of Ints"
}

//meth(List("33", "22"))


val ct = classTag[List[String]]
val c = classOf[List[String]]
val tt = typeTag[List[String]]
val tp = typeOf[List[String]]
val wtt = weakTypeTag[List[String]]
val wt = weakTypeOf[List[String]]

def paramInfo[T: TypeTag](x: T): String = typeOf[T] match {
  case tr @ TypeRef(tpe, symbol, args) =>
    s"""type of $x has type "$tpe", symbol "$symbol", type argument list $args (all generic types)}"""
}

val f = (x:Object) => String.valueOf(x)
paramInfo(f)
paramInfo("123")
paramInfo(42)
paramInfo(List(1, 2, 3))


def weakParamInfo[T:WeakTypeTag](x: T): String = weakTypeOf[T] match {
  case tr @ TypeRef(tpe, symbol, args) =>
    s"""type of $x has type "$tpe", symbol "$symbol", type argument list $args (all generic types)}"""
}

weakParamInfo(List(123))
// TODO What's this????
def foo[T] = weakParamInfo(List[T]())
foo[Int]

// This won't compile
// TypeTag guarantees that you have a concrete type (i.e. one which doesn't contain any
// type parameters or abstract type members); WeakTypeTag does not (Allow have abstract type or parameter).
// http://stackoverflow.com/questions/29435985/weaktypetag-v-typetag
// http://stackoverflow.com/questions/12218641/scala-what-is-a-typetag-and-how-do-i-use-it
// http://stackoverflow.com/questions/12093752/scala-macros-cannot-create-typetag-from-a-type-t-having-unresolved-type-parame
// http://docs.scala-lang.org/overviews/reflection/overview.html
//def bar[T] = paramInfo(List[T]())
//bar[Int]
// This won't compile
def getTT[T:TypeTag] = typeTag[T]
def getWTT[T] = weakTypeTag[T]
getTT[List[_]]
getWTT[List[_]]

