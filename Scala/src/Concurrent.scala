import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.concurrent.Await
import scala.concurrent.Promise
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
    //throw new Exception("afff")
    x + "abc"
  }

  // Futures are started as soon as they are declared.
  // If don't want started should use def instead of val, because it is lazy
  // (but what's the lazy keyword mean??? I forgot....)
  val f1 = Future {
    //if (true) throw new Exception("exp");
    expensiveComputation
  }

  val f2 = f1.flatMap { x => Future { anotherExpensiveComputation(x) } }
  f1.onComplete {
    case Failure(t) => println("f1: failed with exception " + t)
    case Success(v) => println("f2: " + v)
  }

  val f3 = f2.recoverWith {
    case t => {
      println("f3 recover")
      Future {
        println("f3-f2 recoverWith");
        t.getMessage
      }
    }
  }

  f2.onComplete {
    case Failure(t) => println("f2: failed with exception " + t)
    case Success(v) => println("f2: " + v)
  }

  f3.onComplete {
    case Failure(t) => println("f3: failed with exception " + t)
    case Success(v) => println("f3: " + v)
  }

  f1.fallbackTo {
    Future {
      println("executing fallbackto")
      "fallback"
    }
  }

  def badretry(times: Int)(block: Future[String]): Future[String] = {
    if (times == 0) {
      Future.failed(new Exception("Sorry"))
    } else {
      block fallbackTo {
        badretry(times - 1) { block }
      }
    }
  }

  // fallbackto method is not lazy evaluate, so the two futures will run in parallel
  // Not one after another failed
  badretry(10) {
    Future {
      println("badretry trying")
      anotherExpensiveComputation("fffr")
    }
  }.onComplete {
    case Failure(t) => println("badretry f1: failed with exception " + t)
    case Success(v) => println("badretry f1: " + v)
  }

  //------------------------
  def retry(times: Int)(block: => Future[String]): Future[String] = {
    val attempts = (1 to times).map(_ => () => block)
    val failed = Future.failed[String](new Exception)
    // recover with is call by name, lazy evaluate block
    // the case part is an partial function, and it's call by name. 
    // so this function will only be called when using it.
    attempts.foldLeft(failed)((a, blockFunc) => a recoverWith { case _ => blockFunc() })
  }

  retry(10) {

    Future {
      println("retryUsingRecover trying")
      anotherExpensiveComputation("resultstr")
    }
  }.onComplete {
    case Failure(t) => println("retryUsingRecover f1: failed with exception " + t)
    case Success(v) => println("retryUsingRecover f1: " + v)
  }

  // Wait for result
  val c = Await.result(f3, 2 seconds)
  println(c)

  {
    val f = Future[Int] { sys.error("failed") }
    val g = Future { 5 }
    val h = f fallbackTo g

    val z = f zip g
    z.onComplete {
      case t => println(t)
    }
    Await.result(h, 5 seconds) // evaluates to 5
    Await.result(z, 5 seconds)

  }
  println("-------------------")

  {

    val f = Future { 5 }

    f andThen {
      case r => println("r" + r) //sys.error("runtime exception")
    } andThen {
      case Failure(t) => println("t" + t)
      case Success(v) => println("f" + v)
    }

  }

  val pr = Promise[String]()

  def race[T](left: Future[T], right: Future[T]): Future[T] = {
    val p = Promise[T]()
    left onComplete { p.tryComplete }
    right onComplete { p.tryComplete }
    p.future
  }

  val f = race(Future { Thread.sleep(1000); 1 }, Future { 2 })
  val result = Await.result(f, 5 seconds)
  println("result" + result)
  
  
  Thread.sleep(100000);
}