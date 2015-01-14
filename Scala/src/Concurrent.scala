import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.concurrent.Await
object Concurrent extends App {
  val a: Try[String] = Success("good")
  val b: Try[String] = Failure(new Exception("not good"))

  println(a, b, a.filter { _ == "good1" })
  println(a.failed, b.failed) //inversion
  println(a.flatMap { x => Success(123) })
  def expensiveComputation = {
    Thread.sleep(1000)
    "123"
  }

  def anotherExpensiveComputation(x: String) = {
    Thread.sleep(1000)
    x + "abc"
  }

  val f1 = Future {
    //if (true) throw new Exception("exp");
    expensiveComputation
  }

  val f2 = f1.flatMap { x => Future { anotherExpensiveComputation(x) } }
  f1.onComplete {
    case Failure(t) => println("f1: failed with exception " + t)
    case Success(v) => println("f2: " + v)
  }
  
  val f3 = f2.recover { case t => t.getMessage }
  
  f2.onComplete {
    case Failure(t) => println("f2: failed with exception " + t)
    case Success(v) => println("f2: " + v)
  }

  f3.onComplete {
    case Failure(t) => println("f3: failed with exception " + t)
    case Success(v) => println("f3: " + v)
  }

  // Make sure program does not terminate (if program terminate, then the onComplete method will not run)
  
  val c = Await.result(f3, 2 seconds)
  println(c)
  //Thread.sleep(100000);
}