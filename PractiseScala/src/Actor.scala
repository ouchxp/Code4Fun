import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object TestActor extends App {

  case class Greeting(who: String)
  class GreetingActor extends Actor {
    def receive = {
      case Greeting(who) => println("Hello " + who)
      case s:String => println(s) 
    }
  }
  val system = ActorSystem("MySystem")
  val greeter = system.actorOf(Props[GreetingActor], name = "greeter")
  greeter ! Greeting("Charlie Parker")
  greeter ! "Gdd"
}

