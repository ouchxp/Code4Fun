import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.concurrent.Await
import scala.concurrent.Promise
object LazyEval extends App {

  def defstr = { // actually is a function
    println("get defstr")
    "defstr"
  }

  val valstr = { // only evaluate once when defining it
    println("get valstr")
    "valstr"
  }

  lazy val lazystr = { // only evaluate once when first used
    println("get lazystr")
    "lazystr"
  }

  def f1(arg: String) = {
    arg + arg
  }

  def f1lazy(arg: => String) = {
    arg + arg
  }

  println("-------------------")

  f1(defstr) // Eval defstr only once

  println("-------------------")

  f1lazy(defstr) // Eval defstr twice

  println("-------------------")

  f1(lazystr)

  f1lazy(lazystr)

  def f2lazy(arg: => Future[String]) = {
    /** This will evaluate args 3 times immediately */
    //List(1,2,3).map { x => () => arg }

    /** This will not evaluate any future. since all args were wrapped as function. */
    List(1, 2, 3).map { x => () => arg }

    /** Stream only evaluate the first one(automatically wrap the remainders to functions (thunk) ) */
    //Stream(1,2,3).map { x => arg }
  }

  // This will evaluate future with call-by-name since it is a expression not value, it will not print "run" immediately
  val func = f2lazy(Future { println("run"); "abc" })
  Thread.sleep(1000);
}