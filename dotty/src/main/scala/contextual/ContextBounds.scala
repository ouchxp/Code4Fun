object ContextBounds extends App {
  trait Ord[T] {
    def compare(x: T, y: T): Int
    def (x: T) < (y: T) = compare(x, y) < 0
    def (x: T) > (y: T) = compare(x, y) > 0
  }
  given /*IntOrd*/ as Ord[Int] {
    def compare(x: Int, y: Int) =
      if (x < y) -1 else if (x > y) +1 else 0
  }

  // the [T: Ord] means you have a implicit instance Ord[T] can be provided
  // so it is equivalent to `def max[T](a: T, b: T) given Ord[T]`
  def max[T: Ord](a: T, b: T): T =
    if (the[Ord[T]].compare(a, b) > 0) a else b

  def max[T: Ord](xs: List[T]): T = xs.reduceLeft(max)

  println(max(List(1,2,3)))


  // you can also use multiple context bounds
  // ```def f[T: C1 : C2, U: C3](x: T) given (y: U, z: V): R```
  // would expand to
  // ```def f[T, U](x: T) given (y: U, z: V) given C1[T], C2[T], C3[U]: R```

}