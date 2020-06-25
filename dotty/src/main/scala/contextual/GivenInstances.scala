object GivenInstances extends App  {
  case class DBConfig(url: String, user: String, password: String)
  trait Ord[T] {
    def compare(x: T, y: T): Int
    def (x: T) < (y: T) = compare(x, y) < 0
    def (x: T) > (y: T) = compare(x, y) > 0
  }

  given intOrd as Ord[Int] {
    def compare(x: Int, y: Int) =
      if (x < y) -1 else if (x > y) +1 else 0
  }

  // Given Instances could be named or anonymous i.e. ListOrd[T] vs just [T]
  given listOrd[T](using ord: Ord[T]) as Ord[List[T]] {
    def compare(xs: List[T], ys: List[T]): Int = (xs, ys) match {
      case (Nil, Nil) => 0
      case (Nil, _) => -1
      case (_, Nil) => +1
      case (x :: xs1, y :: ys1) =>
        val fst = ord.compare(x, y)
        if (fst != 0) fst else compare(xs1, ys1)
    }
  }

  def f[T](l1: List[T], l2: List[T])(using ord: Ord[List[T]]) = {
    ord.compare(l1, l2)
  }

  println(f(List(1,2,3), List(1,2,4)))
  println(f(List(1,2,3), List(1,2,3)))
  println(f(List(1,2,4), List(1,2,3)))

  // Alias using
  given as DBConfig = DBConfig(url="a", user="b", password="c")
  def f1()(using db: DBConfig)  = {
    println(db)
  }
  f1()

  // implicit parameter
  def max[T](x: T, y: T)(using ord: Ord[T]) : T =
    if (ord.compare(x, y) < 1) y else x

  // Anonymous Given Clauses
  // as long as something can be given in scope, it can be implicitly passed down
  def max[T](x: T, y: T, z: T)(using ord: Ord[T]) : T =
    max(max(x,y), max(y,z)) 
  
  // we can let compiler decide what Ord[T] to use
  println(max(1,2,3))

  // or, we can specify a named Ord[T] if multiple Ord[T] present in scope
  println(max(1,2,3)(using intOrd))

  // Inferring Complex Arguments
  def max[T](xs: List[T])(using Ord[T]): T = xs.reduceLeft(max)
  def descending[T](using asc: Ord[T]): Ord[T] = new Ord[T] {
    def compare(x: T, y: T) = asc.compare(y, x)
  }

  // not naming the given instance in function signature
  // but explicitly provide given instance
  def min[T](xs: List[T])(using Ord[T]) =
    max(xs)(using descending)

  val xs = List(1,2,3)
  println(min(xs))

  
  min(xs) 
  min(xs)(using descending) 
  // currently not working
  // min(xs)(using descending(using listOrd))
  // min(xs)(using descending(using listOrd(using intOrd)))


  // The method summon in Predef returns the given of a specific type. For example, the given instance for Ord[List[Int]] is produced by
  println(summon[Ord[List[Int]]])  // reduces to listOrd(using intOrd)
  def f2[T: Ord](l1: List[T], l2: List[T]) = {
    summon[Ord[List[T]]].compare(l1, l2)
  }
  println(f2(List(1,2,3),List(1,2,4)))

  // we can do the summoning because the `summon` function is defined like this
  // ``` def summon[T](using x: T): x.type = x ```
  // this is exactly like `implicitly` in Scala2
}