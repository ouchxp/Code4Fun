import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object TestActor extends App {
  // Make these inner classes, so it won't conflict with other classes
  case class ObjectMessage(who: String)
  class Receiver extends Actor {
    def receive = {
      case ObjectMessage(who) => println("Hello " + who)
      case "kill" => println("killing..."); context.system.shutdown; println("killed!")
      case i: Int => Thread.sleep(i)
      case x: String => println(x)
    }
  }
  
  val system = ActorSystem create "MySystem"
  val greeter = system.actorOf(Props[Receiver], name = "greeter")
  val GREETER_PATH = greeter.path.toString

  def sayHello = {
    val t = new Thread(new Runnable() {
      def run() {
        Thread sleep 4000
        val as = system actorSelection GREETER_PATH
        as ! 3000
        println("please say it")
        as ! "Hello"
        Thread sleep 1000
        as ! "kill"
      }
    })
    t.start
  }

  sayHello
  (system actorSelection GREETER_PATH) ! ObjectMessage("Tom")
  println("main finished")
}

