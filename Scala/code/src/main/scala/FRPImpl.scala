object FRPImpl extends App {

  {
    // A simple implementation that pulling data every time
    class PulledSignal[T](e: => T) {

      private var expr: () => T = _

      protected def update(e: => T): Unit = { expr = () => e }
      update(e)

      def apply(): T = expr()

      def map[R](f: T => R): PulledSignal[R] = new PulledSignal[R](f(this()))

      override def toString: String = "PulledSignal(" + String.valueOf(apply()) + ")"
    }

    class PulledVar[T](e: => T) extends PulledSignal[T](e) {
      override def update(e: => T): Unit = super.update(e)
    }

    val s = new PulledVar[Int](3)
    val s3 = s.map { x => x * 3 }
    val s5 = s3.map { x => x + 1 }
    println(s3, s5)
    s() = 4
    println(s3, s5)
  }

  println("----------------------------------")

  {
    
  }
}