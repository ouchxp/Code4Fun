import scala.io.StdIn

sealed trait WorldState { def nextState: WorldState }

// V1 /////////////////////////////////////
object RTConsole_v1 {
  def getString(state: WorldState) = (state.nextState, StdIn.readLine)
  def putString(state: WorldState, s: String) = (state.nextState, Console.print(s))
}

abstract class IOApplication_v1 {
  private class WorldStateImpl(id: BigInt) extends WorldState {
    def nextState = new WorldStateImpl(id + 1)
  }
  def main(args: Array[String]): Unit = {
    iomain(args, new WorldStateImpl(0))
  }
  def iomain(args: Array[String], startState: WorldState): (WorldState, _)
}

object HelloWorld_v1 extends IOApplication_v1 {
  import RTConsole_v1._
  def iomain(args: Array[String], startState: WorldState) =
    putString(startState, "Hello world")
}

// break property 3
object Evil_v1 extends IOApplication_v1 {
  import RTConsole_v1._
  def iomain(
    args: Array[String],
    startState: WorldState) = {
    val (stateA, a) = getString(startState)
    val (stateB, b) = getString(startState)
    assert(a == b)
    (startState, b)
  }
}

// V2 /////////////////////////////////////
object RTConsole_v2 {
  def getString = { state: WorldState => (state.nextState, StdIn.readLine) }
  def putString(s: String) = { state: WorldState => (state.nextState, Console.print(s)) }
}

//sealed trait WorldState { def nextState: WorldState }

abstract class IOApplication_v2 {
  private class WorldStateImpl(id: BigInt) extends WorldState {
    def nextState = new WorldStateImpl(id + 1)
  }
  final def main(args: Array[String]): Unit = {
    val ioAction = iomain(args)
    ioAction(new WorldStateImpl(0));
  }
  // return a function
  def iomain(args: Array[String]): (WorldState => (WorldState, _))
}

//file HelloWorld.scala  
object HelloWorld_v2 extends IOApplication_v2 {
  import RTConsole_v2._
  def iomain(args: Array[String]) =
    putString("Hello world")
}

// Break property 3
object Evil_v2 extends IOApplication_v2 {
  import RTConsole_v2._
  def iomain(args: Array[String]) = {
    (startState: WorldState) =>
      {
        val (statea, a) = getString(startState)
        val (stateb, b) = getString(startState)
        assert(a == b)
        (startState, b)
      }
  }
}

// V3 /////////////////////////////////////
sealed trait IOAction_v3[+A] extends Function1[WorldState, (WorldState, A)]
object IOAction_v3 {
  def apply[A](expression: => A): IOAction_v3[A] = new SimpleAction(expression)

  private class SimpleAction[+A](expression: => A) extends IOAction_v3[A] {
    def apply(state: WorldState) = (state.nextState, expression)
  }
}

//sealed trait WorldState{def nextState:WorldState}  
abstract class IOApplication_v3 {
  private class WorldStateImpl(id: BigInt) extends WorldState {
    def nextState = new WorldStateImpl(id + 1)
  }
  final def main(args: Array[String]): Unit = {
    val ioAction = iomain(args)
    ioAction(new WorldStateImpl(0));
  }
  def iomain(args: Array[String]): IOAction_v3[_]
}

//file RTConsole.scala  
object RTConsole_v3 {
  def getString = IOAction_v3(StdIn.readLine)
  def putString(s: String) = IOAction_v3(Console.print(s))
}

object HelloWorld_v3 extends IOApplication_v3 {
  import RTConsole_v3._
  def iomain(args: Array[String]) =
    putString("Hello world")

}

// V4////////////////////////////////////////
sealed abstract class IOAction_v4[+A] extends Function1[WorldState, (WorldState, A)] {
  def map[B](f: A => B): IOAction_v4[B] = flatMap { x => IOAction_v4(f(x)) }
  def flatMap[B](f: A => IOAction_v4[B]): IOAction_v4[B] = new ChainedAction(this, f)

  private class ChainedAction[+A, B](action1: IOAction_v4[B], f: B => IOAction_v4[A]) extends IOAction_v4[A] {
    def apply(state1: WorldState) = {
      val (state2, intermediateResult) = action1(state1);
      val action2 = f(intermediateResult)
      action2(state2)
    }
  }
}

object IOAction_v4 {
  def apply[A](expression: => A): IOAction_v4[A] = new SimpleAction(expression)
  private class SimpleAction[+A](expression: => A) extends IOAction_v4[A] {
    def apply(state: WorldState) = (state.nextState, expression)
  }
}

abstract class IOApplication_v4 {
  private class WorldStateImpl(id: BigInt) extends WorldState {
    def nextState = new WorldStateImpl(id + 1)
  }
  final def main(args: Array[String]): Unit = {
    val ioAction = iomain(args)
    ioAction(new WorldStateImpl(0));
  }
  def iomain(args: Array[String]): IOAction_v4[_]
}

object RTConsole_v4 {
  def getString = IOAction_v4(StdIn.readLine)
  def putString(s: String) = IOAction_v4(Console.print(s))
}

object HelloWorld_v4 extends IOApplication_v4 {
  import RTConsole_v4._
  def iomain(args: Array[String]) = {
//    putString("This is an example of the IO monad.")
//    .flatMap { _ => putString("What's your name?") }
//    .flatMap { _ => getString }
//    .flatMap { name => putString("Hello " + name) }
    
    
    for {
      _ <- putString("This is an example of the IO monad.")
      _ <- putString("What's your name?")
      name <- getString
      _ <- putString("Hello " + name)
    } yield ()
  }
}


