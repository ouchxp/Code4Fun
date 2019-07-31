trait Ord[T] {
  def compare(x: T, y: T): Int
  def (x: T) < (y: T) = compare(x, y) < 0
  def (x: T) > (y: T) = compare(x, y) > 0
}

given /*IntOrd*/ as Ord[Int] {
  def compare(x: Int, y: Int) =
    if (x < y) -1 else if (x > y) +1 else 0
}

// Given Instances could be named or anonymous i.e. ListOrd[T] vs just [T]
given /*ListOrd*/[T] as Ord[List[T]] given (ord: Ord[T]) {
  def compare(xs: List[T], ys: List[T]): Int = (xs, ys) match {
    case (Nil, Nil) => 0
    case (Nil, _) => -1
    case (_, Nil) => +1
    case (x :: xs1, y :: ys1) =>
      val fst = ord.compare(x, y)
      if (fst != 0) fst else compare(xs1, ys1)
  }
}

case class DBConfig(url: String, user: String, password: String)

object GivenInstances extends App  {
  def f[T](l1: List[T], l2: List[T]) given (ord: Ord[List[T]]) = {
    ord.compare(l1, l2)
  }

  println(f(List(1,2,3), List(1,2,4)))
  println(f(List(1,2,3), List(1,2,3)))
  println(f(List(1,2,4), List(1,2,3)))

  // Alias given
  given as DBConfig = DBConfig(url="a", user="b", password="c")
  def f() given (db: DBConfig) = {
    println(db)
  }
  f()

  // implicit parameter
  def max[T](x: T, y: T) given (ord: Ord[T]): T =
    if (ord.compare(x, y) < 1) y else x

  // Anonymous Given Clauses
  // as long as something can be given in scope, it can be implicitly passed down
  def max[T](x: T, y: T, z: T) given (ord: Ord[T]): T =
    max(max(x,y), max(y,z))

  println(max(1,2,3))

  // Inferring Complex Arguments
  def descending[T] given (asc: Ord[T]): Ord[T] = new Ord[T] {
    def compare(x: T, y: T) = asc.compare(y, x)
  }

  def min[T](x: T, y: T) given Ord[T] =
    max(x, y) given descending

  println(min(1,2))
}